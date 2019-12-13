package com.zwkj.ceng;

import com.zwkj.ceng.shell.ReleaseModel;
import com.zwkj.ceng.shell.ShellConfig;
import org.springframework.beans.factory.BeanDefinitionStoreException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestString {


    public static void main(String[] args) throws IllegalAccessException {
        String str = "  if [ -f #{TARNAME} ];then\n" +
                "            echo \" #{TANAME} 存在\"\n" +
                "            else\n" +
                "            echo \"#{projectname} 不存在\"\n" +
                "            fi";

        int index = str.indexOf("#{");
        int last = str.indexOf("}");
        String tmp = str.substring(index + 2, last);
        System.out.println(index);
        System.out.println(last);
        System.out.println(tmp);
        System.out.println(tmp.compareToIgnoreCase("tarname"));

        ReleaseModel releaseModel = new ReleaseModel();
        releaseModel.setPort(22);
        releaseModel.setHost("3333");
        Field[] fields = releaseModel.getClass().getDeclaredFields();
        Map<String, Object> change = new HashMap<>();
        for (Field fieldname : fields) {
            fieldname.setAccessible(true);
            if (fieldname.getType().equals(String.class)) {
                change.put(fieldname.getName(), fieldname.get(releaseModel));
            }
        }
        String t = "wqwqe wqeqwe sdasd dsd ";
        for (Map.Entry<String, Object> map : change.entrySet()) {
            if (map.getKey().equals("port")) {
                t = t.replaceAll("dsd", (String) map.getValue());
            }
            if (map.getKey().equals("host")) {
                t = t.replaceAll("wqeqwe", (String) map.getValue());
            }
        }
        System.out.println(t);

        ShellConfig shellConfig = new ShellConfig();
        shellConfig.setHost("22222");
        shellConfig.setTarName("sss");
        shellConfig.setProjectName("rerere");
        try {
            str = praseCmd(str, shellConfig);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 解析cmd命令
    private static String praseCmd(String cmd, ShellConfig shellConfig) throws Exception {
        Map<String, String> change = new HashMap<>();
        Field[] fields = shellConfig.getClass().getDeclaredFields();
        for (Field fieldname : fields) {
            fieldname.setAccessible(true);
            if (fieldname.getType().equals(String.class)) {
                change.put(fieldname.getName(), (String) fieldname.get(shellConfig));
            }
        }
        return parseStringValue(cmd, change, new HashSet());
    }

    private static String parseStringValue(String cmd, Map<String, String> change, Set visitedPlaceholders) throws Exception {
        int startIndex = cmd.indexOf("#{");
        boolean isMatching = false;
        while (startIndex != -1) {
            int endIndex = cmd.toString().indexOf("}", startIndex + "#{".length());
            if (endIndex != -1) {
                String placeholder = cmd.substring(startIndex + "#{".length(), endIndex);
                if (!visitedPlaceholders.add(placeholder)) {
                    throw new Exception(
                            "Circular placeholder reference '" + placeholder);
                }
                for (Map.Entry<String, String> map : change.entrySet()) {
                    if (placeholder.compareToIgnoreCase(map.getKey()) == 0) {
                        isMatching=true;
                        cmd = cmd.replaceAll("\\#\\{" + placeholder + "\\}", map.getValue());
                    }
                }
                if (cmd != null && isMatching ) {
                    // 嵌套执行;直至无法解析；
                    cmd = parseStringValue(cmd, change, visitedPlaceholders);
                    startIndex = cmd.toString().indexOf("#{");
                } else {
                    throw new Exception(" parseStringValue could not resolve placeholder '" + placeholder + "'");
                }
                visitedPlaceholders.remove(placeholder);
            } else {
                startIndex = -1;
            }
        }
        return cmd;
    }
}