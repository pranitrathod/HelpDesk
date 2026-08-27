
package com.crh.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ComplaintEventConsumer {

    @KafkaListener(
            topics = KafkaTopics.COMPLAINT_CREATED,
            groupId = "crh-group")
    public void consumeCreated(ComplaintEvent event) {

        try {
            log.info("Complaint created event received : {}",
                    event.getComplaintId());

        } catch (Exception ex) {
            log.error("Failed processing complaint-created event", ex);
        }
    }

    @KafkaListener(
            topics = KafkaTopics.COMPLAINT_APPROVED,
            groupId = "crh-group")
    public void consumeApproved(ComplaintEvent event) {

        try {
            log.info("Complaint approved event received : {}",
                    event.getComplaintId());

        } catch (Exception ex) {
            log.error("Failed processing complaint-approved event", ex);
        }
    }
}
