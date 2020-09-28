package com.example.hongsu.system.dao;/* *
 *  @author:WJ
 *  @date: 2020-05-23 14:45
 *  @describe:
 * */

import com.example.hongsu.system.entity.EasyExcelData;

import java.util.List;

public class EasyExcelDataDao {

    public void save(List<EasyExcelData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}
