package cn.rejiejay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对请求标记了 SecurityAnnotater 注解的方法进行拦截
 * 
 * @author _rejiejay Created on 2019年7月3日11:15:33
 */
@Component
public class SecurityAnnotaterInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityAnnotaterInterceptor.class);

	/**
	 * 通过重写在请求到达Controller之前进行拦截并处理
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.warn("---------------------------------------------0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter out = response.getWriter();//获取PrintWriter输出流
//		boolean bl = handler instanceof HandlerMethod;
//		out.write("中国");//使用PrintWriter流向客户端输出字符
//		out.write(String.valueOf(bl));//使用PrintWriter流向客户端输出字符
//		return false;
		
		// 如果句柄是属于拦截器已经注册的的方法
		if (handler instanceof HandlerMethod) {
			// 注解在方法上
			SecurityAnnotater securityAnnotation = ((HandlerMethod) handler)
					.getMethodAnnotation(SecurityAnnotater.class);
			// 注解在类上
			SecurityAnnotater classSecurityAnnotation = ((HandlerMethod) handler).getMethod().getDeclaringClass()
					.getAnnotation(SecurityAnnotater.class);

			// 任意一个存在都进行过滤
			if (securityAnnotation != null && classSecurityAnnotation != null) {
				
				// 表示存在注解，此时因为还会在请求中用到当前用户信息，所以可以存到某个地方
                // 将当前用户的信息存入threadLocal中
				// threadLocalUtils.set(loginUser);
			}
		}

		return true;
	}

//	  重写请求结束后的方法，此处的目的是
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        //释放threadLocal资源
//        threadLocalUtils.remove();
//    }
}
