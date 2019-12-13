package com.zwkj.ceng;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;

public class Test {

	public static void main(String[] args) {

		String cmd = "if [ -f #{TARNAME} ];then\r\n" + "	echo \" #{ARNAME} 存在 \"\r\n" + "else\r\n"
				+ "	echo \" #{TARNAME} 不存在 \"\r\n" + "fi";

		String change = "TARNAME";

		Map<String, String> map = new HashMap<String, String>();
		map.put("TARNAME", "www");
		map.put("ARNAME", "sss");
		String tmp = parseStringValue(cmd, map, new HashSet());
		System.out.println(tmp);

	}

	private static String parseStringValue(String cmd, Map<String, String> change, Set visitedPlaceholders) {
		StringBuffer buf = new StringBuffer(cmd);
		int startIndex = cmd.indexOf("#{");
		String propVal = null;
		while (startIndex != -1) {
			int endIndex = buf.toString().indexOf("}", startIndex + "#{".length());
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex + "#{".length(), endIndex);
				if (!visitedPlaceholders.add(placeholder)) {
					throw new BeanDefinitionStoreException(
							"Circular placeholder reference '" + placeholder + "' in property definitions");
				}
				// 用System.getEnv和外部的properties文件替代了#{}中间的值
//				String propVal = resolvePlaceholder(placeholder, props, this.systemPropertiesMode);
//				propVal = cmd.replace(placeholder, change);

				for (Map.Entry<String, String> map : change.entrySet()) {
					if (map.getKey().equals(placeholder)) {
						propVal = cmd.replaceAll("\\#\\{" + placeholder + "\\}", map.getValue());
					}
				}

				if (propVal != null) {
					// Recursive invocation, parsing placeholders contained in the
					// previously resolved placeholder value.
					// 嵌套执行;直至无法解析；
					propVal = parseStringValue(propVal, change, visitedPlaceholders);
//					buf.replace(startIndex, endIndex + "}".length(), propVal);
					startIndex = propVal.toString().indexOf("#{");
				}
//				else if (this.ignoreUnresolvablePlaceholders) {
//					// Proceed with unprocessed value.
//					startIndex = buf.toString().indexOf("#{", endIndex + "}".length());
//				} 

				else {
					throw new BeanDefinitionStoreException("Could not resolve placeholder '" + placeholder + "'");
				}
				visitedPlaceholders.remove(placeholder);
			} else {
				startIndex = -1;
			}
		}
	
		return buf.toString();
	}

}
