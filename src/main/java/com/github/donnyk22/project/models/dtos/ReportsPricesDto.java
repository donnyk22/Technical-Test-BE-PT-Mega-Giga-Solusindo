package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReportsPricesDto {
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private BigDecimal avgPrice;
}
