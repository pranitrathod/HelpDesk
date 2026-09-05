package com.pranit.helpdesk.event;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Component;
@Component public class KafkaTicketEventPublisher implements TicketEventPublisher { private final KafkaTemplate<String,TicketEvent> kafka; public KafkaTicketEventPublisher(KafkaTemplate<String,TicketEvent> kafka){this.kafka=kafka;} public void publish(TicketEvent event){kafka.send("ticket-events",event.getTicketId().toString(),event);} }
