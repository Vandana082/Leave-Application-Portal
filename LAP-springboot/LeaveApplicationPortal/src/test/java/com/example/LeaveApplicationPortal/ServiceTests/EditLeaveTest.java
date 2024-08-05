package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EditLeaveTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    private Leave leave1;
    private Leave leave2;

    @BeforeEach
    void setUp() throws IOException {
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Casual_Sick_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Accepted", new byte[0]);
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "26/07/2024", 3, "Please approve my leave", "Akash", "Pending", null);
    }

    @Test
    public void testEditLeave() {
        when(leaveRepo.findByUseridAndStatus(leave2.getUserid(), "Pending")).thenReturn(List.of(leave2));
        List<Leave> leaves = service.editLeave("002");
        assertNotNull(leaves);
        assertThat(leaves).hasSize(1);
        assertEquals(leave2.getUserid(), leaves.getFirst().getUserid());
        verify(leaveRepo, times(1)).findByUseridAndStatus(leave2.getUserid(), "Pending");
    }
}
