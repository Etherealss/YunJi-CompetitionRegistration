package pers.etherealss.utils;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description
 * @date 2021-08-14
 */
public class InternetUtil {

    /**
     * 判断端口是否可连接
     * @return
     */
    public static boolean isHostConnected(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (Exception e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
