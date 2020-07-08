package command;

import global.Global;
import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class UserCommand implements Command{

    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        //提示信息
        String message = "";
        if(client.isLogin()) {//判断是否已经登陆
            writer.write("230 you've logged in,if you try to relog,please cut the connection .---------------\r\n");
            writer.flush();
            return ;
        }
        if(Global.loginUsers.contains(data)) {//用户已经登陆
            message = "301 this account has logged in, try another account .---------------\r\n";
        }else if(Global.users.containsKey(data)) {//用户存在
            client.setName(data);
            message = "331 username right,enter your password .---------------\r\n";
        }else {
            message = "301 username does not exist.---------------\r\n";
        }
        writer.write(message);
        writer.flush();
    }
}
