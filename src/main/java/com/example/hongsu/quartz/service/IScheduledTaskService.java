package com.example.hongsu.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hongsu.quartz.entity.ScheduledTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
public interface IScheduledTaskService extends IService<ScheduledTask> {

    List<ScheduledTask> selectSqlByParam(String taskKey);
}
