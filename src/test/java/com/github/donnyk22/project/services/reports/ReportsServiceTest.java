package com.github.donnyk22.project.services.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;
import com.github.donnyk22.project.models.entities.customs.ReportsPricesData;
import com.github.donnyk22.project.models.entities.customs.ReportsRevenueStats;
import com.github.donnyk22.project.models.entities.customs.ReportsTopThreeSales;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;

@ExtendWith(MockitoExtension.class)
public class ReportsServiceTest {

    @InjectMocks
    private ReportsServiceImpl reportsService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private BooksRepository booksRepository;

    // ================= SALES =================

    @Test
    void sales_shouldReturnSalesDto_whenDataExists() {
        // given
        ReportsRevenueStats stats = mock(ReportsRevenueStats.class);

        when(stats.getRevenue()).thenReturn(BigDecimal.valueOf(1_000_000));
        when(stats.getSellItems()).thenReturn(50);
        when(ordersRepository.getRevenueStats()).thenReturn(stats);

        // when
        ReportsSalesDto result = reportsService.sales();

        // then
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1_000_000), result.getRevenue());
        assertEquals(50, result.getSellItems());

        verify(ordersRepository).getRevenueStats();
    }

    @Test
    void sales_shouldThrowException_whenDataNotFound() {
        when(ordersRepository.getRevenueStats()).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
            () -> reportsService.sales());
    }

    // ================= BESTSELLER =================

    @Test
    void bestSeller_shouldReturnList_whenDataExists() {
        // given
        ReportsTopThreeSales item1 = mock(ReportsTopThreeSales.class);
        when(item1.getTitle()).thenReturn("Book A");
        when(item1.getSellItems()).thenReturn(100);

        ReportsTopThreeSales item2 = mock(ReportsTopThreeSales.class);
        when(item2.getTitle()).thenReturn("Book B");
        when(item2.getSellItems()).thenReturn(80);

        ReportsTopThreeSales item3 = mock(ReportsTopThreeSales.class);
        when(item3.getTitle()).thenReturn("Book C");
        when(item3.getSellItems()).thenReturn(60);

        when(ordersRepository.getTopThree()).thenReturn(List.of(item1, item2, item3));

        // when
        List<ReportsTopThreeSalesDto> result = reportsService.bestSeller();

        // then
        assertNotNull(result);

        assertEquals(3, result.size());
        assertEquals("Book A", result.get(0).getTitle());
        assertEquals(100, result.get(0).getSellItems());

        verify(ordersRepository).getTopThree();
    }

    @Test
    void bestSeller_shouldThrowException_whenEmpty() {
        when(ordersRepository.getTopThree()).
            thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
            () -> reportsService.bestSeller());
    }

    // ================= PRICES =================

    @Test
    void prices_shouldReturnPricesDto_whenDataExists() {
        // given
        ReportsPricesData data = mock(ReportsPricesData.class);

        when(data.getMaxPrice()).thenReturn(BigDecimal.valueOf(200_000));
        when(data.getMinPrice()).thenReturn(BigDecimal.valueOf(50_000));
        when(data.getAvgPrice()).thenReturn(BigDecimal.valueOf(120_000));
        when(booksRepository.getPriceData()).thenReturn(data);

        // when
        ReportsPricesDto result = reportsService.prices();

        // then
        assertNotNull(result);

        assertEquals(BigDecimal.valueOf(200_000), result.getMaxPrice());
        assertEquals(BigDecimal.valueOf(50_000), result.getMinPrice());
        assertEquals(BigDecimal.valueOf(120_000), result.getAvgPrice());

        verify(booksRepository).getPriceData();
    }

    @Test
    void prices_shouldThrowException_whenDataNotFound() {
        when(booksRepository.getPriceData())
            .thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
            () -> reportsService.prices());
    }
}