package com.example.hongsu.quartz.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hongsu.quartz.entity.ScheduledTask;
import com.example.hongsu.quartz.service.impl.ScheduledTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author WJ
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/quartz/scheduled-task")
public class ScheduledTaskController {

    @Autowired private ScheduledTaskServiceImpl scheduledTaskService;

    @PostMapping("/list")
    public List<ScheduledTask> list(){
        List<ScheduledTask> scheduledTasks = scheduledTaskService.list();
        return scheduledTasks;
    }

    @PostMapping("/listSql")
    public List<ScheduledTask> selectSqlByParam(String taskKey) {
        return scheduledTaskService.selectSqlByParam(taskKey);
    }

    @PostMapping("listPage")
    public Object selectListPage(Integer pageNum,Integer pageSize){
        QueryWrapper<ScheduledTask> wrapper = new QueryWrapper<>();
        Page<ScheduledTask> page = new Page<>(pageNum, pageSize);
        IPage<ScheduledTask> iPage = scheduledTaskService.page(page,wrapper);
        return iPage;
    }

    @PostMapping("listSqlLam")
    public Object selectListSqlLam(String param){
        LambdaQueryWrapper<ScheduledTask> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(ScheduledTask::getTaskKey,param);
        List<ScheduledTask> scheduledTasks = scheduledTaskService.selectListSqlLam(lambdaQuery);
        scheduledTasks.forEach(System.out::println);
        return scheduledTasks;
    }

    @PostMapping("selectPageXml")
    public Object selectPageXml(String param){
        QueryWrapper<ScheduledTask> query = Wrappers.query();
        query.like("task_key", param);
        Page<ScheduledTask> page = new Page<>(1, 3);
        IPage<ScheduledTask> taskIPage = scheduledTaskService.selectPageXml(page, query);
//        IPage<Map<String, Object>> userIPage = userMapper.selectUserPage(page, query);
        System.out.println("总记录数："+taskIPage.getTotal());
        System.out.println("总页数："+taskIPage.getPages());
        List<ScheduledTask> users = taskIPage.getRecords();
//        List<Map<String, Object>> users = userIPage.getRecords();
        users.forEach(System.out::println);
        return query;
    }

    @PostMapping("selectPageXmlMap")
    public Object selectPageXmlMap(String param){
        Page<ScheduledTask> page = new Page<>(1, 3);
        IPage<Map<String, Object>> taskIPage = scheduledTaskService.selectPageXmlMap(page, param);
//        IPage<ScheduledTask> userIPage = userMapper.selectUserPage2(page, 30);
        System.out.println("总记录数："+taskIPage.getTotal());
        System.out.println("总页数："+taskIPage.getPages());
        List<Map<String, Object>> users = taskIPage.getRecords();
//        List<ScheduledTask> users = userIPage.getRecords();
        users.forEach(System.out::println);
        return taskIPage;
    }
}
