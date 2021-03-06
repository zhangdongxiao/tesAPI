package apipack.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * apipack
 *
 * @author zhangdongxiao
 * @create 2018-11-29 10:50 AM
 * @desc
 */
public class ZkMsgTest {

    public static void main(String[] args) {

        String hostPort = "localhost:2182";
        String zpath = "/ZNODE_N1/Z1";

//        List<String> zooChildren;
//        ZooKeeper zk = null;
//        try {
//            zk = new ZooKeeper(hostPort, 2000, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (zk != null) {
//            try {
//                zooChildren = zk.getChildren(zpath, false);
//                System.out.println("Znodes of '/': ");
//                for (String child : zooChildren) {
//                    //print the children
//                    System.out.println(child);
//                }
//
//                //判断zk节点是否存在
//                Stat stat = zk.exists("/testpath", false);
//
//                System.out.println("Znode stat " + stat);
//
//                if (stat == null) {
//                    zk.create("/testpath", "this is test msg".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//                } else {
//                    System.out.println("version=" + stat.getVersion());
//
//                    //向zk写数据
//                    zk.setData("/testpath", ("this is test msg" + stat.getVersion()).getBytes(), stat.getVersion());
//                }
//
//                System.out.println("version=" + stat.getVersion());
//
//                byte[] data = zk.getData("/testpath", true, stat);
//
//                String dataStr = new String(data);
//
//                System.out.println(dataStr);
//
//            } catch (KeeperException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(hostPort)
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();

        String clientPath = zpath + "/Curator";

        client.start();

        try {
//            client.create().forPath(clientPath, "this is data".getBytes());
            if (client.checkExists().forPath(clientPath) == null) {
                client.create().creatingParentContainersIfNeeded().forPath(clientPath, "this is data".getBytes());
            } else {
                client.setData().forPath(clientPath, "this is data".getBytes());
            }
            byte[] data = client.getData().forPath(clientPath);
            String dataStr = new String(data);

            System.out.println("@@@@@@@@  dataStr = " + dataStr);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
