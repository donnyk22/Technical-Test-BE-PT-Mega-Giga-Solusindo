package com.github.donnyk22.project.services.reports;

import java.util.List;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;

public interface ReportsService {
    ReportsSalesDto sales();
    List<BooksDto> bestSeller();
    ReportsPricesDto prices();
}
