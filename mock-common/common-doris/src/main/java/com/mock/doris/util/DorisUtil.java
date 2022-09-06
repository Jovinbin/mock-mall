package com.mock.doris.util;

import com.mock.doris.config.DorisKafkaConf;
import com.mock.doris.config.DorisPartitionConf;
import com.mock.doris.entity.DorisRoutineLoadStatusEntity;
import com.mock.doris.param.DorisKafkaParam;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义 Doris 工具类
 *
 * @author zhao
 * @since 2022-08-08 17:19
 */
@Slf4j
public class DorisUtil {

    /**
     * 用来计数，什么时候打印一次状态信息
     */
    private static Integer printfRoutineLoad = 1;

    /**
     * 获取 Doris 连接
     * @return  连接
     */
    public static Connection getDorisConnection(){
        String url = "jdbc:mysql://" + "192.168.9.167" + ":9030/doris_zzz?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&allowMultiQueries=true";
        System.out.println("DorisUrl：" + url);
        return JdbcConnectUtil.getConnection(null, url, "root", "root123");
    }


    /**
     * 创建 Doris 数据表
     *
     * @param dorisConnection   Doris连接
     * @param createDorisTableSql   建表语句
     */
    public static void createTable(Connection dorisConnection, String createDorisTableSql) {
        try {
            PreparedStatement statement = dorisConnection.prepareStatement(createDorisTableSql);
//            System.out.println("执行Doris建表语句：\n" + createDorisTableSql);
            log.info("Doris ---> 执行Doris建表语句：\n" + createDorisTableSql);
            statement.execute();
//            System.out.println("Doris ---> 建表成功 ~");
            log.info("Doris ---> 建表成功 ~");
            statement.close();
        } catch (SQLException e) {
            log.error("Doris ---> 创建表失败：", e);
        }
    }

    /**
     * 创建 Doris 例行导入任务 （Kafka）
     *
     * @param dorisConnection   Doris连接
     * @param createKafkaRoutineLoadSql 建routine load语句
     */
    public static void createKafkaRoutineLoad(Connection dorisConnection, String createKafkaRoutineLoadSql) {
        try {
            PreparedStatement statement = dorisConnection.prepareStatement(createKafkaRoutineLoadSql);
//            System.out.println("执行Doris创建routine load语句：\n" + createKafkaRoutineLoadSql);
            log.info("Doris ---> 执行Doris创建routine load语句：\n" + createKafkaRoutineLoadSql);
            statement.executeUpdate();
            log.info("Doris ---> routine load 创建成功 ~");
            statement.close();
        } catch (SQLException e) {
            log.error("Doris ---> 创建Kafka routineLoad失败：", e);
        }
    }

    /**
     * 获取所有的例行导入任务状态信息
     *
     * @return  返回所有例行导入任务
     */
    public static List<DorisRoutineLoadStatusEntity> getAllRoutineLoad(){
        Connection dorisConnection = getDorisConnection();

        List<DorisRoutineLoadStatusEntity> routineLoadList = new ArrayList<>();
        int row = 1;

        PreparedStatement statement;
        ResultSet resultSet;
        try {
            // 获取所有的例行导入任务并保存到集合中
            statement = dorisConnection.prepareStatement("SHOW ROUTINE LOAD");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                DorisRoutineLoadStatusEntity dorisRoutineLoad = new DorisRoutineLoadStatusEntity()
                        .setId(resultSet.getString(1))
                        .setName(resultSet.getString(2))
                        .setCreateTime(resultSet.getString(3))
                        .setPauseTime(resultSet.getString(4))
                        .setEndTime(resultSet.getString(5))
                        .setDbName(resultSet.getString(6))
                        .setTableName(resultSet.getString(7))
                        .setState(resultSet.getString(8))
                        .setDataSourceType(resultSet.getString(9))
                        .setCurrentTaskNum(resultSet.getString(10))
                        .setJobProperties(resultSet.getString(11))
                        .setDataSourceProperties(resultSet.getString(12))
                        .setCustomProperties(resultSet.getString(13))
                        .setStatistic(resultSet.getString(14))
                        .setProgress(resultSet.getString(15))
                        .setLag(resultSet.getString(16))
                        .setReasonOfStateChanged(resultSet.getString(17))
                        .setErrorLogUrls(resultSet.getString(18))
                        .setOtherMsg(resultSet.getString(19));
                if (printfRoutineLoad % 3 == 0){
                    log.info("*************************** " + row + ". row ***************************");
                    log.info(dorisRoutineLoad.toString());
                    row++;
                }
                routineLoadList.add(dorisRoutineLoad);
            }
            printfRoutineLoad++;
            resultSet.close();
            statement.close();
            dorisConnection.close();
        } catch (SQLException e) {
            log.error("Doris ---> 获取例行导入任务失败：", e);
        }
        return routineLoadList;
    }

    /**
     * 暂停例行导入任务
     * @param dorisConnection   Doris连接
     * @param routineLoadName   导入任务名称（任务名或者数据库名.任务名）
     */
    public static void pauseRoutineLoadByName(Connection dorisConnection, String routineLoadName){
        try {
            PreparedStatement statement = dorisConnection.prepareStatement("PAUSE ROUTINE LOAD FOR " + routineLoadName);
            statement.execute();
            log.info("Doris ---> routine load：{} 暂停成功 ~", routineLoadName);
            statement.close();
        } catch (SQLException e) {
            log.error("Doris ---> routine load：{} 暂停失败 ~", routineLoadName, e);
        }
    }

    /**
     * 重启例行导入任务
     * @param dorisConnection   Doris连接
     * @param routineLoadName   导入任务名称（任务名或者数据库名.任务名）
     */
    public static void resumeRoutineLoadByName(Connection dorisConnection, String routineLoadName){
        try {
            PreparedStatement statement = dorisConnection.prepareStatement("RESUME ROUTINE LOAD FOR " + routineLoadName);
            statement.execute();
            log.info("Doris ---> routine load：{} 重启成功 ~", routineLoadName);
            statement.close();
        } catch (SQLException e) {
            log.error("Doris ---> routine load：{} 重启失败 ~", routineLoadName, e);
        }
    }

    /**
     * 停止例行导入任务（即删除导入任务）
     * @param dorisConnection   Doris连接
     * @param routineLoadName   导入任务名称（任务名或者数据库名.任务名）
     */
    public static void stopRoutineLoadByName(Connection dorisConnection, String routineLoadName){
        try {
            PreparedStatement statement = dorisConnection.prepareStatement("STOP ROUTINE LOAD FOR " + routineLoadName);
            statement.execute();
            log.info("Doris ---> routine load：{} 删除成功 ~", routineLoadName);
            statement.close();
        } catch (SQLException e) {
            log.error("Doris ---> routine load：{} 删除失败 ~", routineLoadName, e);
        }
    }

    /**
     * 获取 Doris 建表语句
     * @param tableName 表名
     * @param fieldList 字段列表
     * @return  建表语句（换行符 \n 仅仅是为了sql美观）
     */
    public static String getCreateDorisTableSql(String tableName, List<String> fieldList){
        // 默认字段 用于记录每一条数据采集了多少次
        String collectCount = "collect_count int sum default \"1\"";
        // 采集时间字段名
        String collectCurrentTime = "collect_current_time";
        // 采集时间字段类型
        String collectCurrentTimeType = " datetime";
        // 所有字段进行拼接
        String fields = fieldList.stream().map(e -> e + " string").collect(Collectors.joining(",\n"));
        // Doris --> aggregateKey列字段
//        String keyFields = fieldList.stream().collect(Collectors.joining(","));
        String keyFields = String.join(",", fieldList);
        // 主键字段             AGGREGATE KEY
        String aggregateKey = "AGGREGATE KEY(" + collectCurrentTime + "," + keyFields + ")";
        // 根据采集时间分区      PARTITION BY RANGE
        String partitionByRange = "PARTITION BY RANGE("+ collectCurrentTime +")()";
        // 根据字段进行分桶      DISTRIBUTED BY HASH
        String distributeByHash = "DISTRIBUTED BY HASH(" + fieldList.get(0) + ") BUCKETS 10";
        // 分区配置             PARTITION PROPERTIES
        String properties = "PROPERTIES("
                + "\n\""  + DorisPartitionConf.DYNAMIC_PARTITION_ENABLE    + "\" = " + "\"true\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_TIME_UNIT + "\" = " + "\"DAY\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_START     + "\" = " + "\"-700\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_END       + "\" = " + "\"3\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_PREFIX    + "\" = " + "\"p\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_BUCKETS   + "\" = " + "\"10\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_CREATE_HISTORY_PARTITION + "\" = " + "\"true\""
                + ",\n\"" + DorisPartitionConf.DYNAMIC_PARTITION_HISTORY_PARTITION_NUM    + "\" = " + "\"7\""
                + ",\n\"" + DorisPartitionConf.REPLICATION_NUM + "\" = " + "\"1\""
                + "\n);";

        // Doris建表sql拼接
        String createDorisTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + "("
                + "\n" + collectCurrentTime + collectCurrentTimeType + ",\n" + fields + ",\n" + collectCount + ")"
                + "\n" + aggregateKey
                + "\n" + partitionByRange
                + "\n" + distributeByHash
                + "\n" + properties;
        log.info("Doris ---> 返回Doris建表SQL语句");
        return createDorisTableSql;
    }

    /**
     * 获取 Doris 创建导入任务语句  -->  Kafka
     * @param tableName         表名
     * @param fieldList         字段名
     * @param mappingFieldList  映射字段名
     * @param dorisKafkaParam   Kafka相关配置参数
     * @return                  创建导入任务语句
     */
    public static String getCreateKafkaRoutineLoadSql(String tableName, List<String> fieldList, List<String> mappingFieldList, DorisKafkaParam dorisKafkaParam){
        // 采集时间字段名
        String collectCurrentTime = "collect_current_time";
        // 所有字段
        String fields = String.join(",", mappingFieldList);
        // Kafka消费对应表字段
        String columns = "COLUMNS(" + fields + "," + collectCurrentTime +")\n";
        // Kafka数据与columns字段一一对应
        fieldList.add("current");
        String jsonPaths = fieldList.stream().map(e -> "\\\"$." + e + "\\\"").collect(Collectors.joining(","));
        jsonPaths = "\"" + Collections.singletonList(jsonPaths) + "\"";
        // Kafka基本配置（配置消费数据格式的参数信息）
        String properties = "PROPERTIES("
                + "\n\""  + DorisKafkaConf.DESIRED_CONCURRENT_NUMBER + "\" = " + "\"3\""
                + ",\n\"" + DorisKafkaConf.MAX_BATCH_INTERVAL + "\" = " + "\"20\""
                + ",\n\"" + DorisKafkaConf.MAX_BATCH_ROWS     + "\" = " + "\"300000\""
                + ",\n\"" + DorisKafkaConf.MAX_BATCH_SIZE     + "\" = " + "\"209715200\""
                + ",\n\"" + DorisKafkaConf.STRICT_MODE        + "\" = " + "\"false\""
                + ",\n\"" + DorisKafkaConf.JSON_PATHS         + "\" = " + jsonPaths
                + ",\n\"" + DorisKafkaConf.FORMAT             + "\" = " + "\"json\""
                + ",\n\"" + DorisKafkaConf.STRIP_OUTER_ARRAY  + "\" = " + "\"true\""
                + ",\n\"" + DorisKafkaConf.JSON_ROOT          + "\" = " + "\"$.data\""
                + ",\n\"" + DorisKafkaConf.MAX_ERROR_NUMBER   + "\" = " + "\"5000000\""
                + "\n)";
        // Kafka集群参数配置
        String kafkaProperties = "FROM KAFKA("
                + "\n\""  + DorisKafkaConf.KAFKA_BROKER_LIST + "\" = " + dorisKafkaParam.getKafkaBrokerList()
                + ",\n\"" + DorisKafkaConf.KAFKA_TOPIC       + "\" = " + dorisKafkaParam.getKafkaTopic()
                + ",\n\"" + DorisKafkaConf.PROPERTY_GROUP_ID + "\" = " + dorisKafkaParam.getConsumerGroupId()
                + ",\n\"" + DorisKafkaConf.PROPERTY_KAFKA_DEFAULT_OFFSETS + "\" = " + dorisKafkaParam.getKafkaDefaultOffset()
                + ",\n\"" + DorisKafkaConf.PROPERTY_ENABLE_AUTO_COMMIT    + "\" = " + dorisKafkaParam.getEnableAutoCommit()
                + "\n);";

        // TODO: 2022/8/12 后续需要传入指定数据库
        // Doris消费Kafka --> routine load 创建语句(创建Kafka任务)
        String createKafkaRoutineLoadSql = "CREATE ROUTINE LOAD doris_zzz." + tableName + " ON " + tableName + "\n"
                + columns + properties + kafkaProperties;

        log.info("Doris ---> 返回Doris创建routine load SQL语句");
        return createKafkaRoutineLoadSql;
    }

}
