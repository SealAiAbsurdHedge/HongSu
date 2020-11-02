package com.example.hongsu.quartz.controller;


import com.example.hongsu.quartz.entity.ScheduledTask;
import com.example.hongsu.quartz.service.impl.ScheduledTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WJ
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/quartz/scheduled-task")
public class ScheduledTaskController {

    @Autowired private ScheduledTaskServiceImpl scheduledTaskService;

    @RequestMapping("/list")
    public List<ScheduledTask> list(){
        List<ScheduledTask> scheduledTasks = scheduledTaskService.list();
        return scheduledTasks;
    }
}
