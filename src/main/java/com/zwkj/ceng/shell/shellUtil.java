package com.zwkj.ceng.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 *
 *
 * @author 作者： GAOXL
 * @version 创建时间：2017年1月4日 下午2:51:06
 * @copyright 公司名称:Yisa
 * @version V1.0
 */
public class shellUtil {

    private static final String HOSTNAME = "122.51.239.204";

    private static final  int PORT = 22;

    private static final String USERNAME = "root";

    private static final String PASSWORD = "Zhangzhicheng1";

    /**
     * @param args
     */
    public static void main(String[] args) {

        Connection conn = getConnection();

//        upFile(conn);

        runSH(conn);

    }

    /*
     * 获取连接
     */
    public static Connection getConnection(){

        Connection conn = null;

        try {

            conn = new Connection(HOSTNAME, PORT);

            conn.connect();

            boolean isConn = conn.authenticateWithPassword(USERNAME, PASSWORD);

            if(!isConn){

                System.out.println("连接服务器失败！用户名或者密码错误！" + "用户名 ： " + USERNAME + "密码  ： " + PASSWORD);

                return null;
            }

            System.out.println("连接服务器成功！");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return conn;
    }

    /*
     * 上传文件
     */
    public static void upFile(Connection conn){

        SCPClient clt = null;

        try {

            clt = conn.createSCPClient();

            clt.put("d:\\create_zk_config.txt", "/");

            System.out.println("上传文件成功！");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                conn.close();
            }
        }

    }

    /*
     * 执行远程sh脚本
     */
    public static void runSH(Connection conn){

        Session ssh = null;

        // 执行命令
        try {
            ssh = conn.openSession();

            ssh.execCommand("sh /t.sh");
            //只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常.
            //使用多个命令用分号隔开
            //ssh.execCommand("cd /root; sh hello.sh");

            // 将Terminal屏幕上的文字全部打印出来
            InputStream is = new StreamGobbler(ssh.getStdout());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while(true){

                String line = br.readLine();

                if(line == null || "".equals(line)){
                    break;
                }

                System.out.println("脚本执行结果 ：" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(ssh != null){
                ssh.close();
            }

            if(conn != null){
                conn.close();
            }
        }


    }
}
