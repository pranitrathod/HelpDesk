
package com.crh.dto;

import com.crh.entity.ResolutionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateComplaintRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Order id is required")
    private String orderId;

    @NotBlank(message = "Description is required")
    private String description;

    private ResolutionType resolutionType;
}
