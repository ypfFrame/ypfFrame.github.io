package cn.ypf88.frame.listeners;

import cn.ypf88.frame.beans.BeanFactory;

import javax.servlet.ServletContextEvent;

/**
 * @author ypf
 * @version V1.0.0
 * @project ypfFrame
 * @package cn.ypf88.frame.listeners
 * @date 2016/8/5 3:00
 * @describe JavaWeb服务启动和停止时执行的监听
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    /**
     * 服务启动时调用的方法
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanFactory.init();
    }

    /**
     * 服务停止时调用的方法
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
