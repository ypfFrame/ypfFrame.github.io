package cn.ypf88.util;

import cn.ypf88.exceptions.ExceptionErrorCode;
import cn.ypf88.exceptions.UtilsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util
 * @date 2016/3/29 15:04
 * @describe 日期工具类
 */
public class DateUtils {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DateUtils.class);
    private static final String[] patterns = new String[]{"yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmssSSS", "yyyy/MM/dd HH:mm:ss SSS", "yyyy-MM-dd HH:mm:ss SSS"};
    public static final String defaultPattern = patterns[0];
    public static final String logPattern = patterns[patterns.length - 1];

    /**
     * 获取当前日期字符串
     *
     * @return 当前日期字符串
     */
    public String getCurrDate() {
        try {
            return formatDate(new Date());
        } catch (UtilsException e) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.format(new Date());
        }
    }

    /**
     * 获取传入的日期解析格式的当前日期字符串
     *
     * @param pattern 日期解析格式
     * @return 当前日期字符串
     * @throws UtilsException 工具类异常
     */
    public String getCurrDate(String pattern) throws UtilsException {
        return formatDate(new Date(), pattern);
    }

    /**
     * 以默认格式格式化日期对象
     *
     * @param date 要格式化的日期对象
     * @return 返回格式化后字符串形式的日期形式
     * @throws UtilsException 工具类异常
     */
    public static String formatDate(Date date) throws UtilsException {
        return formatDate(date, defaultPattern);
    }

    /**
     * 将日期对象格式化为传入的日期解析格式型字符串
     *
     * @param date    要解析的日期格式
     * @param pattern 传入的日期解析格式
     * @return 格式化后的日期字符串
     * @throws UtilsException 工具类异常
     */
    public static String formatDate(Date date, String pattern) throws UtilsException {
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(pattern);
            return format.format(date);
        } catch (IllegalArgumentException e) {
            log.debug("日期格式化异常：解析格式：[{}]", pattern);
            throw new UtilsException(ExceptionErrorCode.ILLEGAL_ARGUMENT, pattern);
        }
    }

    /**
     * 用工具内置日期解析格式数组解析日期字符串
     *
     * @param date 传入的日期
     * @return 解析出的日期对象
     * @throws UtilsException 工具类异常
     */
    public static Date parseDate(String date) throws UtilsException {
        Date d = null;
        for (String pattern : DateUtils.patterns) {
            d = parseDate(date, pattern);
            if (d != null) {
                return d;
            }
        }
        log.debug("输入的日期字符串[{}]不能解析为日期对象", date);
        throw new UtilsException(ExceptionErrorCode.PARSE_ALL, date);
    }

    /**
     * 用传入的日期解析格式解析日期字符串
     *
     * @param date    传入的日期
     * @param pattern 传入的日期解析格式
     * @return 解析出的日期对象
     * @throws UtilsException 工具类异常
     */
    public static Date parseDate(String date, String pattern) throws UtilsException {
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(pattern);
            return format.parse(date);
        } catch (IllegalArgumentException e) {
            log.debug("日期格式化异常：解析格式：[{}]", pattern);
            throw new UtilsException(ExceptionErrorCode.ILLEGAL_ARGUMENT, pattern);
        } catch (ParseException e) {
            log.debug("日期解析异常：日期字符串：[{}]，解析格式：[{}]，解析异常索引：[{}]", date, pattern, e.getErrorOffset());
            throw new UtilsException(ExceptionErrorCode.PARSE, date, pattern, e.getErrorOffset());
        }
    }

    /**
     * 用传入的日期解析格式组解析日期字符串
     *
     * @param date     传入的日期
     * @param patterns 传入的日期解析格式组
     * @return 解析出的日期对象
     * @throws UtilsException 工具类异常
     */
    public static Date parseDate(String date, String... patterns) throws UtilsException {
        int errorOffset = -1;
        for (String pattern : patterns) {
            SimpleDateFormat format = null;

            try {
                format = new SimpleDateFormat(pattern);
                return format.parse(date);
            } catch (IllegalArgumentException e) {
                log.debug("日期格式化异常：解析格式：[{}]", pattern);
                throw new UtilsException(ExceptionErrorCode.ILLEGAL_ARGUMENT, pattern);
            } catch (ParseException e) {
                errorOffset = e.getErrorOffset();
            }
        }
        log.debug("日期解析异常：日期字符串：[{}]，解析格式：[{}]，解析异常索引：[{}]", date, patterns, errorOffset);
        throw new UtilsException(ExceptionErrorCode.PARSE, date, patterns, errorOffset);
    }

    /**
     * 获取传入日期增加天数后的日期对象
     *
     * @param date 传入的日期
     * @param day  增加的天数
     * @return 计算后的日期
     */
    public static Date addDays(Date date, int day) {
        return add(date, Field.DATE, day);
    }

    /**
     * 获取传入日期增加天数后的日期对象
     *
     * @param date  传入的日期
     * @param field 操作的字段,匹配DateUtils.Field枚举
     * @param day   增加的天数
     * @return 计算后的日期字符串
     */
    public static Date add(Date date, DateUtils.Field field, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field.getCode(), day);
        return c.getTime();
    }

    /**
     * 获取传入日期字符串增加天数后的日期字符串
     *
     * @param date 传入的日期字符串
     * @param day  增加的天数
     * @return 计算后的日期
     * @throws UtilsException 工具类异常
     */
    public static String addDays(String date, int day) throws UtilsException {
        return add(date, Field.DATE, day);
    }

    /**
     * 获取传入日期字符串增加天数后的日期字符串
     *
     * @param date  传入的日期字符串
     * @param field 操作的字段,匹配DateUtils.Field枚举
     * @param day   增加的天数
     * @return 计算后的日期字符串
     * @throws UtilsException 工具类异常
     */
    public static String add(String date, DateUtils.Field field, int day) throws UtilsException {
        Date d = null;
        String pattern = null;
        for (String pat : DateUtils.patterns) {
            try {
                d = parseDate(date, pat);
            } catch (UtilsException e) {
            }
            if (d != null) {
                pattern = pat;
                break;
            }
        }
        if (d == null) {
            log.debug("输入的日期字符串[{}]不能解析为日期对象", date);
            throw new UtilsException(ExceptionErrorCode.PARSE_ALL, date);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(field.getCode(), day);
        return formatDate(c.getTime(), pattern);
    }

    /**
     * 获取传入日期字符串增加天数后的日期字符串
     *
     * @param date    传入的日期字符串
     * @param pattern 日期解析格式
     * @param field   操作的字段,匹配DateUtils.Field枚举
     * @param day     增加的天数
     * @return 计算后的日期字符串
     * @throws UtilsException 工具类异常
     */
    public static String add(String date, String pattern, DateUtils.Field field, int day) throws UtilsException {
        Date d = null;
        d = parseDate(date, pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(field.getCode(), day);
        return formatDate(c.getTime(), pattern);
    }

    /**
     * 两个日期相差天数
     *
     * @param d1 日期参数1
     * @param d2 日期参数2
     * @return 两个日期相差天数
     * @throws UtilsException 工具类异常
     */
    public static int betweenDays(Date d1, Date d2) throws UtilsException {
        int day = -1;
        long between;
        d1 = parseDate(formatDate(d1, patterns[0]));
        d2 = parseDate(formatDate(d2, patterns[0]));
        if (d1.after(d2)) {
            between = d1.getTime() - d2.getTime();
        } else {
            between = d2.getTime() - d1.getTime();
        }
        day = (int) between / (1000 * 60 * 60 * 24);
        return day;
    }

    /**
     * 两个日期相差天数
     *
     * @param d1 日期参数1
     * @param d2 日期参数2
     * @return 两个日期相差天数
     * @throws UtilsException 工具类异常
     */
    public static int betweenDays(String d1, String d2) throws UtilsException {
        int day = -1;
        long between;
        Date date1 = parseDate(d1);
        Date date2 = parseDate(d2);
        if (date1.after(date2)) {
            between = date1.getTime() - date2.getTime();
        } else {
            between = date2.getTime() - date1.getTime();
        }
        day = (int) between / (1000 * 60 * 60 * 24);
        return day;
    }

    /**
     * 两个日期相差天数
     *
     * @param d1      日期参数1
     * @param d2      日期参数2
     * @param pattern 日期解析格式
     * @return 两个日期相差天数
     * @throws UtilsException 工具类异常
     */
    public static int betweenDays(String d1, String d2, String pattern) throws UtilsException {
        int day = -1;
        long between;
        Date date1 = parseDate(d1, pattern);
        Date date2 = parseDate(d2, pattern);
        if (date1.after(date2)) {
            between = date1.getTime() - date2.getTime();
        } else {
            between = date2.getTime() - date1.getTime();
        }
        day = (int) between / (1000 * 60 * 60 * 24);
        return day;
    }

    public static enum Field {
        SECOND(Calendar.SECOND, "秒"),
        MINUTE(Calendar.MINUTE, "分钟"),
        HOUR(Calendar.HOUR, "小时"),
        DATE(Calendar.DATE, "天"),
        MOUTH(Calendar.MONTH, "月"),
        YEAR(Calendar.YEAR, "年");

        private int code;
        private String message;

        private Field(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.message;
        }

        public boolean contains(int code) {
            Field[] fields = values();
            for (Field field : fields) {
                if (field.getCode() == code) {
                    return true;
                }
            }
            return false;
        }

        public Field getByCode(int code) {
            Field[] fields = values();
            for (Field field : fields) {
                if (field.getCode() == code) {
                    return field;
                }
            }
            return null;
        }

        public String getMessageByCode(int code) {
            Field[] fields = values();
            for (Field field : fields) {
                if (field.getCode() == code) {
                    return field.getDesc();
                }
            }
            return String.valueOf(code);
        }
    }
}