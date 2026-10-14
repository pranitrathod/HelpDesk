package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.ServiceQuote; import java.util.Optional; import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Lock; import org.springframework.data.jpa.repository.Query; import org.springframework.data.repository.query.Param;
public interface ServiceQuoteRepository extends JpaRepository<ServiceQuote,Long>{
 Optional<ServiceQuote> findByIdAndTicket_Customer_Id(Long id,Long customerId);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select q from ServiceQuote q where q.id=:quoteId and q.ticket.customer.id=:customerId") Optional<ServiceQuote> findForPayment(@Param("quoteId") Long quoteId,@Param("customerId") Long customerId);
}
