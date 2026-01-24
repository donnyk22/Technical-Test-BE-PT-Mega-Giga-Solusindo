package com.github.donnyk22.project.models.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExperimentalMsBrokerForm {
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "message is required")
    private String message;
}
