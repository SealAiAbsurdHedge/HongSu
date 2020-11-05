package com.example.hongsu.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hongsu.quartz.entity.ScheduledTask;
import com.example.hongsu.quartz.mapper.ScheduledTaskMapper;
import com.example.hongsu.quartz.service.IScheduledTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 阿里巴巴最新依赖注入方式替换@Autowired
     */
    final ScheduledTaskMapper scheduledTaskMapper;
    public ScheduledTaskServiceImpl(ScheduledTaskMapper scheduledTaskMapper) {
        this.scheduledTaskMapper = scheduledTaskMapper;
    }

    @Override
    public List<ScheduledTask> selectSqlByParam(String taskKey) {
        if(StringUtils.isEmpty(taskKey)){
            return scheduledTaskMapper.selectSqlByParamXml(taskKey);
        }
        return scheduledTaskMapper.selectSqlByParam(taskKey);
    }

    public List<ScheduledTask> selectListSqlLam(LambdaQueryWrapper<ScheduledTask> lambdaQuery) {
        return scheduledTaskMapper.selectListSqlLam(lambdaQuery);
    }

    public IPage<ScheduledTask> selectPageXml(Page<ScheduledTask> page, QueryWrapper<ScheduledTask> query) {
        return scheduledTaskMapper.selectPageXml(page,query);
    }

    public IPage<Map<String, Object>> selectPageXmlMap(Page<ScheduledTask> page, String param) {
        return scheduledTaskMapper.selectPageXmlMap(page,param);
    }
}
