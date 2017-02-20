package cn.ypf88.services.actions;

import cn.ypf88.frame.actions.Action;
import cn.ypf88.frame.contexts.Context;
import cn.ypf88.frame.contexts.SubstratumContext;
import cn.ypf88.services.server.TestServer;
import cn.ypf88.services.server.impl.TestServerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.services.actions
 * @date 2016/5/15 23:47
 * @describe 业务入口测试类
 */
public class TestAction extends Action {

    private TestServer testServer = new TestServerImpl();

    @Override
    public void execute(Context context) {
        StringBuilder sb = new StringBuilder("requestString=====>[");
        for (Map.Entry<String, Object> req : ((SubstratumContext) context).getDataMap().entrySet()) {
            sb.append(req.getKey() + '=' + req.getValue() + ',');
        }
        sb.deleteCharAt(sb.length() - 1).append("]");
        List<Map<String, Object>> res = testServer.find(null);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("code", 0);
        response.put("list", res);
    }
}
