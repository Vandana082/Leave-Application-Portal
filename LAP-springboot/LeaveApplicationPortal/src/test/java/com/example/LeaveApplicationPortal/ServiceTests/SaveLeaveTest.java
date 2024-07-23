package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.DTO.LeaveDTO;
import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SaveLeaveTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    //private LeaveDTO leaveDto;

    /*@BeforeEach
    void setUp() throws IOException {
        leaveDto = new LeaveDTO();
        leaveDto.set_id("669f77ed13973306005bd76d");
        leaveDto.setUserid("001");
        leaveDto.setUsername("abc");
        leaveDto.setLeaveType("Earned_Leave");
        leaveDto.setStartDate("23/07/2024");
        leaveDto.setEndDate("24/07/2024");
        leaveDto.setCount(2);
        leaveDto.setMsg("Please approve my leave");
        leaveDto.setApprover("Rohith");
        leaveDto.setStatus("Pending");
    }*/

    @Test
    public void testFieldsMandatory() {
        LeaveDTO leaveDto = new LeaveDTO();
        leaveDto.set_id("669f77ed13973306005bd76d");
        leaveDto.setUserid("001");
        leaveDto.setUsername("abc");
        leaveDto.setLeaveType("");
        leaveDto.setStartDate("23/07/2024");
        leaveDto.setEndDate("24/07/2024");
        leaveDto.setCount(2);
        leaveDto.setMsg("Please approve my leave");
        leaveDto.setApprover("Rohith");
        leaveDto.setStatus("Pending");
        MultipartFile file = mock(MultipartFile.class);
        LoginResponse loginResponse = service.saveLeave(leaveDto, file);
        assertFalse(loginResponse.getStatus());
        assertEquals("All fields are mandatory", loginResponse.getMessage());
        verify(leaveRepo, times(0)).save(any(Leave.class));
    }

    @Test
    public void testLeaveApplied() throws IOException {
        LeaveDTO leaveDto = new LeaveDTO();
        leaveDto.set_id("669f77ed13973306005bd76d");
        leaveDto.setUserid("001");
        leaveDto.setUsername("abc");
        leaveDto.setLeaveType("Earned_Leave");
        leaveDto.setStartDate("23/07/2024");
        leaveDto.setEndDate("24/07/2024");
        leaveDto.setCount(2);
        leaveDto.setMsg("Please approve my leave");
        leaveDto.setApprover("Rohith");
        leaveDto.setStatus("Pending");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenReturn(new byte[] {1, 2, 3});
        when(leaveRepo.findByUseridAndStatus(leaveDto.getUserid(), "Pending")).thenReturn(Collections.emptyList());
        LoginResponse loginResponse = service.saveLeave(leaveDto, file);
        assertTrue(loginResponse.getStatus());
        assertEquals("Leave applied", loginResponse.getMessage());

        ArgumentCaptor<Leave> leaveCaptor = ArgumentCaptor.forClass(Leave.class);
        verify(leaveRepo, times(1)).save(leaveCaptor.capture());
        Leave capturedLeave = leaveCaptor.getValue();
        assertEquals("001", capturedLeave.getUserid());
        assertEquals("abc", capturedLeave.getUsername());
        assertEquals("Earned_Leave", capturedLeave.getLeaveType());
    }

    @Test
    public  void testLeaveNotApplied() throws IOException {
        LeaveDTO leaveDto = new LeaveDTO();
        leaveDto.set_id("669f77ed13973306005bd76d");
        leaveDto.setUserid("001");
        leaveDto.setUsername("abc");
        leaveDto.setLeaveType("Earned_Leave");
        leaveDto.setStartDate("23/07/2024");
        leaveDto.setEndDate("24/07/2024");
        leaveDto.setCount(2);
        leaveDto.setMsg("Please approve my leave");
        leaveDto.setApprover("Rohith");
        leaveDto.setStatus("Pending");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenReturn(new byte[0]);
        List<Leave> leaveList = List.of(new Leave());
        when(leaveRepo.findByUseridAndStatus(leaveDto.getUserid(), "Pending")).thenReturn(leaveList);
        LoginResponse loginResponse = service.saveLeave(leaveDto, file);
        assertFalse(loginResponse.getStatus());
        assertEquals("Your leave request is in pending status", loginResponse.getMessage());
        verify(leaveRepo, times(0)).save(any(Leave.class));
    }

    @Test
    public void testError() throws IOException {
        LeaveDTO leaveDto = new LeaveDTO();
        leaveDto.set_id("669f77ed13973306005bd76d");
        leaveDto.setUserid("001");
        leaveDto.setUsername("abc");
        leaveDto.setLeaveType("Earned_Leave");
        leaveDto.setStartDate("23/07/2024");
        leaveDto.setEndDate("24/07/2024");
        leaveDto.setCount(2);
        leaveDto.setMsg("Please approve my leave");
        leaveDto.setApprover("Rohith");
        leaveDto.setStatus("Pending");
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenThrow(new IOException());
        LoginResponse loginResponse = service.saveLeave(leaveDto, file);
        assertFalse(loginResponse.getStatus());
        assertEquals("Error occurred while processing file", loginResponse.getMessage());
        verify(leaveRepo, times(0)).save(any(Leave.class));
    }
}
