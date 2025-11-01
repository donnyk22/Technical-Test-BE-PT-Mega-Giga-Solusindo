package com.github.donnyk22.project.models.forms;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemsAddForm {
    private Integer bookId;
    private Integer quantity;
}
