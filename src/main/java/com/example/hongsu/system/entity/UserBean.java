package com.example.hongsu.system.entity;/* *
 *  @author:WJ
 *  @date: 2020-04-03 15:47
 *  @describe:
 * */

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserBean {

    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotNull(message = "用户id不能为空")
    private String userName;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    public UserBean(){};
    public UserBean(String userName,String password){
        this.userName=userName;
        this.password=password;
    }
}
