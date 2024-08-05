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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetFileTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    private Leave leave1;
    private Leave leave2;

    @BeforeEach
    void setUp() throws IOException {
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Casual_Sick_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Accepted", new byte[0]);
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "26/07/2024", 3, "Please approve my leave", "Akash", "Declined", null);
    }

    @Test
    public void testGetFile_Exists() throws IOException {
        when(leaveRepo.findById(leave1.get_id())).thenReturn(Optional.of(leave1));
        byte[] leave = service.getFile("669e0558e9893f91d6609716");
        assertNotNull(leave);
        assertArrayEquals(leave1.getFile(), leave);
        verify(leaveRepo, times(1)).findById(leave1.get_id());
    }

    @Test
    public void testGetFile_Null() {
        when(leaveRepo.findById(leave2.get_id())).thenReturn(Optional.of(leave2));
        byte[] leave = service.getFile("669e0558e9893f91d6609717");
        assertNull(leave);
        verify(leaveRepo, times(1)).findById(leave2.get_id());
    }
}
