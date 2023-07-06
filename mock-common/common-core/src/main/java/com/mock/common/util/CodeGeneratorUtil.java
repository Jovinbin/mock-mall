package com.mock.common.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.mock.common.config.GenerateConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @since 2022-06-09 9:33
 * @description: 代码生成工具类
 */
public class CodeGeneratorUtil {

    public static final String SUPER_ENTITY_CLASS = "com.suntang.dcm.common.base.BaseEntity";

    public static final String AUTHOR = "yoonada";

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static final String JDBC_URL = "jdbc:mysql://192.168.9.132:3306/metadata?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

    public static final String JDBC_USER_NAME = "root";

    public static final String JDBC_PASSWORD = "Mysql@8505";

    public static final String PARENT_PACKAGE = "com.suntang.dcm.metadata";

    public static final String OUTPUT_DIR = "H:\\metadata";

    public static final String[] TABLE_PREFIX = new String[]{"t_", "sys_", "st_"};

    public static final String[] INCLUDE_TABLES = new String[]{"st_metadata_sync"};

//    public static void main(String[] args) {
//        GenerateConfig config = new GenerateConfig();
//        config.setAuthor(AUTHOR);
//        config.setDbType(DbType.MYSQL);
//        config.setJdbcDriver(JDBC_DRIVER);
//        config.setJdbcUrl(JDBC_URL);
//        config.setJdbcUserName(JDBC_USER_NAME);
//        config.setJdbcPassword(JDBC_PASSWORD);
//        config.setTablePrefix(TABLE_PREFIX);
//        config.setParentPackage(PARENT_PACKAGE);
//        config.setOutputDir(OUTPUT_DIR);
//        config.setIncludeTables(INCLUDE_TABLES);
//        execute(config);
//    }

    public static void execute(GenerateConfig config){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 是否支持AR模式
        gc.setActiveRecord(true)
                // 作者
                .setAuthor(config.getAuthor())
                // 生成路径，最好使用绝对路径
                //TODO  TODO  TODO  TODO
                .setOutputDir(config.getOutputDir() + "/src/main/java")
                // 文件覆盖
                .setFileOverride(false)
                // 主键策略
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE)
                // 设置生成的service接口的名字的首字母是否为I，默认Service是以I开头的,注意 %s 会自动填充表实体属性！
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                // 实体类结尾名称
//                .setEntityName("%sDO")
                //生成基本的resultMap
                .setBaseResultMap(true)
                // 不使用AR模式
                .setActiveRecord(false)
                // 生成基本的SQL片段
                .setBaseColumnList(true)
                // 自动打开输出目录
                .setOpen(false)
                .setSwagger2(true);

        // 2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName(config.getJdbcDriver())
                //TODO  TODO  TODO  TODO
                .setUrl(config.getJdbcUrl())
                .setUsername(config.getJdbcUserName())
                .setPassword(config.getJdbcPassword());

        // 3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();

        // 全局大写命名
        stConfig.setCapitalMode(false)
                // 此处可以移除表前缀表前缀
                .setTablePrefix(config.getTablePrefix().split(","))
                // 使用lombok
                .setEntityLombokModel(true)
                // 使用restController注解
                .setRestControllerStyle(true)
                .setEntityTableFieldAnnotationEnable(false)
                // 数据库表和字段映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 实体父类字段
                .setSuperEntityColumns("create_time", "update_time", "deleted", "CREATE_TIME", "UPDATE_TIME", "DELETED")
                // 实体父类
                .setSuperEntityClass("com.mock.common.base.BaseEntity")
                // 接口父类
                .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")
                // 接口实现父类
                .setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
                // mapper父类
                .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                // 生成的表, 支持多表一起生成，以数组形式填写
                //TODO  TODO  TODO  TODO 两个方式，直接写，或者使用命令行输入
//                .setInclude(config.getIncludeTables());
                .setInclude(config.getIncludeTables().split(","));

        TemplateConfig templateConfig = new TemplateConfig();
        //不生成controller
//        templateConfig.setController(null);

        // 4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        //父包名
        pkConfig.setParent(config.getParentPackage())
                //父包模块名
//                .setModuleName("")
                //mapper父包
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                //controller父包
                .setController("controller")
                //实体类父包
                .setEntity("entity");

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return config.getOutputDir() + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(gc)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setCfg(cfg)
                .setPackageInfo(pkConfig);

        ag.execute();
        System.out.println("=======  Done 相关代码生成完毕  ========");
    }



}
