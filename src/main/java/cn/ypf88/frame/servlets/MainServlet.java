/**
 * 项目入口servlet。
 * 主要功能：
 * 1.请求信息预处理，业务逻辑转发。
 * 2.请求并发数量控制。
 *
 * @author 于鹏飞 1.0
 */
package cn.ypf88.frame.servlets;

import cn.ypf88.frame.actions.Action;
import cn.ypf88.frame.beans.BeanFactory;
import cn.ypf88.frame.contexts.SubstratumContext;
import cn.ypf88.frame.enums.error.SystemError;
import cn.ypf88.frame.exceptions.FailException;
import cn.ypf88.frame.utils.ExceptionUtils;
import cn.ypf88.util.PropertiesUtils;
import cn.ypf88.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.servlets
 * @date 2016/7/29 23:17
 * @describe 系统入口文件
 */
public class MainServlet extends javax.servlet.http.HttpServlet {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MainServlet.class);
    private String transName = "tranCode";
    private String transCode;
    private String respType;

    private Map<String, Object> resMap = new LinkedHashMap<String, Object>();
    private Map<String, Object> header = new HashMap<String, Object>();
    private Map<String, Object> body = new HashMap<String, Object>();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        server(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        server(req, resp);
    }

    /**
     * Http数据请求分发，请求参数
     *
     * @param req  请求对象
     * @param resp 响应对象
     * @throws java.io.IOException
     */
    private void server(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        resMap.put("header", header);
        resMap.put("body", body);
        this.transName = PropertiesUtils.getInfo("classpath:config.properties", "transName");
        if (StringUtils.isEmpty(this.transName)) {
            this.transName = "tranCode";
        }

        String uri = req.getRequestURI();
        Map<String, String[]> params = req.getParameterMap();
        if (!StringUtils.isEmpty(uri)) {
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            if (uri.indexOf(".") > -1) {
                String transCode = uri.split("\\.")[0];
                int index = transCode.lastIndexOf("/");
                this.transCode = transCode.substring(index + 1);
                this.respType = uri.split("\\.")[1];
            }
        }
        Map<String, Object> param = new HashMap<String, Object>(params.size());

//        reqestInfoPrint(uri, params, param);

        SubstratumContext context = null;
        try {
            // 数据分发
            Action action = BeanFactory.getAction(transCode);
            if (action == null) {
                ExceptionUtils.throwFail(SystemError.NO_FOUND_ACTIONS, transCode);
            }
            context = action.serviceMethod(transCode, param);
            header.put("status", "1");
            Map<String, Object> res = context.getDataMap();
            body.putAll(res);
        } catch (FailException e) {
            header.put("status", "0");
            header.put("errorCode", e.getCode());
            header.put("message", e.getLocalizedMessage());
        } finally {
            header.put("sessionId", req.getSession(true).getId());
            header.put(this.transName, this.transCode);
        }
        resp.setCharacterEncoding("UTF-8");
        String res = null;
        if ("json".equalsIgnoreCase(respType)) {
            resp.addHeader("Content-type", "text/json;charset=UTF-8");
            res = json(resMap);
        } else if ("xml".equalsIgnoreCase(respType)) {
            resp.addHeader("Content-type", "text/xml;charset=UTF-8");
            res = "<res>" + xml(resMap) + "</res>";
        }
//        resp.addHeader("Content-length",String.valueOf(res.length()+14));
        ServletOutputStream os = resp.getOutputStream();
        logger.info("\n:::returning:::{}", res);
        os.write(res.getBytes("UTF-8"));
        os.flush();
        os.close();
    }

    private void reqestInfoPrint(String uri, Map<String, String[]> params, Map<String, Object> param) {
        StringBuilder par = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                par.append(entry.getKey() + "=>" + java.util.Arrays.deepToString(entry.getValue()) + ",");
                param.put(entry.getKey(), entry.getValue()[0]);
            }
            par.deleteCharAt(par.length() - 1);
        }
        logger.info("uri:[{}], transCode:[{}], respType:[{}]", uri, this.transCode, this.respType);
        logger.info("params:[{}], param:[{}]", par, param);
        body.put("uri", uri);
        body.put("transCode", transCode);
        body.put("respType", respType);
        body.put("params", params);
        body.put("param", param);
    }

    private String xml(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj != null) {
            if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    Object o = entry.getValue();
                    sb.append("<" + entry.getKey() + ">" + xml(o) + "</" + entry.getKey() + ">");
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                for (Object o : list) {
                    sb.append("<" + o.getClass().getSimpleName().toLowerCase() + ">" + xml(o) + "</" + o.getClass().getSimpleName().toLowerCase() + ">");
                }
            } else if (obj.getClass().isArray()) {
                Object[] arrays = (Object[]) obj;
                for (Object o : arrays) {
                    sb.append(xml(o));
                }
            } else {
                sb.append(obj);
            }
        }
        return sb.toString();
    }

    private String json(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj != null) {
            int count = 0, length;
            if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                length = map.size();
                sb.append("{");
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    Object o = entry.getValue();
                    sb.append("\"" + entry.getKey() + "\":" + json(o));
                    if (count++ < length - 1) {
                        sb.append(",");
                    }
                }
                sb.append("}");
            } else if (obj instanceof List) {
                List list = (List) obj;
                length = list.size();
                sb.append("[");
                for (Object o : list) {
                    sb.append(json(o));
                    if (count++ < length - 1) {
                        sb.append(",");
                    }
                }
                sb.append("]");
            } else if (obj.getClass().isArray()) {
                Object[] arrays = (Object[]) obj;
                for (Object o : arrays) {
                    sb.append(json(o));
                }
            } else if (obj instanceof String) {
                sb.append("\"" + obj.toString() + "\"");
            } else {
                sb.append(obj.toString());
            }
        } else {
            sb.append("");
        }
        return sb.toString();
    }

}
