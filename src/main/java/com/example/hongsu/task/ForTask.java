package com.example.hongsu.task;
/**
 *  @author:WJ
 *  @date: 2020-05-25 15:59
 *  @describe:
 **/

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class ForTask implements Callable<Integer> {

    private Integer num;


    public ForTask(Integer num ){
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = num;
        for(int i=0;i<100;i++) {
            sum += i;
        }
        return sum;
    }
}
