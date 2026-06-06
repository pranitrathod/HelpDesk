
package com.crh.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintEventProducer {

    private final KafkaTemplate<String, ComplaintEvent> kafkaTemplate;

    public void publishComplaintCreated(ComplaintEvent event) {
        kafkaTemplate.send(
                KafkaTopics.COMPLAINT_CREATED,
                String.valueOf(event.getComplaintId()),
                event);

        log.info("Published complaint-created event for complaintId={}",
                event.getComplaintId());
    }

    public void publishComplaintApproved(ComplaintEvent event) {
        kafkaTemplate.send(
                KafkaTopics.COMPLAINT_APPROVED,
                String.valueOf(event.getComplaintId()),
                event);

        log.info("Published complaint-approved event for complaintId={}",
                event.getComplaintId());
    }
}
