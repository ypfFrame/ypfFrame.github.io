package cn.ypf88.services.dao.impl;

import cn.ypf88.services.dao.TestDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.services.dao.impl
 * @date 2016/5/16 1:46
 * @describe 数据操作类接口实现
 */
public class TestDaoImpl implements TestDao {

    @Override
    public List<Map<String, Object>> findInfo(Map<String, Object> params) {
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("name", "张三");
        response.put("age", "14");
        response.put("sex", "男");
        response.put("phone", "12345678901");
        res.add(response);
        response = new HashMap<String, Object>();
        response.put("name", "张三");
        response.put("age", "14");
        response.put("sex", "男");
        response.put("phone", "12345678901");
        res.add(response);
        return res;
    }
}
