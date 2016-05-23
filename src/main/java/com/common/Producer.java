package com.common;

import java.util.concurrent.BlockingQueue;

/**
 * Created by huhaosumail on 16/5/12.
 */
public class Producer implements Runnable{

    protected BlockingQueue queue = null;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("3");
            Thread.sleep(5000);
            queue.put("2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
