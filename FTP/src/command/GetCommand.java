package command;

import constant.Constant;
import server.ClientDeal;
import server.FtpServer;
import util.CloseUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetCommand implements Command{
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException
    {
        String path=client.getCurrentPath()+File.separator+data;
        File file=new File(path);
        if(file.isFile())
        {
            try{
                writer.write("150 the file functioning normal,waiting for data connection.---------------\r\n");
                writer.flush();
                Socket dataSocket1=new Socket(InetAddress.getByName(client.getIp()),client.getPort(), FtpServer.server.getInetAddress(), Constant.DATAPORT);
                BufferedOutputStream ous=new BufferedOutputStream(dataSocket1.getOutputStream());
                InputStream is=new FileInputStream(file);
                byte[] buffer=new byte[1024];
                int len=-1;
                while((len=is.read(buffer))!=-1)
                {
                    ous.write(buffer,0,len);
                    ous.flush();
                }
                writer.write("220 file transmission complete  . ---------------\r\n");
                writer.flush();
                CloseUtil.close(is);
                CloseUtil.close(ous);
                CloseUtil.close(dataSocket1);
                writer.write("226  closing data connection.---------------\r\n");
                writer.flush();
            }catch (UnknownHostException e)
            {
                e.printStackTrace();
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }else
        {
            writer.write("220 the file does not exist.---------------\r\n");
            writer.flush();
        }
    }
}
