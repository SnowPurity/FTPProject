package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public interface Command {
    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException;
}
