package com.victor.zk.distrilock.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * curator test
 * Created by likai on 2016/11/16.
 */
public class CuratorTest {
    @Test
    public void test() throws Exception {
        List<String> zkCluster = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            zkCluster.add("localhost:218"+i);
        }
        String zkClusterHosts = zkCluster.toString();
        zkClusterHosts = zkClusterHosts.substring(1, zkClusterHosts.length()-1);
        zkClusterHosts = zkClusterHosts.replaceAll(" ", "");
        final CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkClusterHosts)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .namespace("message")
                .defaultData(null)
                .build();
        client.start();
        InterProcessMutex exclusiveLock = new InterProcessMutex(client, "/barrier/competitorNode");
//        exclusiveLock.

        /*new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    client.close();
                }catch (Throwable e){
                    System.err.println(e);
                }
            }
        }.start();
        Thread.sleep(5000);*//*
        client.create().
        client.create().forPath("/barrier/competitorNode");*/
    }
}
