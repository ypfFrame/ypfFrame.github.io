package cn.ypf88.frame.contexts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.beans
 * @date 2016/8/1 23:29
 * @describe 底层的上下文数据对象实体，用于系统内部中请求参数及响应数据封装。拥有最大的数据操作功能
 */
public class SubstratumContext extends Context {

    public Object getRowData(String key) {
        return this.rowData.get(key);
    }

    public void setRowData(String key, Object value) {
        this.rowData.put(key, value);
    }

    public void setRowDataMap(Map<String, Object> dataMap) {
        this.rowData = new HashMap<String, Object>(dataMap);
    }

    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTargetTranCode() {
        return this.targetTranCode;
    }

    public void setTargetTranCode(String targetTranCode) {
        this.targetTranCode = targetTranCode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status ? "1" : "0";
    }

}
