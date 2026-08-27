package com.pranit.helpdesk.dto; import com.pranit.helpdesk.model.*; public record UpdateTicketRequest(String title,String description,Priority priority,TicketStatus status){}
