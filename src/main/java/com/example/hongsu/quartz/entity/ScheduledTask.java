package com.example.hongsu.quartz.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Data
@TableName("scheduled_task")
public class ScheduledTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务key值（使用bean名称）
     */
    private String taskKey;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 任务表达式
     */
    private String taskCron;

    /**
     * 程序初始化是否启动 1 是 0 否
     */
    private Integer initStartFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
