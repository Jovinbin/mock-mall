package com.mock.hadoop.hdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 客户端代码：工具类  （提供三个构造方法获取客户端对象）
 * 1、获取一个客户端对象
 * 2、执行相关的操作命令
 * 3、关闭资源
 * HDFS     zookeeper
 *
 * @author zhao
 * @since 2022-08-03 15:54
 */
@Slf4j
public class HdfsClient {

    /**
     * 客户端对象
     */
    private FileSystem fileSystem;

    /**
     * @param configuration  配置信息
     */
    public HdfsClient(Configuration configuration) {
        init(configuration);
    }

    /**
     * @param hdfsUri        连接的集群nn地址
     * @param configuration  配置信息
     */
    public HdfsClient(URI hdfsUri, Configuration configuration) {
        init(hdfsUri, configuration);
    }

    /**
     * @param user           用户
     * @param hdfsUri        连接的集群nn地址
     * @param configuration  配置信息
     */
    public HdfsClient(URI hdfsUri, Configuration configuration, String user) {
        init(hdfsUri, configuration, user);
    }

    /**
     * 初始化信息hdfs客户端信息
     */
    private void init(Configuration configuration) {

        try {
            // 获取到了客户端对象
            fileSystem = FileSystem.get(configuration);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化信息hdfs客户端信息
     */
    private void init(URI hdfsUri, Configuration configuration) {

        try {
            // 获取到了客户端对象
            fileSystem = FileSystem.get(hdfsUri, configuration);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化信息hdfs客户端信息
     */
    private void init(URI hdfsUri, Configuration configuration, String user) {

        try {
            // 获取到了客户端对象
            fileSystem = FileSystem.get(hdfsUri, configuration, user);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  返回客户端对象
     */
    public FileSystem getFileSystem(){
        return fileSystem;
    }

    /**
     * 释放资源
     */
    public void close() throws IOException {
        // 关闭资源
        fileSystem.close();
    }

    //  -----------------   一些常用方法  -----------------
    // TODO: 2022/8/4 后续有用其他的到再继续补充

    /**
     * 检查文件或者文件夹是否存在
     * @param filename  文件夹名称
     */
    public boolean checkFileExist(String filename) {
        try {
            Path f = new Path(filename);
            return fileSystem.exists(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件夹
     * @param dirName   文件夹名称
     */
    public boolean mkdir(String dirName){
        if (checkFileExist(dirName))
            return true;
        try {
            Path f = new Path(dirName);
            log.info("Create and Write :" + f.getName() + " to hdfs");
            return fileSystem.mkdirs(f);
        } catch (Exception e) {
            log.error("创建文件夹失败，异常信息：", e);
        }
        return false;
    }

    /**
     * 文件上传
     * @param delSrc      是否删除元数据
     * @param overwrite   是否允许覆盖
     * @param sourceFilePath  元数据路径
     * @param targetFilePath  目的地路径
     */
    public boolean putFile(boolean delSrc, boolean overwrite, String sourceFilePath, String targetFilePath) {
        try {
            Path source = new Path(sourceFilePath);
            Path target = new Path(targetFilePath);
            fileSystem.copyFromLocalFile(delSrc, overwrite, source, target);
            return true;
        } catch (IOException e) {
            log.error("文件上传失败，异常信息：", e);
        }
        return false;
    }

    /**
     * 文件下载
     * @param delSrc           是否删除原文件
     * @param sourceHdfsPath   源文件路径HDFS
     * @param targetFilePath   目标路径Win
     */
    public boolean downloadFile(boolean delSrc, String sourceHdfsPath, String targetFilePath){
        try {
            Path source = new Path(sourceHdfsPath);
            Path target = new Path(targetFilePath);
            fileSystem.copyToLocalFile(delSrc, source, target);
            return true;
        } catch (IOException e) {
            log.error("文件下载失败，异常信息：", e);
        }
        return false;
    }

    /**
     * 文件下载
     * @param delSrc           是否删除原文件
     * @param sourceHdfsPath   源文件路径HDFS
     * @param targetFilePath   目标路径Win
     * @param useRawLocalFileSystem     是否开启本地模式 --> 文件的校验
     */
    public boolean downloadFile(boolean delSrc, String sourceHdfsPath, String targetFilePath, boolean useRawLocalFileSystem){
        try {
            Path source = new Path(sourceHdfsPath);
            Path target = new Path(targetFilePath);
            fileSystem.copyToLocalFile(delSrc, source, target, useRawLocalFileSystem);
            return true;
        } catch (IOException e) {
            log.error("文件下载失败，异常信息：", e);
        }
        return false;
    }

    /**
     * 删除文件或目录  注：删除非空目录时需要设置recursive，否则将无法删除
     * @param delPath   删除路径
     * @param recursive 是否递归删除
     */
    public boolean delFile(String delPath, boolean recursive){
        boolean isDel = false;
        try {
            Path path = new Path(delPath);
            isDel = fileSystem.delete(path, recursive);
        } catch (IOException e) {
            log.error("删除失败，异常信息：", e);
        }
        return isDel;
    }

    /**
     * 文件或文件夹的更名和移动
     * @param sourcePath    源路径
     * @param targetPath    目标路径
     */
    public boolean renameOrMoveFile(String sourcePath, String targetPath){
        boolean flag = false;
        try {
            flag = fileSystem.rename(new Path(sourcePath), new Path(targetPath));
        } catch (IOException e) {
            log.error("文件更名或移动失败，错误信息：", e);
        }
        return flag;
    }

    /**
     * 查看文件详情信息
     * @param pathString    需要查看的路径
     * @param recursive     是否需要递归获取
     */
    public List<LocatedFileStatus> getFileDetail(String pathString, boolean recursive){
        List<LocatedFileStatus> locatedFileStatusList = new ArrayList<>();
        try {
            // 获取所有文件信息
            RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path(pathString), recursive);
            // 遍历将文件添加到集合当中
            while (listFiles.hasNext()){
                LocatedFileStatus fileStatus = listFiles.next();
                locatedFileStatusList.add(fileStatus);
            }
        } catch (IOException e) {
            log.error("获取文件详情失败，错误信息：", e);
        }
        return locatedFileStatusList;
    }

    /**
     * 获取所有文件信息样本案例
     */
    public void getFileDetailExample() throws IOException {
        // 获取所有文件信息
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);
        // 遍历文件
        while (listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("======= " + fileStatus.getPath() + " ======");
            System.out.println("权限：" + fileStatus.getPermission());
            System.out.println("所属：" + fileStatus.getOwner());
            System.out.println("属组：" + fileStatus.getGroup());
            System.out.println("长度：" + fileStatus.getLen());
            System.out.println("上次修改时间" + fileStatus.getModificationTime());
            System.out.println("副本：" + fileStatus.getReplication());
            System.out.println("块大小" + fileStatus.getBlockSize());
            System.out.println("名称：" + fileStatus.getPath().getName());
            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println("块信息：" + Arrays.asList(blockLocations).toString());
        }

    }

    /**
     * 输出路径下的所有文件是文件还是文件夹
     * @param path  路径
     */
    public void isFileOrDir(String path){
        try {
            FileStatus[] listStatus = fileSystem.listStatus(new Path(path));

            for (FileStatus status : listStatus) {

                if (status.isFile()){
                    log.info("文件：" + status.getPath().getName());
                } else {
                    log.info("目录：" + status.getPath().getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
