package com.github.donnyk22.project.services.reports;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;
import com.github.donnyk22.project.models.entities.customs.ReportsPricesData;
import com.github.donnyk22.project.models.entities.customs.ReportsRevenueStats;
import com.github.donnyk22.project.models.entities.customs.ReportsTopThreeSales;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final OrdersRepository ordersRepository;
    private final BooksRepository booksRepository;

    @Override
    public ReportsSalesDto sales() {
        ReportsRevenueStats stats = ordersRepository.getRevenueStats();
        if (stats == null) {
            throw new ResourceNotFoundException("Sales data not found");
        }
        return new ReportsSalesDto()
            .setRevenue(stats.getRevenue())
            .setSellItems(stats.getSellItems());
    }

    @Override
    public List<ReportsTopThreeSalesDto> bestSeller() {
        List<ReportsTopThreeSales> top = ordersRepository.getTopThree();
        if (top == null || top.isEmpty()) {
            throw new ResourceNotFoundException("Best seller data not found");
        }
        return top.stream()
            .map(item -> new ReportsTopThreeSalesDto()
            .setTitle(item.getTitle())
            .setSellItems(item.getSellItems()))
            .toList();
    }

    @Override
    public ReportsPricesDto prices() {
        ReportsPricesData prices = booksRepository.getPriceData();
        if (prices == null) {
            throw new ResourceNotFoundException("Price data not found");
        }
        return new ReportsPricesDto()
            .setMaxPrice(prices.getMaxPrice())
            .setMinPrice(prices.getMinPrice())
            .setAvgPrice(prices.getAvgPrice());
    }
}