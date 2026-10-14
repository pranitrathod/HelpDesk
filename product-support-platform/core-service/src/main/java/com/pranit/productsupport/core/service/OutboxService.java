package com.pranit.productsupport.core.service;
import com.fasterxml.jackson.core.JsonProcessingException; import com.fasterxml.jackson.databind.ObjectMapper; import com.pranit.productsupport.core.domain.OutboxEvent; import com.pranit.productsupport.core.repository.OutboxEventRepository; import java.time.Instant; import java.util.LinkedHashMap; import java.util.Map; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
@Service public class OutboxService {
 private final OutboxEventRepository repository; private final ObjectMapper mapper; private final String topic;
 public OutboxService(OutboxEventRepository r,ObjectMapper m,@Value("${app.kafka.events-topic}") String topic){this.repository=r;this.mapper=m;this.topic=topic;}
 public void enqueue(String type,String aggregateKey,Object payload){try{Map<String,Object> envelope=new LinkedHashMap<>();envelope.put("eventId",java.util.UUID.randomUUID().toString());envelope.put("eventType",type);envelope.put("occurredAt",Instant.now().toString());envelope.put("payload",payload);repository.save(new OutboxEvent(topic,type,aggregateKey,mapper.writeValueAsString(envelope)));}catch(JsonProcessingException e){throw new IllegalStateException("Unable to serialize outbox event",e);}}
}
