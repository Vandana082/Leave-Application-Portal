package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
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
public class GetUserTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
        user2 = new User("efg", "002", "efg@gmail.com", "uvw", "password12", "Employee");
        user3 = new User("hij", "003", "hij@gmail.com", "rst", "password@123", "Employee");
    }

    @Test
    public void testGetUser_NotExists() {
        when(userRepo.findAll()).thenReturn(Collections.emptyList());
        List<User> users = service.getUser();
        assertThat(users.isEmpty());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testGetUser_Exists() {
        List<User> userList = List.of(user1, user2, user3);
        when(userRepo.findAll()).thenReturn(userList);
        List<User> users = service.getUser();
        assertNotNull(users);
        assertThat(users).hasSize(3);
        verify(userRepo, times(1)).findAll();
    }
}
