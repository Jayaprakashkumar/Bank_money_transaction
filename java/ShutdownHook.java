//package com.course.example;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ShutdownHook extends Thread {
    private final ExecutorService executorService;

    public ShutdownHook(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run(){
        System.out.printf("Application getting shutdown");
        executorService.shutdown();
        try {
            executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdownNow();
        }
        System.out.printf("Application shutdown completed");
    }
}