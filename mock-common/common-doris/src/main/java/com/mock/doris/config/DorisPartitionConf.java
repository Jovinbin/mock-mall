package com.mock.doris.config;

/**
 * Doris 分区配置
 * @author zhao
 * @since 2022-08-08 16:38
 */
public class DorisPartitionConf {

    /**
     * 是否开启动态分区特性，可指定 true 或 false,默认为 true
     */
    public final static String DYNAMIC_PARTITION_ENABLE = "dynamic_partition.enable";

    /**
     * 动态分区调度的单位，可指定 HOUR、DAY、WEEK、MONTH。
     * HOUR，后缀格式为 yyyyMMddHH，分区列数据类型不能为DATE。
     * DAY，后缀格式为 yyyyMMdd。
     * WEEK，后缀格式为 yyyy_ww。即当前日期属于这一年的第几周。
     * MONTH，后缀格式为 yyyyMM。
     */
    public final static String DYNAMIC_PARTITION_TIME_UNIT = "dynamic_partition.time_unit";

    /**
     * -- 保留多少天 --
     * 动态分区的起始偏移，为负数。根据 time_unit 属性的不同，以当
     * 天（星期/月）为基准，分区范围在此偏移之前的分区将会被删除。
     * 如果不填写默认值为 Integer.Min_VALUE 即-2147483648，即不
     * 删除历史分区
     */
    public final static String DYNAMIC_PARTITION_START = "dynamic_partition.start";

    /**
     * 动态分区的结束偏移，为正数。根据 time_unit 属性的不同，以当
     * 天（星期/月）为基准，提前创建对应范围的分区
     */
    public final static String DYNAMIC_PARTITION_END = "dynamic_partition.end";

    /**
     * 动态创建的分区名前缀
     */
    public final static String DYNAMIC_PARTITION_PREFIX = "dynamic_partition.prefix";

    /**
     * 动态创建的分区所对应分桶数量
     */
    public final static String DYNAMIC_PARTITION_BUCKETS = "dynamic_partition.buckets";

    /**
     * 默认为 false。当置为 true 时，Doris 会自动创建所有分区，当期望创建的分区个数大
     * 于 max_dynamic_partition_num 值时，操作将被禁止。当不指定 start 属性时，该参数不生效
     */
    public final static String DYNAMIC_PARTITION_CREATE_HISTORY_PARTITION = "dynamic_partition.create_history_partition";

    /**
     * 表示创建多少个历史分区（默认创建所有）
     * 当 create_history_partition 为 true 时，该参数用于指定创建历史分区数量。默认值为 - 1， 即未设置。
     */
    public final static String DYNAMIC_PARTITION_HISTORY_PARTITION_NUM = "dynamic_partition.history_partition_num";

    /**
     * 副本数量
     */
    public final static String REPLICATION_NUM = "replication_num";

    /**
     * 动态创建的分区所对应的副本数量，如果不填写，则默认为该表创建时指定的副本数量。
     */
    public final static String DYNAMIC_PARTITION_REPLICATION_NUM = "dynamic_partition.replication_num";

}
