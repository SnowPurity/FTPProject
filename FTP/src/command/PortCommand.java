package command;

import server.ClientDeal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class PortCommand implements Command {
    @Override

    public void deal(BufferedWriter writer, String data, ClientDeal client) throws IOException {
        String[] ipAndPort = data.split(",");
        //需要设置ip和端口号
        StringBuilder ip = new StringBuilder();
        int port = 0;
        String message = "200 port and IP are set up .---------------\r\n";
        //Port命令后六个数字用逗号隔开,其中前四个数字组成IP地址,第5个数字乘以256再加上第6个数字为实际的端口号
        if(ipAndPort.length==6) {
            for(int i=0;i<4;i++) {
                ip.append(ipAndPort[i]);
                if (i != 3) {
                    ip.append(".");
                }
            }
            port = Integer.parseInt(ipAndPort[4])*256;
            port += Integer.parseInt(ipAndPort[5]);
            //设置
            client.setIp(ip.toString());
            client.setPort(port);
        }else {
            message = "503 IP or port number has mistaken ---------------------\r\n";
        }
        writer.write(message);
        writer.flush();
    }
}
