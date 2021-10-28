package pers.etherealss.utils.simple;

import java.io.*;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public class FileUtil {

    /**
     * @see #touch(File)
     */
    public static File touch(String fullFilePath) throws IOException {
        if (fullFilePath == null) {
            return null;
        }
        return touch(file(fullFilePath));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     * @throws IOException IO异常
     */
    public static File touch(File file) throws IOException {
        if (null == file) {
            return null;
        }
        if (!file.exists()) {
            // 创建父目录
            mkParentDirs(file);
            try {
                // 创建文件
                file.createNewFile();
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
        return file;
    }

    /**
     * 创建所给文件或目录的父目录
     * @param file 文件或目录
     * @return 父目录
     */
    public static File mkParentDirs(File file) {
        final File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        return parentFile;
    }

    /**
     * 创建File对象
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (null == path) {
            return null;
        }
        return new File(path);
    }

    public static BufferedOutputStream getOutputStream(String filePath) throws IOException {
        return getOutputStream(file(filePath));
    }

    /**
     * 获得一个输出流对象
     * @param file 文件
     * @return 输出流对象
     * @throws IOException IO异常
     */
    public static BufferedOutputStream getOutputStream(File file) throws IOException {
        final OutputStream out = new FileOutputStream(touch(file));
        return IoUtil.toBuffered(out);
    }

    public static BufferedInputStream getInputStream(String filePath) throws IOException {
        return getInputStream(file(filePath));
    }
    /**
     * 获得一个输入流对象
     * @param file 文件
     * @return 输出流对象
     * @throws IOException IO异常
     */
    public static BufferedInputStream getInputStream(File file) throws IOException {
        final InputStream out = new FileInputStream(file);
        return IoUtil.toBuffered(out);
    }
}
