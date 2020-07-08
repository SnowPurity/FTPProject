package command;

import constant.Constant;
import server.ClientDeal;
import server.FtpServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class AppeCommand implements Command{
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException
    {
        System.out.println("start transmission------\r\n");
        try{
            Socket dataSocket  = new Socket(
                    FtpServer.server.getInetAddress(),client.getPasvPort());
            client.setDataSocket(dataSocket);
            InputStream inSocket=client.getDataSocket().getInputStream();
            File file=new File(client.getCurrentPath()+File.separator+data);
            System.out.println(client.getCurrentPath()+File.separator+data);
            RandomAccessFile inFile=new RandomAccessFile(client.getCurrentPath()+File.separator+data,"rw");
            if(!file.exists())
            {
                writer.write("226 s\r\n");
                writer.flush();
            }
            else
            {
                inFile.seek(file.length());
                writer.write("226 "+file.length()+"\r\n");
                writer.flush();
            }
            byte bytebuffer[]=new byte[1024];
            int amount=0;
            int i=0;
            try {
                while ((amount = inSocket.read(bytebuffer)) != -1) {
                    inFile.write(bytebuffer, 0, amount);
                }

            }catch (IOException E){
                E.printStackTrace();
            }
            System.out.println("transfered,closing the connection....\r\n");
            inFile.close();
            inSocket.close();
            writer.write("226 transfer complete....\r\n");
            writer.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
