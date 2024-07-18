package com.example.LeaveApplicationPortal;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

   /* @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUserid("001");
        user.setUsername("ABC");
        user.setEmail("abc@gmail.com");
        user.setRole("xyz");
        user.setPassword("password");
        user.setCategory("employee");
        userRepo.save(user);
    }*/

    @Test
    public void TestFindByUserid() {
        User user = userRepo.findByUserid("AT-101");
        assertNotNull(user, "User should not be null");
        assertEquals("AT-101", user.getUserid(), "User ID should match");
    }
}
