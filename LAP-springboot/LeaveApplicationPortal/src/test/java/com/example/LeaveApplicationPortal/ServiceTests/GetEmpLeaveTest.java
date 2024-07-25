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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetEmpLeaveTest {

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
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "26/07/2024", 3, "Please approve my leave", "Akash", "Declined", null);
        leave3 = new Leave("669e0558e9893f91d6609718", "002", "efg","Earned_Leave", "24/07/2024", "25/07/2024", 2, "Please approve my leave", "Rohith", "Pending", null);
    }

    @Test
    public void testGetEmpLeave_All_NotExists() {
        when(leaveRepo.findByUserid("003")).thenReturn(Collections.emptyList());
        List<Leave> leaves = service.getEmpLeave("003", "All");
        assertThat(leaves.isEmpty());
        verify(leaveRepo, times(1)).findByUserid("003");
    }

    @Test
    public void testGetEmpLeave_All_Exists() {
        List<Leave> leaveList = List.of(leave2, leave3);
        when(leaveRepo.findByUserid("002")).thenReturn(leaveList);
        List<Leave> leaves = service.getEmpLeave("002", "All");
        assertNotNull(leaves);
        assertThat(leaves).hasSize(2);
        assertEquals("002", leaves.getFirst().getUserid());
        verify(leaveRepo, times(1)).findByUserid("002");
    }

    @Test
    public void testGetEmpLeave_Accepted_Exists() {
        List<Leave> leaveList = List.of(leave1);
        when(leaveRepo.findByUseridAndStatus("001", "Accepted")).thenReturn(leaveList);
        List<Leave> leaves = service.getEmpLeave("001", "Accepted");
        assertNotNull(leaves);
        assertThat(leaves).hasSize(1);
        assertEquals("001", leaves.getFirst().getUserid());
        assertEquals("Accepted", leaves.getFirst().getStatus());
    }

    @Test
    public void testGetEmpLeave_Declined_Exists() {
        List<Leave> leaveList = List.of(leave2);
        when(leaveRepo.findByUseridAndStatus("002", "Declined")).thenReturn(leaveList);
        List<Leave> leaves = service.getEmpLeave("002", "Declined");
        assertNotNull(leaves);
        assertThat(leaves).hasSize(1);
        assertEquals("002", leaves.getFirst().getUserid());
        assertEquals("Declined", leaves.getFirst().getStatus());
    }

    @Test
    public void testGetEmpLeave_Pending_Exists() {
        List<Leave> leaveList = List.of(leave3);
        when(leaveRepo.findByUseridAndStatus("002", "Pending")).thenReturn(leaveList);
        List<Leave> leaves = service.getEmpLeave("002", "Pending");
        assertNotNull(leaves);
        assertThat(leaves).hasSize(1);
        assertEquals("002", leaves.getFirst().getUserid());
        assertEquals("Pending", leaves.getFirst().getStatus());
    }
}
