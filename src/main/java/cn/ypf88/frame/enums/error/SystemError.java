package cn.ypf88.frame.enums.error;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.enums
 * @date 2016/8/3 0:22
 * @describe 系统错误码枚举类型
 */
public enum SystemError implements ErrorCode {
    NOT_EMPTY("SYS0000", "{[0]}不能为空"),
    NO_FOUND_ACTIONS("SYS0001", "{[0]}业务入口未找到");

    SystemError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
