package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="outbox_events") public class OutboxEvent {
 @Id @Column(length=36) private String id;
 @Column(nullable=false,length=200) private String topic;
 @Column(name="event_type",nullable=false,length=100) private String eventType;
 @Column(name="aggregate_key",nullable=false,length=120) private String aggregateKey;
 @Column(nullable=false,columnDefinition="longtext") private String payload;
 @Column(nullable=false,length=20) private String status="PENDING";
 @Column(nullable=false) private int attempts=0;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="published_at") private Instant publishedAt;
 @Column(name="last_error",length=1000) private String lastError;
 protected OutboxEvent(){} public OutboxEvent(String topic,String type,String key,String payload){this.id=UUID.randomUUID().toString();this.topic=topic;this.eventType=type;this.aggregateKey=key;this.payload=payload;this.createdAt=Instant.now();}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();} public String getId(){return id;} public String getTopic(){return topic;} public String getAggregateKey(){return aggregateKey;} public String getPayload(){return payload;} public String getStatus(){return status;} public void markSent(){status="SENT";publishedAt=Instant.now();lastError=null;} public void recordFailure(String message){attempts++;lastError=message==null?"Unknown Kafka publish failure":message.substring(0,Math.min(1000,message.length()));if(attempts>=12)status="DEAD";}
}
