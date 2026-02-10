package com.github.donnyk22.project.models.forms;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BookFindForm extends SearchForm{
    private Integer categoyId;
}
