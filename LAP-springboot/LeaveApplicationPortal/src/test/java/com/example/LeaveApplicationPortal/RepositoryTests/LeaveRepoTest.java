package com.example.LeaveApplicationPortal.RepositoryTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class LeaveRepoTest {

    @Autowired
    private LeaveRepo leaveRepo;

    @Test
    public void testFindAll() {
        List<Leave> leave1 = leaveRepo.findAll();
        assertThat(leave1).isNotEmpty();
    }

    @Test
    public void testFindByStatus() {
        List<Leave> leave2 = leaveRepo.findByStatus("Pending");

    }
}
