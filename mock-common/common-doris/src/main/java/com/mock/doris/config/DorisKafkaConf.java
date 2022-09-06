package com.mock.doris.config;

/**
 * @author zhao
 * @since 2022-08-09 9:08
 */
public class DorisKafkaConf {

    //   ------------   Properties  ------------    //

    /**
     * 期望的并发度。一个例行导入作业会被分成多个子任务执行。这个参数指定一个作业最
     * 多有多少任务可以同时执行。必须大于 0。默认为 3。 这个并发度并不是实际的并发度，实
     * 际的并发度，会通过集群的节点数、负载情况，以及数据源的情况综合考虑。
     */
    public final static String DESIRED_CONCURRENT_NUMBER = "desired_concurrent_number";

    /**
     * 每个子任务最大执行时间，单位是秒。范围为 5 到 60。默认为 10。
     */
    public final static String MAX_BATCH_INTERVAL = "max_batch_interval";

    /**
     * 每个子任务最多读取的行数。必须大于等于 200000。默认是 200000。
     */
    public final static String MAX_BATCH_ROWS = "max_batch_rows";

    /**
     * 每个子任务最多读取的字节数。单位是字节，范围是 100MB 到 1GB。默认是
     * 100MB。
     */
    public final static String MAX_BATCH_SIZE = "max_batch_size";

    /**
     * Broker load 导入可以开启 strict mode 模式。开启方式为 properties ("strict_mode" = "true") 。默认的 strict mode 为关闭。
     * strict mode 模式的意思是：对于导入过程中的列类型转换进行严格过滤。严格过滤的策略如下：
     * ① 对于列类型转换来说，如果 strict mode 为 true，则错误的数据将被 filter。这里的错
     * 误数据是指：原始数据并不为空值，在参与列类型转换后结果为空值的这一类数据。
     * ② 对于导入的某列由函数变换生成时，strict mode 对其不产生影响。
     * ③ 对于导入的某列类型包含范围限制的，如果原始数据能正常通过类型转换，但无法
     * 通过范围限制的，strict mode 对其也不产生影响。例如：如果类型是 decimal(1,0), 原始数
     * 据为 10，则属于可以通过类型转换但不在列声明的范围内。这种数据 strict 对其不产生影响。
     */
    public final static String STRICT_MODE = "strict_mode";

    /**
     * jsonpaths: 导入 json 方式分为：简单模式和匹配模式。如果设置了 jsonpath 则为匹配模
     * 式导入，否则为简单模式导入，具体可参考示例
     */
    public final static String JSON_PATHS = "jsonpaths";

    /**
     * 指定导入数据格式，默认是 csv，支持 json 格式
     */
    public final static String FORMAT = "format";

    /**
     * 布尔类型，为 true 表示 json 数据以数组对象开始且将数组对象中进行展平，默认值是false
     */
    public final static String STRIP_OUTER_ARRAY = "strip_outer_array";

    /**
     * json_root 为合法的 jsonpath 字符串，用于指定 json document 的根节点，默认值为""
     */
    public final static String JSON_ROOT = "json_root";

    /**
     * 采样窗口内，允许的最大错误行数。必须大于等于 0。默认是 0，即不允许有错误行。
     * 采样窗口为 max_batch_rows * 10。即如果在采样窗口内，错误行数大于 max_error_number，
     * 则会导致例行作业被暂停，需要人工介入检查数据质量问题。 被 where 条件过滤掉的行不算错误行
     */
    public final static String MAX_ERROR_NUMBER = "max_error_number";

    /**
     * 指定导入作业所使用的时区。默认为使用 Session 的 timezone 参数。该参数会影响所
     * 有导入涉及的和时区有关的函数结果
     */
    public final static String TIMEZONE = "timezone";

    /**
     * 整型，用于设置发送批处理数据的并行度，如果并行度的值超过 BE 配置中的
     * max_send_batch_parallelism_per_job ， 那 么 作 为 协 调 点 的 BE 将 使 用
     * max_send_batch_parallelism_per_job 的值
     */
    public final static String SEND_BATCH_PARALLELISM = "send_batch_parallelism";

    //   ------------   Kafka  ------------    //

    /**
     * Kafka 集群配置（以逗号分隔）    192.168.9.20:9092,192.168.9.21:9092
     */
    public final static String KAFKA_BROKER_LIST = "kafka_broker_list";

    /**
     * Kafka 消费主题
     */
    public final static String KAFKA_TOPIC = "kafka_topic";

    /**
     * Kafka 消费组
     */
    public final static String PROPERTY_GROUP_ID = "property.group.id";

    /**
     *
     */
    public final static String PROPERTY_KAFKA_DEFAULT_OFFSETS = "property.kafka_default_offsets";

    /**
     *
     */
    public final static String PROPERTY_ENABLE_AUTO_COMMIT = "property.enable.auto.commit";

}
