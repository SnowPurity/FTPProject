package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class RmdirCommand implements Command {
    @Override
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        //文件夹目录
        String path = client.getCurrentPath()+ File.separator+data;
        File file = new File(path);
        //响应信息
        String message = "212 directory delete successful.------------\r\n";
        if(file.exists()&&file.isDirectory()) {//判断是否为目录
            if(!file.delete()) {//判断是否为空目录
                message = "503 the directory is not empty,delete failed.------------\r\n";
            }
        }else {
            message = "504 delete fail,the directory does not exist.------------\r\n";
        }
        writer.write(message);
        writer.flush();
    }
}
