package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class SystCommand implements Command{
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException
    {
        String message="215 UNIX Type: Apache FTP------\r\n";
        writer.write(message);
        writer.flush();
    }
}
