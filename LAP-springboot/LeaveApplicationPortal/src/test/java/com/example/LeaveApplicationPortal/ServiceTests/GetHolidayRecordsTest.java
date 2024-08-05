package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.Holidays;
import com.example.LeaveApplicationPortal.Repo.HolidayRepo;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetHolidayRecordsTest {

    @Mock
    HolidayRepo holidayRepo;

    @InjectMocks
    ServiceImpl service;

    private Holidays holidays1;
    private Holidays holidays2;
    private Holidays holidays3;

    @BeforeEach
    void setUp() {
        holidays1 = new Holidays("Republic day", "26th January 2024", "Friday");
        holidays2 = new Holidays("Independence day", "15th August 2024", "Thursday");
        holidays3 = new Holidays("Gandhi jayanthi", "02nd October 2024", "Wednesday");
    }

    @Test
    public void testGetHolidayRecords_NotExists() {
        when(holidayRepo.findAll()).thenReturn(Collections.emptyList());
        List<Holidays> holidays = service.getHolidayRecords();
        assertThat(holidays.isEmpty());
        verify(holidayRepo, times(1)).findAll();
    }

    @Test
    public void testGetHolidayRecords_Exists() {
        List<Holidays> holidaysList = List.of(holidays1, holidays2, holidays3);
        when(holidayRepo.findAll()).thenReturn(holidaysList);
        List<Holidays> holidays = service.getHolidayRecords();
        assertNotNull(holidays);
        assertThat(holidays).hasSize(3);
        verify(holidayRepo, times(1)).findAll();
    }
}
