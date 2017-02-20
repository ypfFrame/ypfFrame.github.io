package cn.ypf88.util;

import java.util.Random;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util
 * @date 2016/3/29 22:12
 * @describe 字符串工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * <dl>
     * <dt>示例：</dt>
     * <dd>null------true</dd>
     * <dd>""--------true</dd>
     * <dd>" "-------true</dd>
     * </dl>
     *
     * @param str 待测试的字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空
     * <dl>
     * <dt>示例：</dt>
     * <dd>null------false</dd>
     * <dd>""--------false</dd>
     * <dd>" "-------false</dd>
     * </dl>
     *
     * @param str 待测试的字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 生成随机字符串
     *
     * @param length     字符串长度
     * @param hasChar    是否包含英文字符
     * @param hasChinese 是否包含中文字符
     * @return 生成的随机字符串
     */
    public static String randomString(int length, boolean hasChar, boolean hasChinese) {
        Random r = new Random(new Random().nextInt());
        StringBuffer sb = new StringBuffer();
        if (!hasChar && !hasChinese) {
            while (sb.length() < length) {
                sb.append(DATA[0][r.nextInt(DATA[0].length)]);
            }
        } else if (hasChar && !hasChinese) {
            while (sb.length() < length) {
                sb.append(DATA[1][r.nextInt(DATA[1].length)]);
            }
        } else if (!hasChar && hasChinese) {
            while (sb.length() < length) {
                sb.append((char) (0x4e00 + r.nextInt(DELTA)));
            }
        } else {
            while (sb.length() < length) {
                if (r.nextBoolean()) {
                    sb.append((char) (0x4e00 + r.nextInt(DELTA)));
                } else {
                    sb.append(DATA[1][r.nextInt(DATA[1].length)]);
                }
            }
        }
        return sb.toString();
    }

    private static final String[][] DATA = {"0123456789".split(""), "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("")};
    private final static int DELTA = 0x9fa5 - 0x4e00 + 1;

}