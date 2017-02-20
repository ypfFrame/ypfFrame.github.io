package cn.ypf88.services.server;

import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.services.server
 * @date 2016/5/16 1:18
 * @describe 逻辑处理测试类接口
 */
public interface TestServer {

    public List<Map<String, Object>> find(Map<String, Object> params);
}
