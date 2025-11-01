package com.github.donnyk22.project.services.reports;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.repositories.OrdersRepository;

@Service
@Transactional
public class ReportsServiceImpl implements ReportsService{

    private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public ReportsSalesDto sales() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'sales'");
        return new ReportsSalesDto();
    }

    @Override
    public List<BooksDto> bestSeller() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'bestSeller'");
        return new ArrayList<>(0);
    }

    @Override
    public ReportsPricesDto prices() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'prices'");
        return new ReportsPricesDto();
    }
    
}
