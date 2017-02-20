package cn.ypf88.frame.contexts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.beans
 * @date 2016/8/1 23:20
 * @describe 上下文数据对象实体，用于业务逻辑中请求参数及响应数据封装。拥有最小的数据操作功能
 */
public class Context implements java.io.Serializable {
    // 交易码
    protected String tranCode;
    // 目标交易码
    protected String targetTranCode;
    // 交易状态。1=交易成功；0=交易失败
    protected String status;
    // 过滤后的请求参数
    protected Map<String, Object> dataMap = new HashMap<String, Object>(0);
    // 源请求参数
    protected Map<String, Object> rowData = new HashMap<String, Object>(0);

    public String getTranCode() {
        return this.tranCode;
    }

    public Object getData(String key) {
        return this.dataMap.get(key);
    }

    public void setData(String key, Object value) {
        this.dataMap.put(key, value);
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = new HashMap<String, Object>(dataMap);
    }

}
