package com.crh.repository;

import com.crh.entity.Complaint;
import com.crh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByCustomer(User customer);
    List<Complaint> findByVendor(User vendor);
}
