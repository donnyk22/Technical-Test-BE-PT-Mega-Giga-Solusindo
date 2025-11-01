package com.github.donnyk22.project.models.dtos;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FindResponse<T> {
    private List<T> records;
    private Integer totalPage;
    private Integer totalItem;
    private Boolean hasNext;
    private Boolean hasPrev;
}