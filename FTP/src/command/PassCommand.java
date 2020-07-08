package command;

import global.Global;
import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class PassCommand implements Command{



    @Override

    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        //提示信息
        String message ="";
        if(client.isLogin()) {//判断是否已经登陆
            writer.write("230 you've logged in,if you try to relog,please cut the connection .---------------\r\n");
            writer.flush();
            return ;
        }
        //获取用户账号
        String name = client.getName();
        if(name==null) {
            writer.write("332 you've not Enter User yet .---------------\r\n");
            writer.flush();
            return ;
        }
        //获取账户对应的密码
        String password = Global.users.get(name);
        if(password!=null&&password.equals(data)) {
            //更新登陆列表
            Global.loginUsers.add(name);
            //更新登陆状态
            client.setLogin(true);
            message = "230 user:"+name+" login successful";
        }else {
            message = "530 wrong code,try again  .";
        }
        writer.write(message+".---------------\r\n");
        writer.flush();
    }



}
