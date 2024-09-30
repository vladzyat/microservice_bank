package com.vladzyatkovski.task_for_solva;
import com.vladzyatkovski.task_for_solva.repository.MonthlyTransactionLimitsRepository;
import com.vladzyatkovski.task_for_solva.service.MonthlyTransactionLimitService;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.dto.MonthlyTransactionLimitDTO;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MonthlyTransactionLimitServiceImplTest {

    @Mock
    private MonthlyTransactionLimitsRepository monthlyTransactionLimitsRepository;

    @InjectMocks
    private MonthlyTransactionLimitService monthlyTransactionLimitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetLimit() {

        MonthlyTransactionLimitDTO limitDTO = new MonthlyTransactionLimitDTO();
        limitDTO.setAccountNumber("1234567890");
        limitDTO.setExpenseCategory("PRODUCT");
        limitDTO.setLimitSum(new BigDecimal("1000.00"));

        MonthlyTransactionLimit expectedLimit = new MonthlyTransactionLimit();
        expectedLimit.setAccount("1234567890");
        expectedLimit.setExpenseCategory(ExpenseCategory.PRODUCT);
        expectedLimit.setLimitSum(new BigDecimal("1000.00"));
        expectedLimit.setLimitDateTime(ZonedDateTime.now());

        when(monthlyTransactionLimitsRepository.save(any(MonthlyTransactionLimit.class)))
                .thenReturn(expectedLimit);

        MonthlyTransactionLimit actualLimit = monthlyTransactionLimitService.setLimit(limitDTO);

        assertEquals(expectedLimit.getAccount(), actualLimit.getAccount());
        assertEquals(expectedLimit.getExpenseCategory(), actualLimit.getExpenseCategory());
        assertEquals(expectedLimit.getLimitSum(), actualLimit.getLimitSum());

        verify(monthlyTransactionLimitsRepository, times(1))
                .save(any(MonthlyTransactionLimit.class));
    }
}
