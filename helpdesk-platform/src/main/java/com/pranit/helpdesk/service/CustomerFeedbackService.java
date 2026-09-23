package com.pranit.helpdesk.service;

import com.pranit.helpdesk.dto.FeedbackResponse;
import com.pranit.helpdesk.dto.SubmitFeedbackRequest;
import java.util.List;

public interface CustomerFeedbackService {
  FeedbackResponse submit(Long customerId, SubmitFeedbackRequest request);
  List<FeedbackResponse> findForTicket(Long ticketId);
}
