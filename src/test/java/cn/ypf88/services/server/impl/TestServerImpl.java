package cn.ypf88.services.server.impl;

import cn.ypf88.services.dao.TestDao;
import cn.ypf88.services.dao.impl.TestDaoImpl;
import cn.ypf88.services.server.TestServer;

import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.services.server.impl
 * @date 2016/5/16 1:44
 * @describe 逻辑处理测试类接口实现
 */
public class TestServerImpl implements TestServer {

    private TestDao testDao = new TestDaoImpl();

    @Override
    public List<Map<String, Object>> find(Map<String, Object> params) {
        return testDao.findInfo(params);
    }
}
