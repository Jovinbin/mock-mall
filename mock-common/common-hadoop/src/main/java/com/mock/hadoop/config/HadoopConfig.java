package com.mock.hadoop.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author zhao
 * @since 2022-10-19 15:58
 */
@Configuration
@ConditionalOnProperty(name= {"hadoop.name-node", "hadoop.user"})
@Slf4j
public class HadoopConfig {

    @Value("${hadoop.name-node}")
    private String nameNode;

    @Value("${hadoop.user}")
    private String user;

    /**
     * Configuration conf=new Configuration（）；
     * 创建一个Configuration对象时，其构造方法会默认加载hadoop中的两个配置文件，
     * 分别是hdfs-site.xml以及core-site.xml，这两个文件中会有访问hdfs所需的参数值，
     * 主要是fs.default.name，指定了hdfs的地址，有了这个地址客户端就可以通过这个地址访问hdfs了。
     * 即可理解为configuration就是hadoop中的配置信息。
     * @return
     */
    @Bean("fileSystem")
    public FileSystem createFs() throws Exception{
        //读取配置文件
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set(FS_DEFAULT_FS, nameNode);
        URI uri = new URI(nameNode.trim());
        FileSystem fs = FileSystem.get(uri, conf, user);
        log.info("HDFS --》 fileSystem 初始化成功");
        return fs;
    }

    /**
     * 连接地址
     * example：hdfs://hdp-01:8020
     */
    public final static String FS_DEFAULT_FS =  "fs.defaultFS";

    /**
     * 配置副本个数
     * Integer
     */
    public final static String DFS_REPLICATION =  "dfs.replication";

    /**
     * 是否使用主机节点名称
     * true OR false
     */
    public final static String DSF_CLIENT_USE_DATANODE_HOSTNAME =  "dfs.client.use.datanode.hostname";

}
