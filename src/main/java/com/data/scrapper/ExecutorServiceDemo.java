package com.data.scrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            for (int i = 1; i < 100; i++) {
                final int x = i;
                System.out.println("for:" + i);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("value " + x);
                   /* try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }


    }
}
