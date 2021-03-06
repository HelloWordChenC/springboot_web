package com.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.util.Enumeration;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Enumeration enu=request.getParameterNames();
        JSONObject obj = new JSONObject();
        String servletURI =request.getServletPath();//项目名称后缀
        String ip =LoginInterceptor.realIP(request);
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            obj.put(paraName,request.getParameter(paraName));
        }
        log.info("请求Ip-----------");
        log.info(ip);
        log.info("请求Api----------");
        log.info(servletURI);
        log.info("请求参数----------");
        log.info(obj.toString());
        //业务代码
        return true;
    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }
    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }


    public static String realIP(HttpServletRequest request) {
        String xff = request.getHeader("x-forwarded-for");
        if (xff != null) {
            int index = xff.indexOf(',');
            if (index != -1) {
                xff = xff.substring(0, index);
            }
            return xff.trim();
        }
        return request.getRemoteAddr();
    }
}
