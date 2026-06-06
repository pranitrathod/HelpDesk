
package com.crh.dto;

import com.crh.entity.ComplaintStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintResponse {
    private Long id;
    private String productName;
    private String orderId;
    private ComplaintStatus status;
    private String customerEmail;
}
