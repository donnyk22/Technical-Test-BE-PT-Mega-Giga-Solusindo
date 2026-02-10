package com.github.donnyk22.project.models.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ExperimentalMsBrokerForm {
    @NotBlank(message = "Subject is required")
    private String subject;
    @NotBlank(message = "message is required")
    private String message;
}
