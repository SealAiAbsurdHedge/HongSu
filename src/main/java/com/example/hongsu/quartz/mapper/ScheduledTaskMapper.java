package com.example.hongsu.quartz.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hongsu.quartz.entity.ScheduledTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WJ
 * @since 2020-11-02
 */
public interface ScheduledTaskMapper extends BaseMapper<ScheduledTask> {

    @Select("select * from scheduled_task where task_key = #{taskKey}")
    List<ScheduledTask> selectSqlByParam(@Param("taskKey") String taskKey);

    List<ScheduledTask> selectSqlByParamXml(@Param("taskKey") String taskKey);

    //注解式
    @Select("select * from scheduled_task ${ew.customSqlSegment}")
    List<ScheduledTask> selectListSqlLam(@Param(Constants.WRAPPER) Wrapper<ScheduledTask> wrapper);

    //xml文件式，mybatis-plus方式
    List<ScheduledTask> selectAll2(@Param(Constants.WRAPPER)Wrapper<ScheduledTask> wrapper);

    //xml文件式，mybatis原生方式
    List<ScheduledTask> selectAll3(ScheduledTask scheduledTask);

    //自定义分页查询，方式一
    IPage<ScheduledTask> selectPageXml(Page<ScheduledTask> page, @Param(Constants.WRAPPER)Wrapper<ScheduledTask> wrapper);
    //返回map类型
//    IPage<Map<String, Object>> selectUserPage(Page<ScheduledTask> page, @Param(Constants.WRAPPER)Wrapper<ScheduledTask> wrapper);

    //自定义分页查询，方式二
    IPage<Map<String, Object>> selectPageXmlMap(Page<ScheduledTask> page, @Param("param") String param);
    //返回对象类型
//    IPage<User> selectUserPage2(Page<ScheduledTask> page, @Param("age") Integer age);
}
