package com.pranit.helpdesk.event;
public interface TicketEventPublisher {
  void publish(TicketEvent event);
}
