package command;

import constant.Constant;
import server.ClientDeal;
import server.FtpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//设置为被动模式
public class PasvCommand implements Command{
    @Override
    public void deal(BufferedWriter writer, String data, ClientDeal client)
    {
        System.out.println("execute the PASV command.....");
        String response="";
        try{
            int tempPort=-1;
            ServerSocket serverSocket=null;
            while(serverSocket==null)
            {
                tempPort=(int)(Math.random()*1e5)%9999+10240;
                serverSocket=getDataServerSocket(tempPort);
                client.setPasvPort(tempPort);
                System.out.println("Get PASV tempport:"+tempPort);
            }
            if(tempPort!=-1 && serverSocket!=null)
            {
                int p1=tempPort/256;
                int p2=tempPort-p1*256;
                response="227 entering passive mode (127,0,0,1,"+p1+","+p2+")";
                System.out.println(response);
            }
            writer.write(response);
            writer.write("\r\n");
            writer.flush();
            System.out.println("set PASV successful");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ServerSocket getDataServerSocket(int port)
    {
        ServerSocket socket=null;
        try{
            socket=new ServerSocket(port);
        }catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return socket;
    }
}