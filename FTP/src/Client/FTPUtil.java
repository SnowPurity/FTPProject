package Client;

import java.io.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {


    /**
     * 上传文件
     *
     * @param hostName         FTP服务器地址
     * @param port             FTP服务器端口号
     * @param userName         FTP登录帐号
     * @param passWord         FTP登录密码
     * @param pathName         FTP服务器保存目录
     * @param fileName         上传到FTP服务器后的文件名称
     * @param originalFileName 源文件地址和名称
     * @param transMethod      传输方法 二进制/ASCII
     * @return
     */

    public static boolean uploadFile(String hostName, int port, String userName, String passWord,
                                     String pathName, String fileName, String originalFileName, int transMethod) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originalFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ftpClient.setControlEncoding("GBK");
        try {
            ftpClient.connect(hostName, port);
            ftpClient.login(userName, passWord);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }

            System.out.println("上传至  " + pathName + "   文件:" + fileName);

            ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);
            if (transMethod == 0) {
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);//----->>ASCII传输方法
                System.out.println("您使用了ASCII传输");
            } else {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//----->>二进制传输方法
                System.out.println("您使用了二进制传输");

            }
            ftpClient.storeFile(fileName, inputStream);
            System.out.println("传输成功！");
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            close(ftpClient);
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param hostName FTP服务器地址
     * @param port     FTP服务器端口号
     * @param userName FTP登录帐号
     * @param passWord FTP登录密码
     * @param pathName FTP服务器保存目录
     * @param fileName 要删除的文件名称
     */
    public static void deleteFile(String hostName, int port, String userName, String passWord, String pathName, String fileName) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(hostName, port);
            //登录FTP服务器
            ftpClient.login(userName, passWord);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return;
            }
            //切换FTP目录
            System.out.println("您删除的是" + pathName + " 上的文件 " + fileName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.dele(fileName);
            System.out.println("删除成功！");
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            //e.printStackTrace();此处报错：connection closed without indication，但是功能仍然实现。
        } finally {
            close(ftpClient);
        }
    }

    /**
     * 下载文件
     *
     * @param hostName  FTP服务器地址
     * @param port      FTP服务器端口号
     * @param userName  FTP登录帐号
     * @param passWord  FTP登录密码
     * @param pathName  FTP服务器文件目录
     * @param fileName  文件名称
     * @param localPath 下载后的文件路径
     */
    public static void downloadFile(String hostName, int port, String userName, String passWord,
                                    String pathName, String fileName, String localPath) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(hostName, port);
            //登录FTP服务器
            ftpClient.login(userName, passWord);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathName);
            String f_ame = new String(fileName.getBytes("GBK"),
                    FTPClient.DEFAULT_CONTROL_ENCODING);    //编码文件格式,解决中文文件名
            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(f_ame, os);
            os.close();

            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            close(ftpClient);
        }
    }

    //确认关闭FTP客户端
    public static void close(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            ftpClient = null;
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

}/*

    //以下是测试代码


    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int port = 21;

        String userName = "duck";
        String passWord = "ILOVEDUCK";
        String pathName = "\\" + userName;//上传文件时指定用户名对应的文件夹


        String fileName = "uploaded123.txt";
        String originFileName = "D:\\test123.txt";//上传时会用到这个路径+文件名
        String localPath = "D:\\download";//下载时会用到这个路径
        int transMethod = 1;//0-->二进制传输，1-->ASCII传输

        FTPUtil.uploadFile(hostName, port, userName, passWord, pathName, fileName, originFileName, transMethod);
        FTPUtil.downloadFile(hostName, port, userName, passWord, pathName, fileName, localPath);
        FTPUtil.deleteFile(hostName, port, userName, passWord, pathName, fileName);
        System.out.println("操作已经全部完成，请检查文件");
    }
    }
    */