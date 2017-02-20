package cn.ypf88.frame.beans;

import cn.ypf88.frame.actions.Action;
import cn.ypf88.util.PropertiesUtils;
import cn.ypf88.util.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.beans
 * @date 2016/8/3 1:19
 * @describe 对象实体工厂。创建实体对象，并保存创建的对象
 */
public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);
    private static Map<String, Action> actions = new HashMap<String, Action>(0);
    private static Map<String, Object> beans = new HashMap<String, Object>(0);
    private static File basePath = new File(WebUtils.BASE_PATH);
    private static String listenerClass = "";

    public static void init() {
        String listenerPath = "";
        try {
            listenerClass = PropertiesUtils.getInfo("classpath:config.properties", "actions");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listenerPath = listenerClass.replaceAll("\\.", "/".equals(File.separator) ? "/" : "\\\\");
        File actionPath = new File(basePath, "WEB-INF/classes/" + listenerPath);
        initActions(actionPath);
    }

    private static void initActions(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    initActions(f);
                }
            } else {
                String name = file.getName();
                if (name.endsWith(".class")) {
                    String actionName = name.substring(0, name.indexOf(".class"));
                    name = actionName.replaceAll("Actions", "");
                    String key = name.substring(0, 1).toLowerCase() + name.substring(1);
                    try {
                        Class objCls = Class.forName(listenerClass + "." + actionName);
                        Class superCls = objCls.getSuperclass();
                        if ("Action".equalsIgnoreCase(superCls.getSimpleName())) {
                            Action action = (Action) objCls.newInstance();
                            actions.put(key, action);
                        }
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Action getAction(String actionName) {
        return actions.get(actionName);
    }

    public static <T> T getObject(String objName) {
        return (T) beans.get(objName);
    }

}
