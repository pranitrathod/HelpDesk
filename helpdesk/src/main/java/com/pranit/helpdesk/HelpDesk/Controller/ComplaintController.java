
package com.crh.controller;

import com.crh.dto.ComplaintResponse;
import com.crh.dto.CreateComplaintRequest;
import com.crh.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(
            @Valid @RequestBody CreateComplaintRequest request,
            @RequestHeader("X-USER-EMAIL") String customerEmail) {

        return ResponseEntity.ok(
                complaintService.createComplaint(request, customerEmail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaint(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaint(id));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ComplaintResponse> approveComplaint(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.approveComplaint(id));
    }
}
