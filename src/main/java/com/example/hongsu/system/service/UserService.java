package com.example.hongsu.system.service;/* *
 *  @author:WJ
 *  @date: 2020-04-03 15:52
 *  @describe:
 * */

import com.example.hongsu.comm.aop.vo.Result;
import com.example.hongsu.system.entity.UserBean;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String addUser(UserBean userBean) {
        // 直接编写业务逻辑
        return "success";
    }

    public UserBean findUserById(String id) {
        UserBean userBean = new UserBean();
        userBean.setId(Long.valueOf(id));
        // 直接编写业务逻辑
        return commReturnBean(userBean);
    }

    public UserBean findByUseBean(UserBean userBean) {
        return commReturnBean(userBean);
    }

    public UserBean findByUsername(String username) {
        UserBean userBean = new UserBean();
        userBean.setUserName(username);
        return commReturnBean(userBean);
    }

    public UserBean commReturnBean(UserBean userBean){
        UserBean userBean1 = new UserBean();
        userBean1.setId(100L);
        userBean1.setUserName("admin");
        userBean1.setAccount("测试");
        userBean1.setPassword("123456");
        if("admin".equals(userBean.getUserName())){
            return userBean1;
        }else{
            return null;
        }
    }

    public Result<?> checkUserIsEffective(UserBean userBean) {
        Result<?> result = new Result<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (userBean == null) {
            result.error500("该用户不存在，请注册");
            return result;
        }
        if(!userBean.equals(commReturnBean(userBean).getPassword())){
            result.error500("密码错误！");
        }
        return result;
    }
}
