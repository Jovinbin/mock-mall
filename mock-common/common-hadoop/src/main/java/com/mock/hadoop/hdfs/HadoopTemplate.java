package com.mock.hadoop.hdfs;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhao
 * @since 2022-10-19 16:04
 */
@Component
@ConditionalOnClass(FileSystem.class)
@Slf4j
public class HadoopTemplate {

    @Resource
    private FileSystem fileSystem;

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

    /**
     * 读取文件内容
     * @param dst 目标文件
     * @return
     */
    public String readFile(String dst) {
        Path srcPath = new Path(dst);
        InputStream in = null;
        String content = "";
        try {
            in = fileSystem.open(srcPath);
            //复制到标准输出流
//            IOUtils.copyBytes(in, System.out, 4096, false);
            content = streamToString(in, "UTF-8").toString();
        } catch (IOException e) {
            log.error("文件内容读取失败，错误信息：", e);
        }
        return content;
    }

    /**
     * 复制文件
     * @param inputStream   需要复制的文件流
     * @param filePath      复制到的文件路径
     */
    public void copyFile(InputStream inputStream, String filePath) {
        try {
            Path path = new Path(filePath);
            FSDataOutputStream fsDataOutputStream = fileSystem.create(path);
            byte[] b = new byte[1024 * 1024];
            int read = 0;
            while ((read = inputStream.read(b)) > 0){
                fsDataOutputStream.write(b, 0, read);
            }
            fsDataOutputStream.flush();
            fsDataOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("文件内容读取失败，错误信息：", e);
        }
    }

    /**
     * 获取Hdfs上的文件流
     * @param fileSystemPath 文件路径
     * @return
     */
    public InputStream getFileInputStream(String fileSystemPath) {
        Path srcPath = new Path(fileSystemPath);
        InputStream in = null;
        try {
            in = fileSystem.open(srcPath);
        } catch (IOException e) {
            log.error("文件内容读取失败，错误信息：", e);
        }
        return in;
    }

    /**
     * 获取路径底下所有的文件路径
     * @param path  目录位置
     * @return
     */
    public List<Path> getAllFilePath(String path){
        List<Path> filePaths = new ArrayList<>();
        try {
            RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path(path), true);
            while (listFiles.hasNext()){
                LocatedFileStatus fileStatus = listFiles.next();
                filePaths.add(fileStatus.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePaths;
    }

    /**
     * 读取文件流数据
     * @param inputStream
     * @return
     */
    @SneakyThrows
    public StringBuffer streamToString(InputStream inputStream, String charsetName){
        StringBuffer sb = new StringBuffer();
        InputStreamReader isReader = null;
        BufferedReader br = null;
        try {
            isReader = new InputStreamReader(inputStream, charsetName);
            br = new BufferedReader(isReader);
            //循环逐行读取
            while (br.ready()) {
                sb.append(br.readLine());
            }
        } catch (IOException e) {
            log.error("文件流读取发生异常 ---》", e);
        } finally {
            // 关闭流
            br.close();
        }
        return sb;
    }

}