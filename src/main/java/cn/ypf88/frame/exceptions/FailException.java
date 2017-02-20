package cn.ypf88.frame.exceptions;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.exceptions
 * @date 2016/7/30 0:33
 * @describe 自定义异常类，用于系统基础异常
 */
public class FailException extends Exception {

    // 异常码
    private String code;

    public FailException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "[" + this.code + "] " + getMessage();
    }
}
