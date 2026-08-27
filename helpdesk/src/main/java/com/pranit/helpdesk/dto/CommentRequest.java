package com.pranit.helpdesk.dto; import jakarta.validation.constraints.NotBlank; public record CommentRequest(@NotBlank String message){}
