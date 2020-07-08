package command;

import constant.Constant;
import server.ClientDeal;
import server.FtpServer;
import util.CloseUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class StorCommand implements Command {

    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        //传输文件虚拟路径
        String path = client.getCurrentPath()+ File.separator+data;
        File file = new File(path);
        if(file.exists()) {
            writer.write("502 upload failed(you may have uploaded it) -----------\r\n");
            writer.flush();
        }else {
            writer.write("150 the file functioning normal,waiting for data connection .---------------\r\n");
            writer.flush();
            //开启数据传输套接字
            Socket dataSocket  = new Socket(InetAddress.getByName(client.getIp()), client.getPort(),
                    FtpServer.server.getInetAddress(), Constant.DATAPORT);
            //获取客户端的输入流
            BufferedInputStream is = new BufferedInputStream(dataSocket.getInputStream());
            //文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //数据缓冲区
            byte[] buffer = new byte[2048];
            int len = -1;
            while((len=is.read(buffer))!=-1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            writer.write("220 file transmission complete   ---------------\r\n");
            writer.flush();
            CloseUtil.close(is);
            CloseUtil.close(fos);
            CloseUtil.close(dataSocket);
            writer.write("226  closing data connection.---------------\r\n");
            writer.flush();
        }
    }
}
