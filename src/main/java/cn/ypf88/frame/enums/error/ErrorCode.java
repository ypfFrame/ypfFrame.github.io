package cn.ypf88.frame.enums.error;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.enums
 * @date 2016/8/3 0:07
 * @describe 错误码实体接口。所有错误枚举类型均要实现该接口。异常工具类以该接口为参数，做报错处理。
 */
public interface ErrorCode {
    /**
     * 返回错误码信息
     *
     * @return 错误码
     */
    public String getCode();

    /**
     * 返回错误描述信息
     *
     * @return 详细的错误描述信息
     */
    public String getMessage();
}
