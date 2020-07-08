package command;

import global.Global;
import server.ClientDeal;
import util.StringUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class CwdCommand implements Command{
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException
    {
        String dir = client.getCurrentPath()+File.separator+data;
        File file =new File(dir);
        try{
            if((file.exists())&&(file.isDirectory())) {
                String nowDir = client.getCurrentPath() + File.separator + data;
                client.setCurrentPath(nowDir);
                writer.write("250 CWD command successful.---------------\r\n");
            }
            else{
                writer.write("550 the directory does not exist.---------------\r\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        writer.flush();
    }
}
