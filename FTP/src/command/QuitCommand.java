package command;

import global.Global;
import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.Writer;

public class QuitCommand implements Command{
    @Override
    public void deal(BufferedWriter writer, String data, ClientDeal client) {
        //将用户从已登陆列表清除
        Global.loginUsers.remove(client.getName());
        //断开连接
        client.clear();
    }
}
