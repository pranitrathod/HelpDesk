package com.pranit.productsupport.core.messaging;
import com.pranit.productsupport.core.domain.OutboxEvent; import com.pranit.productsupport.core.repository.OutboxEventRepository; import java.util.concurrent.TimeUnit; import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.kafka.core.KafkaTemplate; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component;
@Component public class OutboxPublisher {
 private static final Logger log=LoggerFactory.getLogger(OutboxPublisher.class); private final OutboxEventRepository repository; private final KafkaTemplate<String,String> kafka;
 public OutboxPublisher(OutboxEventRepository r,KafkaTemplate<String,String> k){repository=r;kafka=k;}
 @Scheduled(fixedDelayString="${app.outbox.poll-delay-ms:1000}") public void publishPending(){for(OutboxEvent event:repository.findTop50ByStatusOrderByCreatedAtAsc("PENDING")){try{kafka.send(event.getTopic(),event.getAggregateKey(),event.getPayload()).get(8,TimeUnit.SECONDS);event.markSent();repository.save(event);}catch(Exception e){event.recordFailure(e.getMessage());repository.save(event);log.warn("Outbox event {} publish attempt failed; attempts will be retried",event.getId());}}}
}
