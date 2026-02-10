package com.github.donnyk22.project.models.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class OrderItemsAddForm {
    @NotNull(message = "Book ID is required")
    private Integer bookId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be more than 0")
    private Integer quantity;
}
