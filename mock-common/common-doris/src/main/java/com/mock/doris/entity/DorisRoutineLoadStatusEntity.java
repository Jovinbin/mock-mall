package com.mock.doris.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Doris 例行导入任务相关信息
 * @author zhao
 * @since 2022-08-10 14:54
 */
@Data
@Accessors(chain = true)
@TableName("st_doris_routine_load")
@ApiModel(value="Doris例行导入任务对象")
public class DorisRoutineLoadStatusEntity {

    @ApiModelProperty(value = "任务编号")
    @TableId(value = "id", type = IdType.INPUT)
    private String Id;

    @TableField("name")
    @ApiModelProperty(value = "任务名称")
    private String Name;

    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @TableField(value = "pause_time", updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "暂停时间")
    private String PauseTime;

    @TableField("end_time")
    @ApiModelProperty(value = "结束时间")
    private String EndTime;

    @TableField("db_name")
    @ApiModelProperty(value = "数据库名称")
    private String DbName;

    @TableField("table_name")
    @ApiModelProperty(value = "对应数据库表名")
    private String TableName;

    @TableField("state")
    @ApiModelProperty(value = "导入任务运行状态")
    private String State;

    @TableField("datasource_type")
    @ApiModelProperty(value = "数据源")
    private String DataSourceType;

    @TableField("current_task_num")
    @ApiModelProperty(value = "子任务数")
    private String CurrentTaskNum;

    @TableField("job_properties")
    @ApiModelProperty(value = "工作任务配置信息")
    private String JobProperties;

    @TableField("datasource_properties")
    @ApiModelProperty(value = "数据源配置信息")
    private String DataSourceProperties;

    @TableField("custom_properties")
    @ApiModelProperty(value = "配置项")
    private String CustomProperties;

    @TableField("statistic")
    @ApiModelProperty(value = "统计信息")
    private String Statistic;

    @TableField("progress")
    @ApiModelProperty(value = "进程")
    private String Progress;

    @TableField("lag")
    @ApiModelProperty(value = "标签")
    private String Lag;

    @TableField("reason_of_state_change")
    @ApiModelProperty(value = "错误信息")
    private String ReasonOfStateChanged;

    @TableField("error_log_urls")
    @ApiModelProperty(value = "")
    private String ErrorLogUrls;

    @TableField("other_msg")
    @ApiModelProperty(value = "")
    private String OtherMsg;

    @Override
    public String toString() {
        return "DorisRoutineLoadStatusParam：\n" +
               "                  Id：" + Id +
               "\n                Name：" + Name +
               "\n          CreateTime：" + CreateTime +
               "\n           PauseTime：" + PauseTime +
               "\n             EndTime：" + EndTime +
               "\n              DbName：" + DbName +
               "\n           TableName：" + TableName +
               "\n               State：" + State +
               "\n      DataSourceType：" + DataSourceType +
               "\n      CurrentTaskNum：" + CurrentTaskNum +
               "\n       JobProperties：" + JobProperties +
               "\nDataSourceProperties：" + DataSourceProperties +
               "\n    CustomProperties：" + CustomProperties +
               "\n           Statistic：" + Statistic +
               "\n            Progress：" + Progress +
               "\n                 Lag：" + Lag +
               "\nReasonOfStateChanged：" + ReasonOfStateChanged +
               "\n        ErrorLogUrls：" + ErrorLogUrls +
               "\n            OtherMsg：" + OtherMsg;
    }
}
