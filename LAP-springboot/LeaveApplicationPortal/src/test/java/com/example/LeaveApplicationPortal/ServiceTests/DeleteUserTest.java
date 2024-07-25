package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DeleteUserTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    @Test
    public void testDeleteUser() {
        User user1 = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
        LoginResponse loginResponse = service.deleteUser(user1.getUserid());
        assertNotNull(loginResponse);
        assertTrue(loginResponse.getStatus());
        assertEquals("deleted successfully", loginResponse.getMessage());
        verify(userRepo, times(1)).deleteByUserid(user1.getUserid());
    }
}
