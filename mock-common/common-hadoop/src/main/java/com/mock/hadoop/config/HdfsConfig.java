package com.mock.hadoop.config;

/**
 * HDFS 配置类
 * @author zhao
 * @since 2022-08-04 10:27
 */
public class HdfsConfig {

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
