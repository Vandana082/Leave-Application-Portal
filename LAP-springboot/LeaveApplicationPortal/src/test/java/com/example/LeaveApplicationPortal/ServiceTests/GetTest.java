package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
    }

    @Test
    public void testGetByUserid_Found() {
        when(userRepo.findByUserid(anyString())).thenReturn(user1);
        User user = service.get(user1.getUserid());
        assertNotNull(user);
        assertEquals("001", user1.getUserid());
        assertEquals("abc", user1.getUsername());
    }

    @Test
    public void testGetByUserid_NotFound() {
        when(userRepo.findByUserid(anyString())).thenReturn(null);
        User user = service.get(user1.getUserid());
        assertNull(user);
    }
}
