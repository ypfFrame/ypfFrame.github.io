package cn.ypf88.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Locale;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util
 * @date 2016/8/5 1:26
 * @describe 属性文件相关工具测试类
 */
public class PropertiesUtilsTest {

    private String[] fileNames;

    @Before
    public void setUp() throws Exception {
        String[] fileNames = new String[]{"config.properties", "classpath:config.properties", "classpath: config.properties", "ClassPath:config.properties", "classPath:     config.properties", "classpath:  /config/config.properties", "classPath：config/config.properties"};
    }

    @After
    public void tearDown() throws Exception {
        fileNames = null;
    }

    @Test
    public void testGetProperties() throws Exception {
        Assert.assertNotNull("错误属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.ERROR));
        Assert.assertNotNull("异常属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.EXCEPTION));
        Assert.assertNotNull("信息属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.MSG));
    }

    @Test
    public void testGetProperties1() throws Exception {
        Assert.assertNotNull("错误属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.ERROR.getName()));
        Assert.assertNotNull("异常属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.EXCEPTION.getName()));
        Assert.assertNotNull("信息属性文件不存在", PropertiesUtils.getProperties(PropertiesUtils.Prop.MSG.getName()));
    }

    @Test
    public void testGetInfo() throws Exception {
        Assert.assertEquals("错误属性文件中不存在ERR001属性值", "中文错误", PropertiesUtils.getInfo(PropertiesUtils.Prop.ERROR, "ERR001"));
        Assert.assertEquals("异常属性文件中不存在EXC005属性值", "链接未响应数据", PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION, "EXC005"));
        Assert.assertEquals("信息属性文件中不存在MSG001属性值", "中文消息", PropertiesUtils.getInfo(PropertiesUtils.Prop.MSG, "MSG001"));
    }

    @Test
    public void testGetInfo1() throws Exception {
        Assert.assertEquals("错误属性文件中不存在ERR001属性值", "中文错误[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.ERROR, "ERR002", "参数"));
        Assert.assertEquals("异常属性文件中不存在EXC005属性值", "日期格式化异常：解析格式：[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION, "EXC001", "参数"));
        Assert.assertEquals("信息属性文件中不存在MSG001属性值", "中文消息[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.MSG, "MSG002", "参数"));
    }

    @Test
    public void testGetInfo2() throws Exception {
        Assert.assertEquals("错误属性文件中不存在ERR001属性值", "中文错误", PropertiesUtils.getInfo(PropertiesUtils.Prop.ERROR.getName(), "ERR001"));
        Assert.assertEquals("异常属性文件中不存在EXC005属性值", "链接未响应数据", PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION.getName(), "EXC005"));
        Assert.assertEquals("信息属性文件中不存在MSG001属性值", "中文消息", PropertiesUtils.getInfo(PropertiesUtils.Prop.MSG.getName(), "MSG001"));
    }

    @Test
    public void testGetInfo3() throws Exception {
        Assert.assertEquals("错误属性文件中不存在ERR001属性值", "中文错误[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.ERROR.getName(), "ERR002", "参数"));
        Assert.assertEquals("异常属性文件中不存在EXC005属性值", "日期格式化异常：解析格式：[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.EXCEPTION.getName(), "EXC001", "参数"));
        Assert.assertEquals("信息属性文件中不存在MSG001属性值", "中文消息[参数]", PropertiesUtils.getInfo(PropertiesUtils.Prop.MSG.getName(), "MSG002", "参数"));
    }

    @Test
    public void testGetLocale() throws Exception {
        Locale locale = PropertiesUtils.getLocale();
        Assert.assertNotNull("获取地区语言失败", locale);
    }

    @Test
    public void testSetLocale() throws Exception {
        Locale l = Locale.ENGLISH;
        PropertiesUtils.setLocale(l);
        Assert.assertSame("设置地区语言失败", l, PropertiesUtils.getLocale());
    }
}
