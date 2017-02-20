package cn.ypf88.util.web;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util.web
 * @date 2016/3/29 23:48
 * @describe Web相关工具类
 */
public class WebUtils {
    // 项目根目录
    public static final String BASE_PATH;

    static {
        String base = WebUtils.class.getResource("/").getFile();
        if (base.indexOf("WEB-INF") > -1) {
            BASE_PATH = base.substring(0, base.indexOf("WEB-INF"));
        } else {
            BASE_PATH = base;
        }
    }
}
