package cn.rejiejay.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @编写人： yh.zeng @编写时间：2018-9-13 下午11:31:36
 * 
 * @文件描述: 封装SecurityRiskRequestWrapper请求对象 。解决HttpServletRequest的输入流只能读取一次的问题
 */
@Component
@WebFilter(urlPatterns = "/*")
public class SecurityRiskRequestWrapperFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		MyHttpServletRequest requestWrapper = new MyHttpServletRequest((HttpServletRequest) request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void destroy() {
	}

}
