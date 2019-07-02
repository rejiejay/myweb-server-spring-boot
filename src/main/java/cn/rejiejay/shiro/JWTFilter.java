package cn.rejiejay.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA
 * 所有的请求都会先经过Filter，所以我们继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法。
 * 代码的执行流程 preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
 * @Date 2018-04-08
 * @Time 12:36
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 通过重写preHandle，实现跨越访问。对跨域提供支持
	 * preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	/**
	 * 如果带有 token，则对 token 进行检查，否则直接通过
	 * isAccessAllowed -> isLoginAttempt -> executeLogin
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller 中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
		// 判断请求的请求头是否带上 "Token"
		if (isLoginAttempt(request, response)) {
			// 如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
			try {
				executeLogin(request, response);
				return true;
			} catch (Exception e) {
				// 过滤器检查到报错，直接抛出报错信息 token 错误 请求不进去
				responseError(response, e.getMessage());
			}
		}
		// 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
		return true;
	}

	/**
	 * 判断用户是否想要登入。 检测 header 里面是否包含 Token 字段
	 * 检测header里面是否包含Authorization字段即可
	 * isLoginAttempt -> executeLogin
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String token = req.getHeader("Token"); // 这里可以进行修改的
		return token != null;
	}

	/**
	 * 执行登陆操作
	 * executeLogin
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader("Token");
		JWTToken jwtToken = new JWTToken(token);
		/**
		 * getSubject(request, response).login(token); 这一步就是提交给了 realm 进行处理
		 * 提交给 realm 进行登入，如果错误他会抛出异常并被捕获
		 * Subject 是 Shiro 最重要的一个概念。
		 * “Subject”只是一个安全术语，意味着应用程序用户的特定于安全性的“视图”。Shiro Subject实例代表单个应用程序用户的安全状态和相关操作。
		 */
		getSubject(request, response).login(jwtToken);
		return true; // 如果没有抛出异常则代表登入成功，返回true
	}

	/**
	 * 将非法请求跳转到 /unauthorized/**
	 */
	private void responseError(ServletResponse response, String message) {
		try {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			// 设置编码，否则中文字符在重定向时会变为空字符串
			message = URLEncoder.encode(message, "UTF-8");
			httpServletResponse.sendRedirect("/unauthorized/" + message);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
