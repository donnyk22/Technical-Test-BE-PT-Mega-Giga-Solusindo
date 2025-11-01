package com.github.donnyk22.project.models.entities.customs;

import java.math.BigDecimal;

public interface ReportsPricesData {
    BigDecimal getMaxPrice();
    BigDecimal getMinPrice();
    BigDecimal getAvgPrice();
}
