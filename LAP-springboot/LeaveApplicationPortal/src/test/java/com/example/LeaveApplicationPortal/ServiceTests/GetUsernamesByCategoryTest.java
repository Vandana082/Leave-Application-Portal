package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetUsernamesByCategoryTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    @Test
    public void testGetUsernamesByCategoryManagement_Exists() {
        UsernameDTO user1 = new UsernameDTO("user1");
        UsernameDTO user2 = new UsernameDTO("user2");
        UsernameDTO user3 = new UsernameDTO("user3");
        List<UsernameDTO> usernameDTOList = Arrays.asList(user1, user2, user3);
        when(userRepo.findUsernamesByCategoryManagement()).thenReturn(usernameDTOList);
        List<UsernameDTO> usernames = service.getUsernamesByCategoryManagement(user1.getUsername());
        assertThat(usernames).hasSize(2);
        assertTrue(usernames.contains(user2));
        assertTrue(usernames.contains(user3));
        assertFalse(usernames.contains(user1));
        verify(userRepo, times(1)).findUsernamesByCategoryManagement();
    }

    @Test
    public void testGetUsernamesByCategoryManagement_NotExists() {
        when(userRepo.findUsernamesByCategoryManagement()).thenReturn(Collections.emptyList());
        List<UsernameDTO> usernames = service.getUsernamesByCategoryManagement("user1");
        assertThat(usernames).hasSize(0);
        assertTrue(usernames.isEmpty());
    }
}
