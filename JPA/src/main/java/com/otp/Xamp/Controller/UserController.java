package com.otp.Xamp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.otp.Xamp.Entity.User;
import com.otp.Xamp.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return this.userService.getAllUser();
    }

    @GetMapping("user/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return this.userService.getUserById(id);
    }

    @PutMapping("/update-user/{id}")
    public String updateUserbyId(@PathVariable("id") Integer id,
                                 @RequestBody String userName,
                                 @RequestBody String password) {
        String message = null;
        String s = this.userService.updateUser(id, userName, password);
        if (!s.isEmpty()) {
            message = "Record update successfully  " + s;
        } else {
            message = "Record Not found!! Please check Entered Id....." + id.toString();
        }
        return message;
    }

    @DeleteMapping("delete-user/{id}")
    public String delelteUserById(@PathVariable("id") Integer id) {
        String message = "";
        String s = this.userService.deleteUserById(id);
        message = s;
        return message;
    }
}
