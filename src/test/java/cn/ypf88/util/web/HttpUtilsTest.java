package cn.ypf88.util.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util.web
 * @date 2016/4/20 23:58
 * @describe HTTP相关工具类测试
 */
public class HttpUtilsTest {

    private HttpUtils httpUtils;

    @Before
    public void setUp() throws Exception {
        httpUtils = new HttpUtils();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        String url = "http://www.baidu.com/s";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wd", "java");
        String resp = HttpUtils.get(url, params, HttpUtils.Charset.UTF8);
        Assert.assertNotNull("未响应数据", resp);
    }

    @Test
    public void testPost() throws Exception {
        String url = "http://www.baidu.com/s";
        String resp = HttpUtils.post(url, "wd=java", HttpUtils.Charset.UTF8);
        Assert.assertNotNull("未响应数据", resp);
    }

}
