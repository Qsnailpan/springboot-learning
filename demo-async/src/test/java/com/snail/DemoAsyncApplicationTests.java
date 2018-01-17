package com.snail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoAsyncApplicationTests {

    @Autowired
    private Task task;

    @Test
    public void contextLoads() {

    }

    @Test
    public void doTask() throws InterruptedException {
        long start = System.currentTimeMillis ();

        Future<String> task1 = null;
        Future<String> task2 = null;
        Future<String> task3 = null;
        try {
            task1 = task.doTaskOne ();
            task2 = task.doTaskTwo ();
            task3 = task.doTaskThree ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        while (true) {
            if (task1.isDone () && task2.isDone () && task3.isDone ()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep (1000);
        }

        long end = System.currentTimeMillis ();

        System.out.println ("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}
