package com.example.hongsu.quartz.service.impl;

import com.example.hongsu.quartz.entity.ScheduledTask;
import com.example.hongsu.quartz.mapper.ScheduledTaskMapper;
import com.example.hongsu.quartz.service.IScheduledTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Service
public class ScheduledTaskServiceImpl extends ServiceImpl<ScheduledTaskMapper, ScheduledTask> implements IScheduledTaskService {

}
