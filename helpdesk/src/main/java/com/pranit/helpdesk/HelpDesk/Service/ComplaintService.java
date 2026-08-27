
package com.crh.service;

import com.crh.dto.ComplaintResponse;
import com.crh.dto.CreateComplaintRequest;
import com.crh.entity.Complaint;
import com.crh.entity.ComplaintStatus;
import com.crh.entity.User;
import com.crh.exception.InvalidComplaintStateException;
import com.crh.exception.ResourceNotFoundException;
import com.crh.kafka.ComplaintEvent;
import com.crh.kafka.ComplaintEventProducer;
import com.crh.repository.ComplaintRepository;
import com.crh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final ComplaintEventProducer producer;

    public ComplaintResponse createComplaint(
            CreateComplaintRequest request,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Complaint complaint = Complaint.builder()
                .productName(request.getProductName())
                .orderId(request.getOrderId())
                .description(request.getDescription())
                .resolutionType(request.getResolutionType())
                .customer(customer)
                .status(ComplaintStatus.CREATED)
                .build();

        Complaint saved = complaintRepository.save(complaint);

        producer.publishComplaintCreated(
                ComplaintEvent.builder()
                        .complaintId(saved.getId())
                        .eventType("CREATED")
                        .status("CREATED")
                        .message("Complaint created")
                        .build());

        return map(saved);
    }

    public ComplaintResponse getComplaint(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Complaint not found"));

        return map(complaint);
    }

    public ComplaintResponse approveComplaint(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Complaint not found"));

        if (complaint.getStatus() != ComplaintStatus.INVESTIGATION) {
            throw new InvalidComplaintStateException(
                    "Complaint must be in INVESTIGATION state");
        }

        complaint.setStatus(ComplaintStatus.APPROVED);

        Complaint updated = complaintRepository.save(complaint);

        return map(updated);
    }

    private ComplaintResponse map(Complaint complaint) {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .productName(complaint.getProductName())
                .orderId(complaint.getOrderId())
                .status(complaint.getStatus())
                .customerEmail(complaint.getCustomer().getEmail())
                .build();
    }
}
