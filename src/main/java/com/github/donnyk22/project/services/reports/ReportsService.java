package com.github.donnyk22.project.services.reports;

import java.util.List;

import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;

public interface ReportsService {
    ReportsSalesDto sales() throws Exception;
    List<ReportsTopThreeSalesDto> bestSeller() throws Exception;
    ReportsPricesDto prices() throws Exception;
}
