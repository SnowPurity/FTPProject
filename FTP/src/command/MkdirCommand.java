package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class MkdirCommand implements Command {
    @Override
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        //新建服务器工作目录
        String path = client.getCurrentPath() + File.separator + data;
        File file = new File(path);
        //响应信息
        String message = "212 directory has set up . --------------\r\n";
        if (file.exists() && file.isDirectory()) {
            message = "504 the directory has already existed  . --------------\r\n";
        } else {
            //新建文件夹
            file.mkdir();
        }
        writer.write(message);
        writer.flush();
    }
}
