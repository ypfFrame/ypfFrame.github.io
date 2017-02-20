package cn.ypf88.frame.utils;

import cn.ypf88.frame.enums.error.ErrorCode;
import cn.ypf88.frame.exceptions.FailException;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.exceptions
 * @date 2016/7/30 0:32
 * @describe 异常信息工具类
 */
public class ExceptionUtils {

    public static FailException throwFail(String code, String message) throws FailException {
        throw new FailException(code, message);
    }

    public static FailException throwFail(String code, String message, Object... params) throws FailException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                message = message.replace("[" + i + "]", String.valueOf(params[i]));
            }
        }
        throw new FailException(code, message);
    }

    public static FailException throwFail(ErrorCode error) throws FailException {
        return throwFail(error.getCode(), error.getMessage());
    }

    public static FailException throwFail(ErrorCode error, Object... params) throws FailException {
        return throwFail(error.getCode(), error.getMessage(), params);
    }
}
