package com.victor.zk.distrilock.core;

import com.victor.zk.distrilock.core.serverImpl.ZkDistributedLock;

import java.util.ArrayList;
import java.util.List;

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
                        //String zkHosts  ="127.0.0.1:2181";
                        String zkHosts  ="127.0.0.1:2181";
                        List<String> zkCluster = new ArrayList<String>();
                        for (int i = 0; i < 8; i++) {
                            zkCluster.add("localhost:218"+i);
                        }
                        String zkClusterHosts = zkCluster.toString();
                        zkClusterHosts = zkClusterHosts.substring(1, zkClusterHosts.length()-1);
                        zkHosts = zkClusterHosts.replaceAll(" ", "");

                        ZkDistributedLock zkDistributedLock = new ZkDistributedLock();
                        zkDistributedLock.connectZooKeeper(zkHosts, "barrier");
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
