package com.app.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String from;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String to;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String message;

}
