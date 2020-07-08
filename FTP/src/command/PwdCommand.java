package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class PwdCommand implements Command {
    @Override
    public void deal(BufferedWriter writer, String data, ClientDeal client) {
        try {
            //当前工作目录
            String path = client.getCurrentPath();
            writer.write("212 current path: "+path+" \r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
