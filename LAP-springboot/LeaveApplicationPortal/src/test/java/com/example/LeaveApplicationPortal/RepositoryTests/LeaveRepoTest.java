package com.example.LeaveApplicationPortal.RepositoryTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class LeaveRepoTest {

    @Autowired
    private LeaveRepo leaveRepo;

    public void setUp() {
        Leave leave = new Leave();
        leave.set_id("669e0558e9893f91d6609716");
        leave.setUserid("001");
        leave.setStatus("Pending");
        leave.setApprover("Akshatha");
        leaveRepo.save(leave);
    }

    @Test
    public void testFindAll() {
        //Success
        List<Leave> leave1 = leaveRepo.findAll();
        assertThat(leave1).isNotEmpty();
    }

    @Test
    public void testFindByStatus() {
        //Success
        List<Leave> leave2 = leaveRepo.findByStatus("Pending");
        List<Leave> leave3 = leaveRepo.findByStatus("Accepted");
        List<Leave> leave4 = leaveRepo.findByStatus("Declined");
        assertThat(leave2).isNotEmpty();//.hasSize(1);
        assertThat(leave3).isNotEmpty();//.hasSize(17);
        assertThat(leave4).isNotEmpty();//.hasSize(4);

        //Failure
        List<Leave> leave5 = leaveRepo.findByStatus("Rejected");
        assertThat(leave5).isEmpty();
    }

    @Test
    public void testFindByUserid() {
        //Success
        List<Leave> leave6 = leaveRepo.findByUserid("AT-101");
        assertNotNull(leave6);
        assertEquals("AT-101", leave6.getFirst().getUserid());
        assertEquals("Vandana", leave6.getFirst().getUsername());

        //Failure
        List<Leave> leave7 = leaveRepo.findByUserid("1088");
        assertThat(leave7).isEmpty();
    }

    @Test
    public void testFindByUseridAndStatus() {
        //Success
        List<Leave> leave8 = leaveRepo.findByUseridAndStatus("AT-101", "Accepted");
        //assertThat(leave8).isNotEmpty().hasSize(8);
        assertThat(leave8.getFirst().getUserid()).isEqualTo("AT-101");
        assertThat(leave8.getFirst().getStatus()).isEqualTo("Accepted");

        //Failure
        List<Leave> leave9 = leaveRepo.findByUseridAndStatus("AT-101", "Rejected");
        assertThat(leave9).isEmpty();
    }

    @Test
    public void testFindByUseridAndLeaveType() {
        //Success
        List<Leave> leave10 = leaveRepo.findByUseridAndLeaveType("AT-101", "Earned_Leave");
        //assertThat(leave10).isNotEmpty().hasSize(5);
        assertThat(leave10.getFirst().getUserid()).isEqualTo("AT-101");
        assertThat(leave10.getFirst().getLeaveType()).isEqualTo("Earned_Leave");

        //Failure
        List<Leave> leave11 = leaveRepo.findByUseridAndLeaveType("1088", "Earned_Leave");
        assertThat(leave11).isEmpty();
    }

    @Test
    public void testFindByUseridContainingIgnoreCase() {
        //Success
        List<Leave> leave12 = leaveRepo.findByUseridContainingIgnoreCase("01");
        assertThat(leave12).isNotEmpty().extracting(Leave::getUserid).contains("AT-101");

        //Failure
        List<Leave> leave13 = leaveRepo.findByUseridContainingIgnoreCase("88");
        assertThat(leave13).isEmpty();
    }

    @Test
    public void testFindByStatusAndApprover() {
        //Success
        List<Leave> leave14 = leaveRepo.findByStatusAndApprover("Accepted", "Akshatha");
        assertThat(leave14).isNotEmpty();//.hasSize(7);

        //Failure
        List<Leave> leave15 = leaveRepo.findByStatusAndApprover("Accepted", "Vandana");
        assertThat(leave15).isEmpty();
    }

    @Test
    public void testDeleteBy_id() {
        //Success
        setUp();
        Optional<Leave> beforeDeletion = leaveRepo.findById("669e0558e9893f91d6609716");
        assertThat(beforeDeletion).isPresent();

        //Failure
        leaveRepo.deleteBy_id("669e0558e9893f91d6609716");
        Optional<Leave> afterDeletion = leaveRepo.findById("669e0558e9893f91d6609716");
        assertThat(afterDeletion).isNotPresent();
    }
}
