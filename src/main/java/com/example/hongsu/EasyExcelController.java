package com.example.hongsu;/* *
 *  @author:WJ
 *  @date: 2020-05-23 14:50
 *  @describe:
 * */

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.example.hongsu.system.EasyExcelFileUtil;
import com.example.hongsu.system.entity.EasyExcelData;
import com.example.hongsu.system.entity.UserBean;
import com.example.hongsu.system.service.DemoDataListener;
import com.example.hongsu.task.ForTask;
import com.example.hongsu.util.RSAUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateKey;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.*;

@Ignore
public class EasyExcelController {

    public List<EasyExcelData> dataList() {
        List<EasyExcelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 1.0 + i);
            list.add(easyExcelData);
        }
        return list;
    }

    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = EasyExcelFileUtil.getPath() + "demo" + File.separator + "demo.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        //EasyExcel.read(fileName, EasyExcelData.class, new DemoDataListener()).sheet().doRead();
        // 写法2：
        fileName = EasyExcelFileUtil.getPath() + "demo" + File.separator + "demo.xls";
        ExcelReader excelReader = EasyExcel.read(fileName, EasyExcelData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    @Test
    public void oldTest() {
        long a1 = System.currentTimeMillis();
        List<EasyExcelData> list = new ArrayList<>();
        for (long i = 0L; i < 100000000L; i++) {
            EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
        }
        for (long i = 0L; i < 10000000L; i++) {
            EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
        }
        for (long i = 0L; i < 10000000L; i++) {
            EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
        }
        for (long i = 0L; i < 10000000L; i++) {
            EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
        }
        long a2 = System.currentTimeMillis();
        System.out.println(a2 - a1);
    }


    @Test
    public void executorsTest() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        long a1 = System.currentTimeMillis();
        Runnable test1 = () -> {
            for (long i = 0L; i < 100000000L; i++) {
                EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
            }
        };
        Runnable test2 = () -> {
            for (long i = 0L; i < 100000000L; i++) {
                EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
            }
        };
        Runnable test3 = () -> {
            for (long i = 0L; i < 100000000L; i++) {
                EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
            }
        };
        Runnable test4 = () -> {
            for (long i = 0L; i < 100000000L; i++) {
                EasyExcelData easyExcelData = new EasyExcelData(String.valueOf(i), new Date(), 2.0);
            }
        };
        cachedThreadPool.execute(test1);
        cachedThreadPool.execute(test2);
        cachedThreadPool.execute(test3);
        cachedThreadPool.execute(test4);
        try {
            cachedThreadPool.shutdown();
            cachedThreadPool.awaitTermination(5, TimeUnit.MINUTES);

        } catch (Exception e) {
            e.printStackTrace();
        }
        long a2 = System.currentTimeMillis();
        System.out.println(a2 - a1);
    }

    @Test
    public void executorsTest1() {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        List<ForTask> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ForTask task = new ForTask(i);
            list.add(task);
        }
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
        for (ForTask task : list) {
            Future<Integer> future = executor.submit(task);
            resultList.add(future);
        }
        try {
            for (Future future : resultList) {
                Integer objMap = (Integer) future.get();
                System.out.println(objMap);
            }
        } catch (Exception e) {

        } finally {
            executor.shutdown();
        }
    }

    @Test
    public void makeFile() {
        File file = new File("D:\\upload\\makeFile");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void intputExcel() throws FileNotFoundException {
        String templateFileName =
                EasyExcelFileUtil.getPath() + "员工信息.xlsx";

        // 方案1 根据对象填充
        String fileName = EasyExcelFileUtil.getPath() + "员工信息" + System.currentTimeMillis() + ".xlsx";

        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        //EasyExcelData easyExcelData = new EasyExcelData("1",new Date(),1.0);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(dataList());
    }

    @Test
    public void intputExcelMap() {
        String templateFileName =
                EasyExcelFileUtil.getPath() + "员工信息.xlsx";

        // 方案1 根据对象填充
        String fileName = EasyExcelFileUtil.getPath() + "员工信息" + System.currentTimeMillis() + ".xlsx";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("string", "张三");
        map.put("date", new Date());
        map.put("doubleData", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    @Test
    public void intputExcelLists() {
        String templateFileName =
                EasyExcelFileUtil.getPath() + "员工信息.xlsx";

        // 方案2 分多次 填充 会使用文件缓存（省内存）
        String fileName = EasyExcelFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(dataList(), writeSheet);
        excelWriter.fill(dataList(), writeSheet);
        // 千万别忘记关闭流
        excelWriter.finish();
    }

    /**
     * Future的线程阻塞问题
     */
    @Test
    public void fuTureVoid() throws ExecutionException, InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        List<Future<UserBean>> list = new ArrayList<>();
        Future<UserBean> shiXinDataThread = cachedThreadPool.submit(() -> {
            UserBean user = new UserBean("admin", "123");
            System.out.println("先走这");
            return user;
        });
        shiXinDataThread.get();
        list.add(shiXinDataThread);
        cachedThreadPool.shutdown();
        System.out.println(JSONObject.toJSON(list.get(0).get()));
    }

    @Test
    public void runnbaleVoid() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Runnable shiXinDataThread = () -> {
            System.out.println("可能没走完");
        };
        cachedThreadPool.execute(shiXinDataThread);
        //shiXinDataThread.run();
        cachedThreadPool.shutdown();
        System.out.println("走完了");
    }

    /**
     * Futrue线程等菜模式
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // 等凉菜
        Callable ca1 = new Callable() {

            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "凉菜准备完毕";
            }
        };
        FutureTask<String> ft1 = new FutureTask<String>(ca1);
        new Thread(ft1).start();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Callable ca2 = new Callable() {

            @Override
            public Object call() throws Exception {
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "包子准备完毕";
            }
        };
        FutureTask<String> ft2 = new FutureTask<String>(ca2);
        new Thread(ft2).start();

        System.out.println(ft1.get());
        System.out.println(ft2.get());

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间：" + (end - start));
    }

    /**
     * FutrueTask线程模式
     */
    @Test
    public void test2() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ForTask forTask = new ForTask(1);
        Future<Integer> future= service.submit(forTask);
        FutureTask<Integer> futureTask = new FutureTask<>(forTask);
        service.execute(futureTask);
        System.out.println(futureTask.get());
    }

    /**
     * BouncyCastle加密、解密
     */
    @Test
    public void test3() {
        HashMap<String, Object> keys = new HashMap<>();
        {
            try {
                keys = RSAUtils.getKeys();
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
        RSAPrivateKey privateKey = (RSAPrivateKey) keys.get("private");
        System.out.println();
    }

    @Test
    public void test4(){
        Date date = new Date();
        System.out.println(date);
    }
}
