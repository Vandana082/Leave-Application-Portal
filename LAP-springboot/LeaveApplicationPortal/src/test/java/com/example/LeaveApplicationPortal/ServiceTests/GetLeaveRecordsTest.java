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
public class GetLeaveRecordsTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    private Leave leave1;
    private Leave leave2;

    MultipartFile file = mock(MultipartFile.class);

    @BeforeEach
    void setUp() throws IOException {
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Casual_Sick_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Accepted", file.getBytes());
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "25/07/2024", 2, "Please approve my leave", "Rohith", "Declined", null);
    }

    @Test
    public void testGetLeaveRecords_NoRecords() {
        when(leaveRepo.findAll()).thenReturn(Collections.emptyList());
        List<Leave> leaves = service.getLeaveRecords("All", "");
        assertThat(leaves.isEmpty());
    }

    @Test
    public void testGetLeaveRecords_AllExists_SearchEmpty() {
        List<Leave> leaveList = List.of(leave1, leave2);
        when(leaveRepo.findAll()).thenReturn(leaveList);
        List<Leave> leaves = service.getLeaveRecords("All", "");
        assertThat(leaves).hasSize(2);
        verify(leaveRepo, times(1)).findAll();
    }

    @Test
    public void testGetLeaveRecords_Accepted_SearchEmpty() {
        List<Leave> leaveList = List.of(leave1);
        when(leaveRepo.findByStatus("Accepted")).thenReturn(leaveList);
        List<Leave> leaves = service.getLeaveRecords("Accepted", "");
        assertThat(leaves).hasSize(1);
        assertEquals(leave1.getStatus(), leaves.getFirst().getStatus());
        verify(leaveRepo, times(1)).findByStatus("Accepted");
    }

    @Test
    public void testGetLeaveRecords_Declined_SearchEmpty() {
        List<Leave> leaveList = List.of(leave2);
        when(leaveRepo.findByStatus("Declined")).thenReturn(leaveList);
        List<Leave> leaves = service.getLeaveRecords("Declined", "");
        assertThat(leaves).hasSize(1);
        assertEquals(leave2.getStatus(), leaves.getFirst().getStatus());
        verify(leaveRepo, times(1)).findByStatus("Declined");
    }

    @Test
    public void testGetLeaveRecords_Pending_SearchEmpty() {
        when(leaveRepo.findByStatus("Pending")).thenReturn(Collections.emptyList());
        List<Leave> leaves = service.getLeaveRecords("Pending", "");
        assertThat(leaves.isEmpty());
        verify(leaveRepo, times(1)).findByStatus("Pending");
    }

    @Test
    public  void testGetLeaveRecords_AnyStatus_SearchTermExists() {
        List<Leave> leaveList = List.of(leave1);
        when(leaveRepo.findByUseridContainingIgnoreCase("1")).thenReturn(leaveList);
        List<Leave> leaves = service.getLeaveRecords("All", "1");
        assertThat(leaves).hasSize(1);
        assertEquals(leave1.getUserid(), leaves.getFirst().getUserid());
        verify(leaveRepo, times(1)).findByUseridContainingIgnoreCase("1");
    }
}
