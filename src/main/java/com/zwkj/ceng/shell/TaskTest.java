package com.zwkj.ceng.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class TaskTest {

	private static final String HOSTNAME = "122.51.239.204";

	private static final int PORT = 22;

	private static final String USERNAME = "root";

	private static final String PASSWORD = "Zhangzhicheng!3";

	private static final String DEFAULTCHART = "UTF-8";

	static Connection conn = null;

	static String PATH = "/usr/cceng/test/";
	static String PROJECTNAME = "TestMybatis-1.0-SNAPSHOT";
	static String FULLPATH = PATH + PROJECTNAME;
	static String TARNAME = "TestMybatis-1.0-SNAPSHOT.tar.gz";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		conn = getConnection(HOSTNAME, PORT, USERNAME, PASSWORD);
//		String result = "";
//		// 查询 tar文件 是否存在
//		result = execute(conn, "if [ -f " + FULLPATH + " ];then\n" + "echo " + TARNAME + "存在 \n" + "else \n" + "echo "
//				+ TARNAME + "不存在\n" + "fi");
//		System.out.println("result  ==> \n " + result);
//		// 查询 项目 是否运行 运行则删除
//		result = execute(conn, "ps -ef | grep java |awk '{print $2}' ");
//		System.out.println("result  ==> \n" + result);

		List<String> cmds = new ArrayList<String>();
		cmds.add("if [ -f " + FULLPATH + " ];then\n" + "echo " + TARNAME + "存在 \n" + "else \n" + "echo " + TARNAME
				+ "不存在\n" + "fi");

		cmds.add("ps -ef | grep java |awk '{print $2}' ");

		BaseTask task = new BaseTask(conn, cmds);
		task.excutors(new Listener() {

			@Override
			public String onSuccess(String str) {
				// TODO Auto-generated method stub
				System.out.println("onSuccess  --- " + str);
				return "onSuccess" + str;
			}

			@Override
			public String onError(String str) {
				// TODO Auto-generated method stub
				System.out.println("onError" + str);
				return "onError" + str;
			}
		});

	}

	/*
	 * 获取连接
	 */
	public static Connection getConnection(String HOSTNAME, int PORT, String USERNAME, String PASSWORD) {
		Connection conn = null;
		try {
			conn = new Connection(HOSTNAME, PORT);
			conn.connect();
			boolean isConn = conn.authenticateWithPassword(USERNAME, PASSWORD);
			if (!isConn) {
				System.out.println("连接服务器失败！用户名或者密码错误！" + "用户名 ： " + USERNAME + "密码  ： " + PASSWORD);
				return null;
			}
			System.out.println("连接服务器成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Session getSession(Connection conn) {
		Session session = null;
		try {
			if (conn != null) {
				session = conn.openSession();// 打开一个会话
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}

	/**
	 * 执行shll脚本或者命令
	 *
	 * @param shell 命令(多条命令以；隔开)
	 * @return 结果
	 */
	public static String execute(Connection conn, String shell) {
		String result = "";
		System.out.println(" shell ----> " + shell);
		try {
			if (conn != null) {
				Session session = conn.openSession();// 打开一个会话
				session.execCommand(shell);// 执行命令
				result = processStdout(session.getStdout(), DEFAULTCHART);
				// 如果为得到标准输出为空，说明脚本执行出错了
				if (StringUtils.isEmpty(result)) {
					result = processStdout(session.getStderr(), DEFAULTCHART);
				}
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 *
	 * @param in      输入流对象
	 * @param charset 编码
	 * @return 以纯文本的格式返回
	 */
	private static String processStdout(InputStream in, String charset) {
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
			String line;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\n");
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/*
	 * 上传文件
	 */
	public static void upFile(Connection conn) {

		SCPClient clt = null;

		try {

			clt = conn.createSCPClient();

			clt.put("d:\\create_zk_config.txt", "/");

			System.out.println("上传文件成功！");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}

	/*
	 * 执行远程sh脚本
	 */
	public static void runSH(Connection conn) {

		Session ssh = null;

		// 执行命令
		try {
			ssh = conn.openSession();

			ssh.execCommand("sh /t.sh");
			// 只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常.
			// 使用多个命令用分号隔开
			// ssh.execCommand("cd /root; sh hello.sh");

			// 将Terminal屏幕上的文字全部打印出来
			InputStream is = new StreamGobbler(ssh.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			while (true) {

				String line = br.readLine();

				if (line == null || "".equals(line)) {
					break;
				}

				System.out.println("脚本执行结果 ：" + line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (ssh != null) {
				ssh.close();
			}

			if (conn != null) {
				conn.close();
			}
		}

	}

}