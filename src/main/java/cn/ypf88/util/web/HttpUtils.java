package cn.ypf88.util.web;

import cn.ypf88.exceptions.ExceptionErrorCode;
import cn.ypf88.exceptions.UtilsException;
import cn.ypf88.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfUtils
 * @package cn.ypf88.util.web
 * @date 2016/3/29 22:12
 * @describe HTTP相关工具类
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static final int TIME_OUT = 100000;

    public static void main(String[] args) throws UtilsException {
        String url = "http://www.baidu.com/s";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wd", "java");
        String resp = get(url, params, HttpUtils.Charset.UTF8);
        System.out.println(resp);
    }
    /**
     * POST方式发送HTTP请求
     *
     * @param url     HTTP请求地址
     * @param params  HTTP请求参数
     * @param charset HTTP请求响应字符集
     * @return 响应数据字符串
     * @throws UtilsException
     */
    public static String get(String url, Map<String, Object> params, HttpUtils.Charset charset) throws UtilsException {
        return http(url, null, params, null, Method.GET, charset);
    }

    /**
     * POST方式发送HTTP请求
     *
     * @param url     HTTP请求地址
     * @param data    HTTP报文体数据
     * @param charset HTTP请求响应字符集
     * @return 响应数据字符串
     * @throws UtilsException
     */
    public static String post(String url, String data, HttpUtils.Charset charset) throws UtilsException {
        return http(url, null, null, data, Method.POST, charset);
    }

    /**
     * HTTP请求响应处理类
     *
     * @param url     HTTP地址
     * @param header  HTTP头信息列表
     * @param params  HTTP请求GET参数
     * @param data    HTTP请求数据体参数
     * @param method  HTTP请求方式，取HttpUtils.Method枚举
     * @param charset HTTP请求字符集，取HttpUtils.Charset枚举
     * @return Http请求响应字符串
     * @throws UtilsException 异常信息
     */
    private static String http(String url, Map<String, String> header, Map<String, Object> params, String data, HttpUtils.Method method, HttpUtils.Charset charset) throws UtilsException {
        HttpURLConnection conn = buildHttpURLConnection(buildUrlWithQueryString(url, params, charset), method, header);
        OutputStream os = null;
        if (StringUtils.isNotEmpty(data)) {
            try {
                os = conn.getOutputStream();
                os.write(data.getBytes(charset.getValue()));
                os.flush();
            } catch (IOException e) {
                throw new UtilsException(e);
            }
        }
        String response = readResponseString(conn, charset);

        return response;
    }

    /**
     * 获取响应数据，并以字符串形式返回
     *
     * @param conn    Http链接对象
     * @param charset 响应数据字符串编码格式
     * @return 响应数据字符串数据
     * @throws UtilsException 响应数据获取异常
     */
    private static String readResponseString(HttpURLConnection conn, HttpUtils.Charset charset) throws UtilsException {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset.getValue()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UtilsException(ExceptionErrorCode.CONN_NOT_RESPONSE);
        }
    }

    /**
     * 对请求链接拼装参数并通过传入的字符集做URL加密
     *
     * @param url     请求链接
     * @param params  需要拼装的参数
     * @param charset 字符集，从HTTPUtils.Charset枚举中获取
     * @return
     */
    private static String buildUrlWithQueryString(String url, Map<String, Object> params, HttpUtils.Charset charset) {
        StringBuffer _url = new StringBuffer(url);
        StringBuffer param = new StringBuffer();
        if (params != null && !params.isEmpty()) {
            if (_url.indexOf("?") == -1) {
                _url.append("?");
            } else {
                _url.append("&");
            }
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                param = param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            param = param.deleteCharAt(param.length() - 1);
        }
        try {
            return _url + URLEncoder.encode(param.toString(), charset.getValue());
        } catch (UnsupportedEncodingException e) {
            log.debug("[{}]为不支持的编码格式，使用[{}]格式编码", charset.getValue(), Charset.UTF8.getValue());
            try {
                return _url + URLEncoder.encode(param.toString(), Charset.UTF8.getValue());
            } catch (UnsupportedEncodingException e1) {
                return null;
            }
        }
    }

    /**
     * 根据请求的地址路径创建HTTP链接对象。指定HTTP请求方式，及请求头信息
     *
     * @param url    请求的地址路径
     * @param method HTTP请求方式
     * @param header 请求头信息列表
     * @return HTTP链接对象
     * @throws IOException 链接流处理错误
     */
    private static HttpURLConnection buildHttpURLConnection(String url, HttpUtils.Method method, Map<String, String> header) throws UtilsException {
        URL _url = null;
        HttpURLConnection conn = null;
        try {
            _url = new URL(url);
            conn = (HttpURLConnection) _url.openConnection();
        } catch (MalformedURLException e) {
            throw new UtilsException(ExceptionErrorCode.URL_NOT_PARSE, url);
        } catch (IOException e) {
            throw new UtilsException(e);
        } catch (Exception e) {
            throw new UtilsException(e);
        }
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(initSSLSocketFactory());
            ((HttpsURLConnection) conn).setHostnameVerifier(new HttpUtils().new TrustAnyHostnameVerifier());
        }
        try {
            conn.setRequestMethod(method.getValue());
        } catch (ProtocolException e) {
        }
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(TIME_OUT);
        conn.setReadTimeout(TIME_OUT);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    /**
     * 初始化SSLSocket工厂
     *
     * @return
     */
    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] e = new TrustManager[]{new HttpUtils().new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, e, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    /**
     * 证书管理
     */
    private class TrustAnyTrustManager implements X509TrustManager {
        private TrustAnyTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    /**
     * 证书验证
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        private TrustAnyHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * HTTP请求方式
     */
    public static enum Method {
        /**
         * GET请求方式
         */
        GET("GET", "get方式"),
        /**
         * POST请求方式
         */
        POST("POST", "post方式");
        private String value;
        private String desc;

        Method(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return this.value;
        }

        public String getDesc() {
            return this.desc;
        }

        public boolean contains(String method) {
            Method[] fields = values();
            for (Method field : fields) {
                if (field.getValue().equals(method)) {
                    return true;
                }
            }
            return false;
        }

        public Method getByCode(String method) {
            Method[] fields = values();
            for (Method field : fields) {
                if (field.getValue().equals(method)) {
                    return field;
                }
            }
            return null;
        }

    }

    /**
     * 编码字符集
     */
    public static enum Charset {
        /**
         * GBK编码集
         */
        GBK("GBK"),
        /**
         * GB2312编码集
         */
        GB2312("GB2312"),
        /**
         * UTF-8编码集
         */
        UTF8("UTF-8");
        private String value;

        Charset(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public boolean contains(String code) {
            Charset[] fields = values();
            for (Charset field : fields) {
                if (field.getValue().equals(code)) {
                    return true;
                }
            }
            return false;
        }

        public Charset getByCode(String code) {
            Charset[] fields = values();
            for (Charset field : fields) {
                if (field.getValue().equals(code)) {
                    return field;
                }
            }
            return null;
        }

    }
}