package com.github.donnyk22.project.models.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BookFindForm extends SearchForm{
    private Integer categoy;
}
