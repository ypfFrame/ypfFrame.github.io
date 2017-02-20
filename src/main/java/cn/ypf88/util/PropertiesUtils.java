package cn.ypf88.util;

import cn.ypf88.util.web.WebUtils;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util
 * @date 2016/3/31 23:42
 * @describe 属性文件相关工具类
 */
public class PropertiesUtils {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PropertiesUtils.class);
    public static Locale locale = Locale.getDefault();
    private static File basePath = new File(WebUtils.BASE_PATH);
    private static Pattern pattern = Pattern.compile("^classpath[:|：][ ]*(.+)", Pattern.CASE_INSENSITIVE);

    static {
        String base = PropertiesUtils.class.getResource("/").getFile();
        if (base.indexOf("WEB-INF") > -1) {
            base = base.substring(0, base.indexOf("WEB-INF"));
        }
        basePath = new File(base);
    }

    private PropertiesUtils() {
    }
    /**
     * 获取对应的属性文件对象
     *
     * @param prop 工具类中的属性文件枚举对象,应用本身预定义属性文件名称
     * @return 属性文件对象
     * @throws IOException 读取属性文件异常
     */
    public static Properties getProperties(PropertiesUtils.Prop prop) throws IOException {
        Properties p = new Properties();
        InputStream is;
        try {
            is = getFileStream(getFile(prop.getName()));
        } catch (IOException e) {
            is = ClassLoader.getSystemResourceAsStream(prop.getName());
            if (is == null) {
                logger.error("当前属性文件[{}-{}]不存在。当前工具类不支持[{}]语言。", prop.getName(), prop.getDesc(), locale.getDisplayName());
                throw new FileNotFoundException("当前属性文件[" + prop.getName() + "-" + prop.getDesc() + "]不存在。当前工具类不支持[" + locale.getDisplayName() + "]语言。");
            }
        }
        p.load(is);
        return p;
    }

    /**
     * 获取对应的属性文件对象
     *
     * @param propPath 工具类中的属性文件名称
     * @return 属性文件对象
     * @throws IOException 读取属性文件异常
     */
    public static Properties getProperties(String propPath) throws IOException {
        Properties p = new Properties();
        InputStream is;
        try {
            is = getFileStream(getFile(propPath));
        } catch (IOException e) {
            is = ClassLoader.getSystemResourceAsStream(propPath);
            if (is == null) {
                logger.error("当前属性文件[{}]不存在。当前工具类不支持[{}]语言。", propPath, locale.getDisplayName());
                throw new FileNotFoundException("当前属性文件[" + propPath + "]不存在。当前工具类不支持[" + locale.getDisplayName() + "]语言。");
            }
        }
        p.load(is);
        return p;
    }

    /**
     * 获取属性文件中对应的属性值
     *
     * @param prop 要查询的属性文件
     * @param key  属性文件中的key
     * @return 返回查询到的属性文件对应值
     * @throws IOException 读取属性文件异常
     */
    public static String getInfo(PropertiesUtils.Prop prop, String key) throws IOException {
        Properties p = getProperties(prop);
        return p.getProperty(key);
    }

    /**
     * 获取属性文件中对应的属性值，并通过参数列表替换属性值中对应位置
     *
     * @param prop   要查询的属性文件
     * @param key    属性文件中的key
     * @param params 参数列表
     * @return 返回查询到的属性文件对应值
     * @throws IOException 读取属性文件异常
     */
    public static String getInfo(PropertiesUtils.Prop prop, String key, Object... params) throws IOException {
        Properties p = getProperties(prop);
        String value = p.getProperty(key);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                value = value.replaceAll("\\{" + (i + 1) + "\\}", String.valueOf(params[i]));
            }
        }
        return value;
    }

    /**
     * 获取属性文件中对应的属性值
     *
     * @param propPath 要查询的属性文件名称
     * @param key      属性文件中的key
     * @return 返回查询到的属性文件对应值
     * @throws IOException 读取属性文件异常
     */
    public static String getInfo(String propPath, String key) throws IOException {
        Properties p = getProperties(propPath);
        return p.getProperty(key);
    }

    /**
     * 获取属性文件中对应的属性值，并通过参数列表替换属性值中对应位置
     *
     * @param propPath 要查询的属性文件名称
     * @param key      属性文件中的key
     * @param params   参数列表
     * @return 返回查询到的属性文件对应值
     * @throws IOException 读取属性文件异常
     */
    public static String getInfo(String propPath, String key, Object... params) throws IOException {
        Properties p = getProperties(propPath);
        String value = p.getProperty(key);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                value = value.replaceAll("\\{" + (i + 1) + "\\}", String.valueOf(params[i]));
            }
        }
        return value;
    }

    /**
     * 获取文件路径字符串的文件对象
     *
     * @param fileName 文件路径字符串。classpath:开头-表示从classes文件夹为相对路径。默认以项目根目录为相对路径
     * @return
     */
    private static File getFile(String fileName) {
        File file = null;
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
            File classPath = new File(basePath, "WEB-INF/classes");
            file = new File(classPath, matcher.group(1));
        } else {
            file = new File(basePath, fileName);
        }
        return file.getAbsoluteFile();
    }

    /**
     * 获取File对象的输入流对象
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private static InputStream getFileStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /**
     * 获取当前系统语言对象
     *
     * @return 当前系统语言对象
     */
    public static Locale getLocale() {
        return PropertiesUtils.locale;
    }

    /**
     * 修改系统语言参数
     *
     * @param locale
     */
    public static void setLocale(Locale locale) {
        PropertiesUtils.locale = locale;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(PropertiesUtils.getLocale().getLanguage());
    }

    public static enum Prop {
        /**
         * exceptions_[Locale].properties<br/>
         * <dl>
         * <dt>example:</dt>
         * <dd>exceptions_zh_CN.properties(LOCALE.CHINA)</dd>
         * <dd>exceptions_zh_TW.properties(LOCALE.TAIWAN)</dd>
         * <dd>exceptions_en_US.properties(LOCALE.US)</dd>
         * </dl>
         */
        EXCEPTION("exceptions_" + locale.toString() + ".properties", locale.getDisplayName() + "异常信息资源文件"),
        /**
         * errors_[Locale].properties<br/>
         * <dl>
         *     <dt>example:</dt>
         *     <dd>errors_zh_CN.properties(LOCALE.CHINA)</dd>
         *     <dd>errors_zh_TW.properties(LOCALE.TAIWAN)</dd>
         *     <dd>errors_en_US.properties(LOCALE.US)</dd>
         * </dl>
         */
        ERROR("errors_" + locale.toString() + ".properties", locale.getDisplayName() + "错误信息资源文件"),
        /**
         * messages_[Locale].properties
         * <dl>
         *     <dt>example:</dt>
         *     <dd>messages_zh_CN.properties(LOCALE.CHINA)</dd>
         *     <dd>messages_zh_TW.properties(LOCALE.TAIWAN)</dd>
         *     <dd>messages_en_US.properties(LOCALE.US)</dd>
         * </dl>
         */
        MSG("messages_" + locale.toString() + ".properties", locale.getDisplayName() + "消息信息资源文件");

        private String fileName;
        private String desc;

        Prop(String fileName, String desc) {
            this.fileName = fileName;
            this.desc = desc;
        }

        public String getName() {
            return this.fileName;
        }

        public String getDesc() {
            return this.desc;
        }
    }
}
