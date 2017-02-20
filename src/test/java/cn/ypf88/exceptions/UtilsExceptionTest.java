package cn.ypf88.exceptions;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.exceptions
 * @date 2016/4/19 0:37
 * @describe 异常类测试
 */
public class UtilsExceptionTest {

    private UtilsException[] exceptions;

    @Before
    public void setUp() throws Exception {
        exceptions = new UtilsException[5];
        exceptions[0] = new UtilsException(ExceptionErrorCode.ILLEGAL_ARGUMENT, "yyyyMdd");
        exceptions[1] = new UtilsException(ExceptionErrorCode.PARSE, "20140z305", "yyyyMMdd", 5);
        exceptions[2] = new UtilsException(ExceptionErrorCode.PARSE_ALL, "20120z31");
        exceptions[3] = new UtilsException(ExceptionErrorCode.URL_NOT_PARSE, "htp");
        exceptions[4] = new UtilsException(ExceptionErrorCode.CONN_NOT_RESPONSE);
    }

    @After
    public void tearDown() throws Exception {
        exceptions = null;
    }

    @Test
    public void testGetCode() throws Exception {
        for (int i = 0; i < exceptions.length; i++) {
            Assert.assertEquals("错误码不对", "EXC00" + (i + 1), exceptions[i].getCode());
        }
    }

    @Test
    public void testGetMessage() throws Exception {
        Assert.assertEquals("错误信息不对", "日期格式化异常：解析格式：[yyyyMdd]", exceptions[0].getMessage());
        Assert.assertEquals("错误信息不对", "日期解析异常：日期字符串：[20140z305]，解析格式：[yyyyMMdd]，解析异常索引：[5]", exceptions[1].getMessage());
        Assert.assertEquals("错误信息不对", "输入的日期字符串[20120z31]不能解析为日期对象", exceptions[2].getMessage());
        Assert.assertEquals("错误信息不对", "[htp]地址不能被解析", exceptions[3].getMessage());
        Assert.assertEquals("错误信息不对", "链接未响应数据", exceptions[4].getMessage());
    }

    @Test
    public void testGetLocalizedMessage() throws Exception {
        Assert.assertEquals("本地错误信息不对", "日期格式化异常：解析格式：[yyyyMdd]", exceptions[0].getLocalizedMessage());
        Assert.assertEquals("本地错误信息不对", "日期解析异常：日期字符串：[20140z305]，解析格式：[yyyyMMdd]，解析异常索引：[5]", exceptions[1].getLocalizedMessage());
        Assert.assertEquals("本地错误信息不对", "输入的日期字符串[20120z31]不能解析为日期对象", exceptions[2].getLocalizedMessage());
        Assert.assertEquals("本地错误信息不对", "[htp]地址不能被解析", exceptions[3].getLocalizedMessage());
        Assert.assertEquals("本地错误信息不对", "链接未响应数据", exceptions[4].getLocalizedMessage());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("toString错误信息不对", "EXC001:日期格式化异常：解析格式：[yyyyMdd]", exceptions[0].toString());
        Assert.assertEquals("toString错误信息不对", "EXC002:日期解析异常：日期字符串：[20140z305]，解析格式：[yyyyMMdd]，解析异常索引：[5]", exceptions[1].toString());
        Assert.assertEquals("toString错误信息不对", "EXC003:输入的日期字符串[20120z31]不能解析为日期对象", exceptions[2].toString());
        Assert.assertEquals("toString错误信息不对", "EXC004:[htp]地址不能被解析", exceptions[3].toString());
        Assert.assertEquals("toString错误信息不对", "EXC005:链接未响应数据", exceptions[4].toString());
    }
}
