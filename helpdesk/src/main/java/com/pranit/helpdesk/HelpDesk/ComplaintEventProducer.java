
package com.crh.kafka;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintEvent {

    private Long complaintId;
    private String eventType;
    private String status;
    private String message;
}
