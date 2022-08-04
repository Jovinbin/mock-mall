package com.mock.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 客户端代码
 * 1、获取一个客户端对象
 * 2、执行相关的操作命令
 * 3、关闭资源
 * HDFS     zookeeper
 *
 * @author zhao
 * @since 2022-08-03 15:54
 */
public class HdfsClient {

    @Test
    public void testMkdir() throws URISyntaxException, IOException, InterruptedException {
        // 连接的集群nn地址
        URI uri = new URI("hdfs://zjb:8020");
        // 创建一个配置文件
        Configuration configuration = new Configuration();

        // 用户
        String user = "root";

        // 1 获取到了客户端对象
        FileSystem fs = FileSystem.get(uri, configuration, user);

        // 2 创建一个文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan"));

        // 3 关闭资源
        fs.close();

    }

}
