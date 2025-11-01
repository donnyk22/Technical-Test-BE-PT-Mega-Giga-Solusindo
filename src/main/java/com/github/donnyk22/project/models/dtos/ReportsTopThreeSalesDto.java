package com.github.donnyk22.project.models.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportsTopThreeSalesDto {
    private String title;
    private Integer sellItems;
}
