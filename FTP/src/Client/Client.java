package Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame
{
    FTPUtil ftpUtil=new FTPUtil();
    JTextField jtf=new JTextField(25);
    String hostName = "127.0.0.1";
    int port = 21;
    String userName = null;
    String passWord = null;
    String pathName = userName;
    String fileName = null;
    String originFileName = null;//上传时会用到这个路径+文件名
    public Client()
    {
        JFrame jf=new JFrame("ftp客户端");
        jf.setSize(200,150);
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = null;
        JLabel lip1=new JLabel("ip");
        JLabel lip=new JLabel(hostName);
        JLabel lport1=new JLabel("端口号");
        JLabel lport=new JLabel(String.valueOf(port));
        JLabel labelup=new JLabel("上传文件路径");
        //JButton b1=new JButton("选择文件");
        JButton button1=new JButton("上传");
        JButton button2=new JButton("下载");
        JLabel labeldown=new JLabel("下载文件路径");
        JTextField textField2=new JTextField(20);
        JLabel l1=new JLabel("用户名");
        JLabel l2=new JLabel("密码");
        JTextField user=new JTextField(20);
        JTextField pass=new JTextField(20);
        JLabel label1=new JLabel("模式选择");    //创建标签
        JComboBox cmb=new JComboBox();
        cmb.addItem("--请选择--");    //向下拉列表中添加一项
        cmb.addItem("主动模式");
        cmb.addItem("被动模式");

        JLabel chose=new JLabel("传输方式");
        JRadioButton choose1=new JRadioButton("二进制传输");
        JRadioButton choose2=new JRadioButton("ASCLL传输");
        JRadioButton choose3=new JRadioButton("使用被动模式");//Here

        JLabel labeldelete=new JLabel("删除文件");
        JTextField textField3=new JTextField(20);
        JButton button3=new JButton("删除");
        ButtonGroup group = new ButtonGroup();
        group.add(choose1);
        group.add(choose2);
        group.add(choose3);
        //布局
        JPanel panel=new JPanel(gridBag);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(lip,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(lport1,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(lport,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(l1,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(user,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(l2,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(cmb,c);
        c=new GridBagConstraints();
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.fill=GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(pass,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(button1,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(jtf,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(chose,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(choose1,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(choose2,c);
        c=new GridBagConstraints();
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.fill=GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(button1,c);
        c=new GridBagConstraints();
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.fill=GridBagConstraints.BOTH;
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(labeldelete,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(textField3,c);
        c=new GridBagConstraints();
        gridBag.addLayoutComponent(button3,c);
        //布局
        panel.add(lip1);
        panel.add(lip);
        panel.add(lport1);
        panel.add(lport);
        panel.add(l1);
        panel.add(user);
        panel.add(l2);
        panel.add(pass);
        panel.add(labelup);
        panel.add(cmb);
        panel.add(jtf);
        panel.add(chose);
        panel.add(choose1);
        panel.add(choose2);
        panel.add(button1);
        panel.add(labeldown);
        panel.add(textField2);
        panel.add(button2);
        panel.add(textField3);
        panel.add(button3);
        jf.add(panel);
        jf.pack();    //自动调整大小
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button1.addActionListener(new ActionListener() {//对上传按钮添加监听事件
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                int transMethod= 0;//如果忘记选，默认为Ascii传输
                String userName=user.getText();
                String passWord=pass.getText();
                DefaultButtonModel model = (DefaultButtonModel) choose1.getModel();
                if(model.getGroup().isSelected(model))transMethod=1;
                else transMethod=0;
                JFileChooser fc=new JFileChooser("D:\\");
                int val=fc.showOpenDialog(null);    //文件打开对话框
                if(val==fc.APPROVE_OPTION)
                {
                    //正常选择文件
                    jtf.setText(fc.getSelectedFile().toString());
                }
                else
                {
                    //未正常选择文件，如选择取消按钮
                    jtf.setText("未选择文件");
                }
                String originFileName;
                pathName="\\"+userName;
                originFileName=jtf.getText();
                System.out.println(transMethod);
                System.out.println(pathName);
                System.out.println(originFileName);
                String [] sz;
                sz = originFileName.split("\\\\");
                fileName=sz[sz.length-1];
                FTPUtil.uploadFile(hostName, port, userName, passWord, pathName, fileName, originFileName,transMethod);
            }

        });
        button2.addActionListener(new ActionListener() {//对下载按钮添加监听事件
            public void actionPerformed(ActionEvent arg0) {
                String localPath="D:\\download";
                String userName=user.getText();
                String passWord=pass.getText();
                String fileName=textField2.getText();
                pathName="\\"+userName;
                System.out.println(pathName);
                System.out.println(fileName);
                FTPUtil.downloadFile(hostName, port, userName, passWord, pathName, fileName, localPath);
            }
        });
        button3.addActionListener(new ActionListener() {//对下载按钮添加监听事件
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String userName=user.getText();
                String passWord=pass.getText();
                pathName="\\"+userName;
                fileName=textField3.getText();
                System.out.println(pathName);
                System.out.println(fileName);
                FTPUtil.deleteFile(hostName,port,userName,passWord,pathName,fileName);
            }
        });
    }

    public static void main(String[] args)
    {
        new Client();
    }
}
