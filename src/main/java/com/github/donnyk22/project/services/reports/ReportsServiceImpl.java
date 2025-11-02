package com.github.donnyk22.project.services.reports;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;
import com.github.donnyk22.project.models.entities.customs.ReportsPricesData;
import com.github.donnyk22.project.models.entities.customs.ReportsRevenueStats;
import com.github.donnyk22.project.models.entities.customs.ReportsTopThreeSales;
import com.github.donnyk22.project.models.enums.UserRoles;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;
import com.github.donnyk22.project.utils.AuthExtractUtil;

@Service
@Transactional
public class ReportsServiceImpl implements ReportsService{

    private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);

    @Autowired OrdersRepository ordersRepository;
    @Autowired BooksRepository booksRepository;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    public ReportsSalesDto sales() throws Exception{
        if(!authExtractUtil.getUserRole().equals(UserRoles.ADMIN.val())){
            logger.error("Unauthorized");
            throw new Exception("Unauthorized");
        }
        ReportsRevenueStats stats = ordersRepository.getRevenueStats();
        ReportsSalesDto result = new ReportsSalesDto()
            .setRevenue(stats.getRevenue())
            .setSellItems(stats.getSellItems());
        return result;
    }

    @Override
    public List<ReportsTopThreeSalesDto> bestSeller() throws Exception{
        if(!authExtractUtil.getUserRole().equals(UserRoles.ADMIN.val())){
            logger.error("Unauthorized");
            throw new Exception("Unauthorized");
        }
        List<ReportsTopThreeSales> top = ordersRepository.getTopThree();
        List<ReportsTopThreeSalesDto> result = new ArrayList<>();
        for(ReportsTopThreeSales item: top){
            ReportsTopThreeSalesDto dto = new ReportsTopThreeSalesDto()
                .setTitle(item.getTitle())
                .setSellItems(item.getSellItems());
            result.add(dto);
        }
        return result;
    }

    @Override
    public ReportsPricesDto prices() throws Exception{
        if(!authExtractUtil.getUserRole().equals(UserRoles.ADMIN.val())){
            logger.error("Unauthorized");
            throw new Exception("Unauthorized");
        }
        ReportsPricesData prices = booksRepository.getPriceData();
        ReportsPricesDto result = new ReportsPricesDto()
            .setMaxPrice(prices.getMaxPrice())
            .setMinPrice(prices.getMinPrice())
            .setAvgPrice(prices.getAvgPrice());
        return result;
    }
    
}
