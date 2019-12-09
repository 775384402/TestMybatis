package com.zwkj.ceng.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.zwkj.ceng.shell.sax.Task;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.Data;

@Data
public class BaseTask {
	private static final String DEFAULTCHART = "UTF-8";
	Connection con;
	List<String> cmds;
	String cmd;
	List<BaseTask> tasks = new ArrayList<BaseTask>();
	BaseTask nextTask;
	List<Task> tasks2 = new ArrayList<Task>();

//	public BaseTask(Connection con, List<String> cmds) {
//		this.con = con;
//		this.cmds = cmds;
//		tasks = creatTasks();
//	}

	public BaseTask(Connection con, List<Task> tasks2) {
		this.con = con;
		this.tasks2 = tasks2;
	}

	private BaseTask(Connection con, String cmd) {
		this.con = con;
		this.cmd = cmd;
	}

	private List<BaseTask> creatTasks() {
		if (con != null && !CollectionUtils.isEmpty(cmds)) {
			for (int i = 0; i < cmds.size(); i++) {
				tasks.add(new BaseTask(con, cmds.get(i)));
				if (i > 0 && i <= cmds.size() - 1) {
					tasks.get(i - 1).setNextTask(tasks.get(i));
				}
			}
		}
		return tasks;
	}

	public void excutors(Listener listener) {
		if (!CollectionUtils.isEmpty(tasks)) {
			for (int i = 0; i < tasks.size(); i++) {
				String result = tasks.get(i).excutor(listener);

				if (result.contains("不存在")) {
					listener.onError(result);
					break;
				}
			}
		}

	}

	public String excutor(Listener listener) {
		String result = "";
		try {
			Session session = con.openSession();
			session.execCommand(getCmd());
			result = processStdout(session.getStdout(), DEFAULTCHART);

			// 如果为得到标准输出为空，说明脚本执行出错了
			if (StringUtils.isEmpty(result)) {
				result = processStdout(session.getStderr(), DEFAULTCHART);
			}

			listener.onSuccess(result);

			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(stdout, charset));
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
}
