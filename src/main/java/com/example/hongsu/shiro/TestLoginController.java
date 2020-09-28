package com.example.hongsu.shiro;

import com.alibaba.fastjson.JSONObject;
import com.example.hongsu.comm.aop.vo.Result;
import com.example.hongsu.comm.constant.CommonConstant;
import com.example.hongsu.system.entity.UserBean;
import com.example.hongsu.system.service.UserService;
import com.example.hongsu.util.JwtUtil;
import com.example.hongsu.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  @author:WJ
 *  @date: 2020-04-07 9:57
 *  @describe:  测试登陆功能
 */
@RestController
@RequestMapping("/api")
public class TestLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenRule tokenRule;
    @Autowired
    private RedisUtil redisUtil;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    //登录
    @PostMapping("/login")
    public Result<JSONObject> login(@RequestParam(value = "userName") String userName,
                                    @RequestParam(value = "passWord") String passWord,
                                    @RequestParam(value = "captcha") String captcha) {
        JSONObject jsonObject = new JSONObject();
        Result<JSONObject> result = new Result<>();
        if(captcha==null){
            result.error500("验证码无效");
            return result;
        }
        result = (Result<JSONObject>) userService.checkUserIsEffective(new UserBean(userName, passWord));
        userInfo(new UserBean(userName, passWord), result);
        return result;
    }

    /**
     * 将登陆用户存入redis用于后续权限操作
     */
    private Result<com.alibaba.fastjson.JSONObject> userInfo(UserBean userBean, Result<com.alibaba.fastjson.JSONObject> result) {
        String password = userBean.getPassword();
        String username = userBean.getUserName();
        // 生成token
        String token = JwtUtil.sign(username, password);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        // 获取用户部门信息
        com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
        obj.put("token", token);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

    /**
     * 后台生成图形验证码
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码2")
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletResponse response, @PathVariable String key) {
        Result<String> res = new Result<String>();
        try {
            /*String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);*/
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    //@UserLoginToken//用于权限测试
    @GetMapping("/getMessage")
    @ResponseBody
    public String getMessage(HttpServletResponse response) {
        return "success";
    }

}
