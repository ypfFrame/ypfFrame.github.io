package cn.ypf88.exceptions;

import cn.ypf88.util.PropertiesUtils;

import java.io.IOException;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.exceptions
 * @date 2016/4/1 0:31
 * @describe 工具类异常
 */
public class UtilsException extends Exception {
    /**
     * 异常编号
     */
    private String code;
    /**
     * 异常信息
     */
    private String message;

    public UtilsException(String code) {
        this.code = code;
        try {
            this.message = PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION, code);
        } catch (IOException e) {
            this.message = code;
        }
    }

    public UtilsException(String code, Object... params) {
        this.code = code;
        try {
            this.message = PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION, code, params);
        } catch (IOException e) {
            this.message = code;
        }
    }

    public UtilsException(Throwable throwable) {
        super.addSuppressed(throwable);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getLocalizedMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.message;
    }
}
