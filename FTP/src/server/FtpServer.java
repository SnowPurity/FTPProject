package server;

import constant.Constant;
import global.Global;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FtpServer {

    private static ExecutorService ThreadPool= Executors.newFixedThreadPool(Constant.THREADPOOLSIZE);

    public static ServerSocket server=null;

    public static void main(String[] args) throws IOException
    {
        server=new ServerSocket(Constant.LISTENPORT);

        Global.initGlobalMessgae();

        while(true)
        {
            Socket socket=server.accept();
            ThreadPool.execute(new ClientDeal(socket));
        }
    }
}
