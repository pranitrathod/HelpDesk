package com.pranit.helpdesk.service.impl;

import com.pranit.helpdesk.domain.CustomerFeedback;
import com.pranit.helpdesk.dto.FeedbackResponse;
import com.pranit.helpdesk.dto.SubmitFeedbackRequest;
import com.pranit.helpdesk.exception.ConflictException;
import com.pranit.helpdesk.repository.CustomerFeedbackRepository;
import com.pranit.helpdesk.service.CustomerFeedbackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

  private final CustomerFeedbackRepository repository;

  @Override
  public FeedbackResponse submit(Long customerId, SubmitFeedbackRequest request) {
    if (repository.existsByTicketId(request.getTicketId())) {
      throw new ConflictException("Feedback has already been submitted for this ticket");
    }

    return response(repository.save(
        new CustomerFeedback(
            request.getTicketId(),
            customerId,
            request.getRating(),
            request.getComment())));
  }

  @Override
  @Transactional(readOnly = true)
  public List<FeedbackResponse> findForTicket(Long ticketId) {
    return repository.findByTicketId(ticketId).stream()
        .map(this::response)
        .toList();
  }

  private FeedbackResponse response(CustomerFeedback feedback) {
    return new FeedbackResponse(
        feedback.getId(),
        feedback.getTicketId(),
        feedback.getRating(),
        feedback.getComment(),
        feedback.getSubmittedAt());
  }
}
