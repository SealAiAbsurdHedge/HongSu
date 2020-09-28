package com.example.hongsu.system.controller;/* *
 *  @author:WJ
 *  @date: 2020-04-03 15:50
 *  @describe:
 * */

import com.example.hongsu.system.entity.UserBean;
import com.example.hongsu.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value="/addUser")
    public String addUser(@RequestBody/*(required=false) */@Valid UserBean userBean, BindingResult bindingResult,@RequestParam(value = "account") String account) {
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        for (ObjectError error : bindingResult.getAllErrors()) {
            return error.getDefaultMessage();
        }
        return userService.addUser(userBean);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
