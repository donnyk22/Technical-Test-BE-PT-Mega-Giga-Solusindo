package com.github.donnyk22.project.models.forms;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SearchForm {
    private String keyword;
    private Integer page;
    private Integer limit = 10;
}
