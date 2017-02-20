package cn.ypf88.exceptions;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.exceptions
 * @date 2016/4/1 0:43
 * @describe 异常错误码
 */
public interface ExceptionErrorCode {
    /**
     * 日期格式化异常：解析格式：[{1}]
     */
    public static final String ILLEGAL_ARGUMENT = "EXC001";
    /**
     * 日期解析异常：日期字符串：[{1}]，解析格式：[{2}]，解析异常索引：[{3}]
     */
    public static final String PARSE = "EXC002";
    /**
     * 输入的日期字符串[{1}]不能解析为日期对象
     */
    public static final String PARSE_ALL = "EXC003";
    /**
     * [{1}]地址不能被解析
     */
    public static final String URL_NOT_PARSE = "EXC004";
    /**
     * 链接未响应数据
     */
    public static final String CONN_NOT_RESPONSE = "EXC005";
}
