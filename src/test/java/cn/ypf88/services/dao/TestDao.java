package cn.ypf88.services.dao;

import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.services.dao
 * @date 2016/5/16 1:18
 * @describe 数据操作类接口
 */
public interface TestDao {
    public List<Map<String, Object>> findInfo(Map<String, Object> params);
}
