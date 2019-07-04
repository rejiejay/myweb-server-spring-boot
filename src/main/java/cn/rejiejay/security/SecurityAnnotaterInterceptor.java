package cn.rejiejay.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.rejiejay.utils.Consequencer;

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
	Consequencer consequencer = new Consequencer();

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 如果句柄是属于拦截器已经注册的的方法
		if (handler instanceof HandlerMethod) {
			// 注解在方法上
			SecurityAnnotater securityAnnotation = ((HandlerMethod) handler).getMethodAnnotation(SecurityAnnotater.class);
			/**
			 * 判断接口是否需要权限
			 * "" 或者 anonymous 游客
			 */
			if (securityAnnotation != null) { // 不为空表示有拦截器
				String role = securityAnnotation.role();
				/**
				 * 有拦截器的情况下，判断是不是空 或者 游客
				 */
				if (role != "" && role != "anonymous") {
					// 不是空或游客 表示这里是需要权限的
					String token = request.getHeader("x-rejiejay-authorization");
					if (!StringUtils.isEmpty(token)) {
						
						
					} else {
						// 在需要权限的情况下, 凭证为空。说明未授权
						response.setHeader("Content-type", "application/json;charset=UTF-8");
						response.setContentType("application/json;charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						PrintWriter out = response.getWriter();
						out.print(consequencer.setResult(40002).setMessage("不合法的凭证类型").getJsonStringMessage());
						out.flush();
						return false; // 直接返回失败
					}
					
				} else {
					// 表示是不需要权限的
					return true; // 可以直接请求
				}
			}
			
			/**
			 * 注解在类上
			 * 一般我不会写在类上面
			 */
			// SecurityAnnotater classSecurityAnnotation = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(SecurityAnnotater.class);
			// if (classSecurityAnnotation != null) { }
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
