package com.victor.zk.distrilock.core;

import com.victor.zk.distrilock.core.serverImpl.ZkDistributedLock;

/**
 * Unit test for simple App.
 */
public class LockTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            new Thread() {
                public void run() {
                    try {
                        ZkDistributedLock zkDistributedLock = new ZkDistributedLock();
                        zkDistributedLock.connectZooKeeper("127.0.0.1:2181", "barrier");
                        zkDistributedLock.lock();

                        System.out.println(Thread.currentThread().getName()+ "在做事，做完就释放锁");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "我做完事情了");
                        zkDistributedLock.releaseLock();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
