package pers.etherealss.utils.simple;

import java.io.*;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public class IoUtil {

    public static BufferedOutputStream toBuffered(OutputStream out) {
        return (out instanceof BufferedOutputStream) ? (BufferedOutputStream) out : new BufferedOutputStream(out);
    }

    public static BufferedInputStream toBuffered(InputStream in) {
        return (in instanceof BufferedInputStream) ? (BufferedInputStream) in : new BufferedInputStream(in);
    }

    /**
     * 将byte[]写到流中
     * @param out 输出流
     * @param isDoClose 写入完毕是否关闭输出流
     * @param content 写入的内容
     * @throws IOException IO异常
     */
    public static void write(OutputStream out, boolean isDoClose, byte[] content) throws IOException {
        try {
            out.write(content);
        } finally {
            if (isDoClose) {
                close(out);
            }
        }
    }

    /**
     * 关闭<br>
     * 关闭失败不会抛出异常
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) throws IOException {
        if (null != closeable) {
            closeable.close();
        }
    }
}
