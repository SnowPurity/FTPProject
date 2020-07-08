package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class DeleCommand implements Command{
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException
    {
        String path=client.getCurrentPath()+ File.separator+data;
        File file=new File(path);
        String message="213 successfully deleted the file.-------\r\n";
        if(file.exists()&&file.isFile())
        {
            file.delete();
        }else
        {
            message="504 delete failed,the file does not exist.-------\r\n";
        }
        writer.write(message);
        writer.flush();
    }
}
