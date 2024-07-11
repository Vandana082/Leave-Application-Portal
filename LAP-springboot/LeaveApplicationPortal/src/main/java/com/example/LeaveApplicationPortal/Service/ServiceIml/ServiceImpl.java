package com.example.LeaveApplicationPortal.Service.ServiceIml;

import com.example.LeaveApplicationPortal.DTO.LeaveDTO;
import com.example.LeaveApplicationPortal.DTO.LoginDTO;
import com.example.LeaveApplicationPortal.DTO.UserDTO;
import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.Holidays;
import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.HolidayRepo;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class ServiceImpl implements Services {

    private final UserRepo userRepo;
    private final LeaveRepo leaveRepo;
    private final HolidayRepo holidayRepo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ServiceImpl(UserRepo userRepo, LeaveRepo leaveRepo, HolidayRepo holidayRepo, MongoTemplate mongoTemplate) {
        this.userRepo = userRepo;
        this.leaveRepo = leaveRepo;
        this.holidayRepo = holidayRepo;
        this.mongoTemplate = mongoTemplate;
    }

    //Factory method to create LoginResponse objects
    private LoginResponse createLoginResponse(String message, boolean status) {
        return new LoginResponse(message, status);
    }


    public LoginResponse loginUser(LoginDTO loginDto) {
        User user1 = userRepo.findByUserid(loginDto.getUserid());
        if(loginDto.getUserid() == null || loginDto.getUserid().isEmpty() || loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            return createLoginResponse("All fields are mandatory", false);
        }
        Optional<User> userOptional = userRepo.findOneByUseridAndPassword(loginDto.getUserid(), loginDto.getPassword());
        if (userOptional.isPresent()) {
            if (Objects.equals(user1.getCategory(), "Management")) {
                return createLoginResponse("Login as Management", true);
            }
            else {
                return createLoginResponse("Login as Employee", true);
            }
        }
        else {
            return createLoginResponse("Password incorrect", false);
        }
    }

    public LoginResponse saveUser(UserDTO userDto) {
        User user = new User(
                userDto.getUsername(),
                userDto.getUserid(),
                userDto.getEmail(),
                userDto.getRole(),
                userDto.getPassword(),
                userDto.getCategory()
        );
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        boolean isValidEmail = pattern.matcher(userDto.getEmail()).matches();
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty() || userDto.getUserid() == null || userDto.getUserid().isEmpty() || userDto.getEmail() == null || userDto.getEmail().isEmpty() || userDto.getPassword() == null || userDto.getPassword().isEmpty() || userDto.getCategory() == null || userDto.getCategory().isEmpty()) {
            return createLoginResponse("All fields are mandatory", false);
        }
        User user1 = userRepo.findByUserid(userDto.getUserid());
        if(user1 != null) {
            return createLoginResponse("User ID already exist", false);
        }
        if (!isValidEmail) {
            return createLoginResponse("Please enter valid email address", false);
        }
        if (userDto.getPassword().length() < 6) {
            return createLoginResponse("Password should contain at least 6 characters", false);
        }
        if (userDto.getPassword().length() > 12) {
            return createLoginResponse("Password cannot exceed more than 12 characters", false);
        }
        else {
            userRepo.save(user);
            return createLoginResponse("Sign up successful", true);
        }
    }


    public User get(String userid) {
        return userRepo.findByUserid(userid);
    }

    public LoginResponse saveLeave(LeaveDTO leaveDto, MultipartFile file) {
        try {
            Leave leave1 = new Leave();
            leave1.set_id(leaveDto.get_id());
            leave1.setUserid(leaveDto.getUserid());
            leave1.setUsername(leaveDto.getUsername());
            leave1.setLeaveType(leaveDto.getLeaveType());
            leave1.setStartDate(leaveDto.getStartDate());
            leave1.setEndDate(leaveDto.getEndDate());
            leave1.setCount(leaveDto.getCount());
            leave1.setMsg(leaveDto.getMsg());
            leave1.setApprover(leaveDto.getApprover());
            leave1.setStatus(leaveDto.getStatus());
            if (file == null || file.isEmpty()) {
                leave1.setFile(null);
            }
            else {
                leave1.setFile(file.getBytes());
            }
            if(leave1.getLeaveType() == null || leave1.getLeaveType().isEmpty() || leave1.getStartDate() == null || leave1.getStartDate().isEmpty() || leave1.getEndDate() == null || leave1.getEndDate().isEmpty() || leave1.getMsg() == null || leave1.getMsg().isEmpty() || leave1.getApprover() == null || leave1.getApprover().isEmpty()) {
                return createLoginResponse("All fields are mandatory", false);
            }
            List<Leave> leave2 = leaveRepo.findByUseridAndStatus(leave1.getUserid(), "Pending");
            if (leave2.isEmpty()) {
                leaveRepo.save(leave1);
                return createLoginResponse("Leave applied", true);
            }
            else {
                return createLoginResponse("Your leave request is in pending status", false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return createLoginResponse("Error occurred while processing file", false);
        }

    }



    public List<UsernameDTO> getUsernamesByCategoryManagement(String username) {
        List<UsernameDTO> entities = userRepo.findUsernamesByCategoryManagement();
        entities = entities.stream()
                .filter(entity -> !entity.getUsername().equals(username))
                .collect(Collectors.toList());
        return entities;
    }

    public List<Leave> getLeaveRecords(String status, String search) {
            if (Objects.equals(status, "All") && search.isEmpty()) {
                return leaveRepo.findAll();
            }
            if (!Objects.equals(status, "All") && search.isEmpty()) {
                return leaveRepo.findByStatus(status);
            }
            else {
               return leaveRepo.findByUseridContainingIgnoreCase(search);
            }
    }


    public List<Leave> getLeaveRequestRecords(String approver) {
        return leaveRepo.findByStatusAndApprover("Pending", approver);
    }

    public LoginResponse updateLeaveRequest(String userid, String newStatus) {
        Query query = createUpdateQuery(userid);
        Update update = new Update().set("status", newStatus);
        mongoTemplate.updateFirst(query, update, Leave.class);
        return createLoginResponse("Updated", true);
    }

    public List<Holidays> getHolidayRecords() {
        return holidayRepo.findAll();
    }


    public List<Leave> getEmpLeave(String userid, String status) {
        if (Objects.equals(status, "All")) {
            return leaveRepo.findByUserid(userid);
        }
        else {
            return leaveRepo.findByUseridAndStatus(userid, status);
        }
    }

    public LoginResponse changePassword(String userid, String currentPwd, String newPwd) {
        Query query = new Query(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("password").is(currentPwd));
        Update update = new Update().set("password", newPwd);
        mongoTemplate.updateFirst(query, update, User.class);
        User user = userRepo.findByUserid(userid);
        if(Objects.equals(user.getPassword(), newPwd)) {
            return createLoginResponse("Password changed", true);
        }
        return createLoginResponse("Wrong password", false);
    }

    public LoginResponse forgotPassword(String userid, String email) {
        LoginResponse user = userRepo.findByUseridAndEmail(userid, email);
        if(user != null){
            return createLoginResponse("userid found", true);
        }
        return createLoginResponse("userid not found", false);
    }

    public LoginResponse changePwd(String userid, String password) {
        Query query = new Query(Criteria.where("userid").is(userid));
        Update update = new Update().set("password", password);
        mongoTemplate.updateFirst(query, update, User.class);
        return createLoginResponse("Password changed", true);
    }

    public int sumIntValues(String userid, String leaveType) {
        List<Leave> leave = leaveRepo.findByUseridAndLeaveType(userid, leaveType);
        int sum = 0;
        for (Leave leave1 : leave) {
            if(Objects.equals(leave1.getStatus(), "Accepted")) {
                sum += leave1.getCount();
            }
        }
        return sum;
    }

    public List<User> getUser() {
        return userRepo.findAll();
    }

    public LoginResponse deleteUser(String userid) {
        userRepo.deleteByUserid(userid);
        return createLoginResponse("deleted successfully", true);
    }

    public byte[] getFile(String id) {
        Optional<Leave> leave = leaveRepo.findById(id);
        return leave.map(Leave::getFile).orElse(null);
    }

    public List<Leave> editLeave(String userid) {
        return leaveRepo.findByUseridAndStatus(userid, "Pending");
    }

    public LoginResponse updateLeaveForm(Map<String, String> formData, MultipartFile file) {
        try {
            Query query = createUpdateQuery(formData.get("userid"));
            Update update = new Update().set("leaveType", formData.get("leaveType")).set("startDate", formData.get("startDate")).set("endDate", formData.get("endDate")).set("count", formData.get("count")).set("msg", formData.get("msg")).set("approver", formData.get("approver"));
            if (file == null || file.isEmpty()) {
                update.set("file", null);
            }
            else {
                update.set("file", file.getBytes());
            }
            mongoTemplate.updateFirst(query, update, Leave.class);
            return createLoginResponse("Leave updated successfully", true);
        }
        catch (IOException e) {
            e.printStackTrace();
            return createLoginResponse("Error occurred while processing file", false);
        }

    }

    @Override
    public LoginResponse deleteLeave(String _id) {
        leaveRepo.deleteBy_id(_id);
        return new LoginResponse("Leave request deleted successfully", true);
    }


    


    //subclasses
    
    protected Query createUpdateQuery(String userid) {
        return new Query(Criteria.where("userid").is(userid)).addCriteria(Criteria.where("status").is("Pending"));
    }


}
