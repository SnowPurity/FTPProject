package command;

import constant.Constant;
import server.ClientDeal;
import server.FtpServer;
import util.CloseUtil;
import util.TimeUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    @Override

    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {

        //文件目录路径
        String path = client.getCurrentPath()+data;
        File file = new File(path);
        //检查文件或目录是否存在这种抽象路径名记
        if(!file.exists()) {
            writer.write("213 the file directory does not exist.---------------\r\n");
            writer.flush();
            return ;
        }else {
            //文件列表信息
            List<String> fileListMsg = new ArrayList<>();
            fileListMsg.add("-------the files of this directory are as follows :------\r\n");
            //获得该目录下的抽象路径名的文件和目录命名数组
            String[] fileList = file.list();
            if(fileList!=null) {
                for(String dirName:fileList) {
                    //临时目录文件
                    File tempFile = new File(path+File.separator+dirName);
                    if(tempFile.isFile()) {
                        String info = "f"+" rw-rw-rw- ftp  ftp "+tempFile.length()+"B     "+ TimeUtil.longToString_Time(tempFile.lastModified())+"    "+dirName+"\r\n";
                        fileListMsg.add(info);
                    }
                }
                fileListMsg.add("\r\n");
            }
            try {
                writer.write("150 the file functioning normal,waiting for data connection .---------------\r\n");
                writer.flush();
                //开启数据传输套接
                Socket dataSocket  = new Socket(InetAddress.getByName(client.getIp()), client.getPort(), FtpServer.server.getInetAddress(), Constant.DATAPORT);
                //输出流
                BufferedWriter dataWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream(),"GB2312"));
                for(String str:fileListMsg) {
                    dataWriter.write(str);
                }
                writer.write("226 file transmission complete ---------------\r\n");
                writer.flush();
                CloseUtil.close(dataWriter);
                CloseUtil.close(dataSocket);
                writer.write("226  closing data connection.---------------\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}