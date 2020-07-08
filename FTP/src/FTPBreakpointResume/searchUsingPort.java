package FTPBreakpointResume;

import java.io.IOException;
import java.net.ServerSocket;

public class searchUsingPort {
    public static void main(String[] args) throws Exception {

        for (int port = 1; port < 65535; port++) {
            try {
                ServerSocket s = new ServerSocket(port);
            } catch (IOException e) {
                System.out.println("当前系统中已经使用的端口：" + port);
            }
        }
    }
}