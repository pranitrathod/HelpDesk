package com.pranit.helpdesk.service;
import com.pranit.helpdesk.dto.BusinessModuleDtos.FeedbackResponse;
import com.pranit.helpdesk.dto.BusinessModuleDtos.SubmitFeedbackRequest;
import java.util.List;
public interface CustomerFeedbackService {
  FeedbackResponse submit(Long customerId, SubmitFeedbackRequest request);
  List<FeedbackResponse> findForTicket(Long ticketId);
}
