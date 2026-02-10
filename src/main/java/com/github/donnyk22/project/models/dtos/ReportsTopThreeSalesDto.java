package com.github.donnyk22.project.models.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReportsTopThreeSalesDto {
    private String title;
    private Integer sellItems;
}
