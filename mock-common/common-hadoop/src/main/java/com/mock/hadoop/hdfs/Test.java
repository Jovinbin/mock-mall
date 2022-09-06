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

        Configuration configuration = new Configuration();
        configuration.set(HdfsConfig.DFS_REPLICATION, "1");//设置副本数为一
        configuration.set(HdfsConfig.DSF_CLIENT_USE_DATANODE_HOSTNAME, "true");
        HdfsClient hdfsClient = new HdfsClient(new URI("hdfs://zjb:8020"), configuration, "root");

        // 创建文件夹
//        hdfsClient.mkdir("/xiyou/huaguoshan");

        // 上传文件
//        hdfsClient.putFile(false, true, "F:/test/mysql.status", "/xiyou/huaguoshan");

        //下载文件
//        boolean b = hdfsClient.downloadFile(false, "hdfs://zjb:8020/xiyou/huaguoshan", "F:/test", true);

        //删除文件
//        boolean b = hdfsClient.delFile("/input", false);

        //移动或更名文件
//        boolean b = hdfsClient.renameOrMoveFile("/input/b.txt", "/c.txt");

        //获取文件信息
//        hdfsClient.getFileDetail("/", true);

        hdfsClient.getFileDetailExample();

        //判断是文件夹还是文件
//        hdfsClient.isFileOrDir("/");


//        System.out.println("b = " + b);

        hdfsClient.close();


    }

}
