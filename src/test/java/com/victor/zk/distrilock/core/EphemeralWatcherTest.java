package com.victor.zk.distrilock.core;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * test feature of ephemeral node
 * Created by likai on 2016/11/15.
 */
public class EphemeralWatcherTest {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException, BrokenBarrierException {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        ZooKeeper zkClient = new ZooKeeper("127.0.0.1:2180,127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185,127.0.0.1:2186,127.0.0.1:2187", 5000,
                new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if (event.getState().equals(Event.KeeperState.SyncConnected)) {
//                            latch.countDown();
                        }
                    }
                });

        zkClient.delete("/message/barrier", -1);

        long timeMills = System.currentTimeMillis();
        String root = zkClient.create("/message", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("create "+ root +" 耗时 "+(System.currentTimeMillis()-timeMills));
        String node = zkClient.create("/message/barrier", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        timeMills = System.currentTimeMillis();
        System.out.println("create "+ node +" 耗时 "+(System.currentTimeMillis()-timeMills));

        timeMills = System.currentTimeMillis();
        final String tmpNode1 = zkClient.create("/message/barrier/competitorNode", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create "+ tmpNode1 +" 耗时 "+(System.currentTimeMillis()-timeMills));
        timeMills = System.currentTimeMillis();
        final String tmpNode2 = zkClient.create("/message/barrier/competitorNode", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create "+ tmpNode2 +" 耗时 "+(System.currentTimeMillis()-timeMills));

        Stat exists = zkClient.exists(tmpNode1, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    if (event.getType().equals(Event.EventType.NodeDeleted) && event.getPath().equals(tmpNode1)) {
                        cyclicBarrier.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("exists = " + exists);

        if (exists == null) {
            //得到锁
        } else {
            cyclicBarrier.await();
        }
        System.out.println("I got lock");
    }
}
