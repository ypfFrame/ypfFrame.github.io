package cn.ypf88.server.actions;

import cn.ypf88.frame.actions.Action;
import cn.ypf88.frame.contexts.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.server.actions
 * @date 2016/8/3 1:25
 * @describe 业务逻辑测试类
 */
public class IndexActions extends Action {

    @Override
    public void execute(Context context) {
        context.setData("name", "张三");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "李四");
        map.put("age", 14);
        map.put("address", "北京市");
        map.put("sex", "男");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("name", "王五");
        map.put("age", 16);
        map.put("address", "陕西省");
        map.put("sex", "女");
        list.add(map);
        context.setData("list", list);
    }
}
