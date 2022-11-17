package com.mock.hadoop.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * @author zhao
 * @since 2022-09-20 10:02
 */
@Slf4j
public class FileUtils {

    /**
     * 读取文件流数据
     * @param inputStream
     * @return
     */
    @SneakyThrows
    public static StringBuffer streamToString(InputStream inputStream, String charsetName){
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

    /**
     * 判断文件流是否是文本格式
     * @param inputStream
     * @return
     */
    @SneakyThrows
    public static boolean judgeStreamIsText(InputStream inputStream, String charsetName){
        boolean isText = true;
        InputStreamReader isReader = null;
        BufferedReader br = null;
        try {
            isReader = new InputStreamReader(inputStream, charsetName);
            br = new BufferedReader(isReader);
            if (!br.ready()){
                isText = false;
            }
            //循环逐行读取
            while (br.ready()) {
                int t = br.read();
                if (t < 32 && t != 9 && t != 10 && t != 13) {
                    isText = false;
                    break;
                }
            }
        } catch (IOException e) {
            log.error("文件流读取发生异常 ---》", e);
        } finally {
            // 关闭流
            br.close();
        }
        return isText;
    }

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            log.info("创建的{}目标文件已存在！", destFileName);
            return false;
        } else if (destFileName.endsWith(File.separator)) {
            log.info("创建单个文件{}失败，目标文件不能为目录！", destFileName);
            return false;
        } else {
            if (!file.getParentFile().exists()) {
                log.info("目标文件所在目录不存在，准备创建它！");
                if (!file.getParentFile().mkdirs()) {
                    log.info("创建目标文件所在目录失败！");
                    return false;
                }
            }
            try {
                if (file.createNewFile()) {
                    log.info("创建单个文件{}成功！", destFileName);
                    return true;
                } else {
                    log.info("创建单个文件{}失败！", destFileName);
                    return false;
                }
            } catch (IOException var3) {
                var3.printStackTrace();
                log.info("创建单个文件{}失败！{}", destFileName, var3.getMessage());
                return false;
            }
        }
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            log.info("创建目录{}，目标目录已经存在", destDirName);
            return false;
        } else {
            if (!destDirName.endsWith(File.separator)) {
                destDirName = destDirName + File.separator;
            }

            if (dir.mkdirs()) {
                log.info("创建目录{}成功！", destDirName);
                return true;
            } else {
                log.info("创建目录{}失败！", destDirName);
                return false;
            }
        }
    }

    public static List<File> readAllFile(String filePath, List<File> list) {
        File f = new File(filePath);
        // 得到f文件夹下面的所有文件。
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件,如果有二级目录，则添加文件名称为二级目录/文件名称
                readAllFile(file.getAbsolutePath(), list);
            } else {
                list.add(file);
            }
        }
        return list;
    }

}
