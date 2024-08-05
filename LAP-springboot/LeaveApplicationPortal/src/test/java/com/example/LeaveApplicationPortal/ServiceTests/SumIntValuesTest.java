package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SumIntValuesTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    private Leave leave1;
    private Leave leave2;
    private Leave leave3;

    MultipartFile file = mock(MultipartFile.class);

    @BeforeEach
    void setUp() throws IOException {
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Casual_Sick_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Accepted", file.getBytes());
        leave2 = new Leave("669e0558e9893f91d6609717", "001", "abc","Earned_Leave", "24/07/2024", "26/07/2024", 3, "Please approve my leave", "Akash", "Accepted", null);
        leave3 = new Leave("669e0558e9893f91d6609718", "001", "abc","Earned_Leave", "24/07/2024", "25/07/2024", 2, "Please approve my leave", "Rohith", "Accepted", null);
    }

    @Test
    public void testSumIntValues_Earned_Leaves_NotExists() {
        when(leaveRepo.findByUseridAndLeaveType("002", "Earned_Leaves")).thenReturn(Collections.emptyList());
        int sum = service.sumIntValues("002", "Earned_Leaves");
        assertEquals(0, sum);
        verify(leaveRepo, times(1)).findByUseridAndLeaveType("002", "Earned_Leaves");
    }

    @Test
    public void testSumIntValues_Casual_Sick_Leave() {
        List<Leave> leaveList = List.of(leave1);
        when(leaveRepo.findByUseridAndLeaveType(leave1.getUserid(), leave1.getLeaveType())).thenReturn(leaveList);
        int sum = service.sumIntValues("001", "Casual_Sick_Leave");
        assertEquals(1, sum);
        verify(leaveRepo, times(1)).findByUseridAndLeaveType(leave1.getUserid(), leave1.getLeaveType());
    }

    @Test
    public void testSumIntValues_Earned_Leaves() {
        List<Leave> leaveList = List.of(leave2, leave3);
        when(leaveRepo.findByUseridAndLeaveType(leave2.getUserid(), leave2.getLeaveType())).thenReturn(leaveList);
        int sum = service.sumIntValues("001", "Earned_Leave");
        assertEquals(5, sum);
        verify(leaveRepo, times(1)).findByUseridAndLeaveType(leave2.getUserid(), leave2.getLeaveType());
    }
}
