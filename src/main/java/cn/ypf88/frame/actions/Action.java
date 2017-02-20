package cn.ypf88.frame.actions;

import cn.ypf88.frame.contexts.Context;
import cn.ypf88.frame.contexts.SubstratumContext;
import cn.ypf88.frame.exceptions.FailException;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.servlets
 * @date 2016/5/16 0:01
 * @describe 业务入口接口
 */
public abstract class Action {
    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    // 上下文数据
    private SubstratumContext context = new SubstratumContext();

    /**
     * 业务逻辑处理方法
     *
     * @param context 上下文总线
     * @throws FailException 交易失败异常信息
     */
    protected abstract void execute(Context context) throws FailException;

    /**
     * 前置方法。从源请求对象中将数据封装到上下文Context对象中
     *
     * @param tranCode 请求uri地址
     * @param params   请求参数
     */
    private void beforeMethod(String tranCode, Map<String, Object> params) {
        this.context = new SubstratumContext();
        this.context.setTranCode(tranCode);
        this.context.setRowDataMap(params);
        this.context.setDataMap(params);
    }

    /**
     * 后处理方法
     */
    private void afterMethod(SubstratumContext context) {

    }

    /**
     *
     */
    public SubstratumContext serviceMethod(String tranCode, Map<String, Object> params) throws FailException {
        beforeMethod(tranCode, params);
        execute(context);
        afterMethod(context);
        return (SubstratumContext) context;
    }

}
