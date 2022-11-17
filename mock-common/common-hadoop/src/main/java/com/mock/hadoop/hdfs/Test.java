package com.mock.hadoop.hdfs;

import com.mock.hadoop.config.HdfsConfig;
import org.apache.hadoop.conf.Configuration;

import java.net.URI;

/**
 * @author zhao
 * @since 2022-08-03 16:40
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String nameNode = "node-01:8020,node-02:8020,node-03:8020";
        String nameServices = "mycluster";
//        String[] nameNodesAddr = {"node-01:8020","node-02:8020","node-03:8020"};
        Configuration conf = new Configuration();
//        configuration.set(HdfsConfig.DFS_REPLICATION, "1");//设置副本数为一
//        String[] namenodes = {"nn1","nn2"};
        // hdfs集群节点
        String[] nameNodesAddr = nameNode.split(",");
        // 节点nn个数
        String[] nameNodes = new String[nameNodesAddr.length];
        for (int i = 0; i < nameNodes.length; i++) {
            nameNodes[i] = "nn" + (i + 1);
        }
        String nodes = String.join(",", nameNodes);
        conf.set(HdfsConfig.FS_DEFAULT_FS, "hdfs://" + nameServices);
        conf.set("dfs.nameservices", nameServices);
        conf.set("dfs.ha.namenodes." + nameServices, nodes);
        for (int i = 0; i < nameNodesAddr.length; i++) {
            conf.set("dfs.namenode.rpc-address." + nameServices + "." + nameNodes[i], nameNodesAddr[i]);
        }
        conf.set("dfs.client.failover.proxy.provider." + nameServices,"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.set(HdfsConfig.DSF_CLIENT_USE_DATANODE_HOSTNAME, "true");
        HdfsClient hdfsClient = new HdfsClient(new URI("hdfs://mycluster:8020"), conf, "suntang");

        // 创建文件夹
//        hdfsClient.mkdir("/xiyou/huaguoshan");

        // 上传文件
        hdfsClient.putFile(false, true, "F:/test/test.status", "/xiyou/huaguoshan/test.status");

        //下载文件
//        boolean b = hdfsClient.downloadFile(false, "hdfs://zjb:8020/xiyou/huaguoshan", "F:/test", true);

        //删除文件
//        boolean b = hdfsClient.delFile("/input", false);

        //移动或更名文件
//        boolean b = hdfsClient.renameOrMoveFile("/xiyou/huaguoshan/test.status", "/xiyou/haha");

        //获取文件信息
//        hdfsClient.getFileDetail("/", true);

//        hdfsClient.getFileDetailExample();

        //判断是文件夹还是文件
//        hdfsClient.isFileOrDir("/xiyou");

        // 读取文件内容
//        String s = hdfsClient.readFile("/xiyou/haha/test.status");

//        System.out.println("b = " + b);
//        hdfsClient.test("");

//        InputStream fileInputStream = hdfsClient.getFileInputStream("hdfs://node-02:8020/xiyou/haha/test.status");

//        List<Path> allFilePath = hdfsClient.getAllFilePath("/xiyou/huaguoshan/");
//        String filePath = allFilePath.get(0).toString();
//        System.out.println("allFilePath.get(0).getName() = " + allFilePath.get(0).getName());
//        System.out.println("复制的文件是：{}" + filePath);
//        InputStream fileInputStream = hdfsClient.getFileInputStream(filePath);
//        hdfsClient.copyFile(fileInputStream, "/xiyou/dada/" + allFilePath.get(0).getName());
//        hdfsClient.close();


    }

}
