package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportsSalesDto {
    private BigDecimal revenue;
    private Integer bookSalesNumber;
}
