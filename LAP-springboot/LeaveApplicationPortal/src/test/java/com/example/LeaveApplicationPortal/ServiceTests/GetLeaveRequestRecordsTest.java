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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetLeaveRequestRecordsTest {

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
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Casual_Sick_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Pending", file.getBytes());
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "25/07/2024", 2, "Please approve my leave", "Rohith", "Pending", null);
        leave3 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "26/07/2024", 3, "Please approve my leave", "Akash", "Accepted", null);

    }

    @Test
    public void testGetLeaveRequestRecords_NotFound() {
        when(leaveRepo.findByStatusAndApprover("Pending", "Akash")).thenReturn(Collections.emptyList());
        List<Leave> leaves = service.getLeaveRequestRecords("Akash");
        assertThat(leaves.isEmpty());
        verify(leaveRepo, times(1)).findByStatusAndApprover("Pending", "Akash");
    }

    @Test
    public void testGetLeaveRequestRecords_Found() {
        List<Leave> leaveList = List.of(leave1);
        when(leaveRepo.findByStatusAndApprover("Pending", "Akshatha")).thenReturn(leaveList);
        List<Leave> leaves = service.getLeaveRequestRecords("Akshatha");
        assertThat(leaves).hasSize(1);
        assertEquals(leave1.getUserid(), leaves.getFirst().getUserid());
        assertEquals(leave1.getStatus(), leaves.getFirst().getStatus());
        verify(leaveRepo, times(1)).findByStatusAndApprover("Pending", "Akshatha");
    }
}
