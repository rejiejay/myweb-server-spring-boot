package cn.rejiejay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rejiejay.service.UserServer;
import cn.rejiejay.utils.Consequencer;
import cn.rejiejay.utils.DigitalSignature;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 对请求标记了 SecurityAnnotater 注解的方法进行拦截
 * 
 * @author _rejiejay Created on 2019年7月3日11:15:33
 */
@Component
public class SecurityAnnotaterInterceptor extends HandlerInterceptorAdapter {
//	private static final Logger logger = LoggerFactory.getLogger(SecurityAnnotaterInterceptor.class);
	Consequencer consequencer = new Consequencer();

	@Autowired
	private UserServer userServer;
	
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
		String reqParamString = requestToDigitalSignature(request); // 用来生成密匙的请求参数
		
		// 允许携带的请求头
		response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-rejiejay-authorization");

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
					String digitalSignatureEncodedString = request.getHeader("x-rejiejay-authorization");
					if (!StringUtils.isEmpty(digitalSignatureEncodedString)) { // 这个是非空的情况下
						// 解析 数字签名
						String digitalSignatureStr = "";
						try {
							digitalSignatureStr = DigitalSignature.DecodeSignature(reqParamString, digitalSignatureEncodedString);
						} catch (Exception e) {
							return setErrorResponse(response, 40003, "不合法的凭证类型" + e.toString()); // 直接返回失败
						}
						
						// 判断解析的JSON是否合法
						if (!JSON.isValid(digitalSignatureStr)) { // 合法返回 _ture 不合法返回false
							return setErrorResponse(response, 40002, "不合法的凭证类型");
						}
						
						// 合法的情况下 获取解析后的数字签名里面携带的 JSON 数据
						JSONObject digitalSignature = JSON.parseObject(digitalSignatureStr);
						
						// 根据通过数字签名携带的信息（用户名 拦截器允许的角色 前端携带的凭证） 验证是否符合权限
						JSONObject securityVerifiResult = userServer.securityVerifiByDigitalSign(
							digitalSignature.getString("username"), // 需要授权的用户名
							role, // 拦截器允许的角色
							digitalSignature.getString("token") // 前端携带的凭证
						);
						
						// 不符合权限情况下，直接返回错误结果
						if (securityVerifiResult.getInteger("result") != 1) {
							return setErrorResponse(response, securityVerifiResult.getInteger("result"), securityVerifiResult.getString("message"));
						}

						// 符合的情况下，恭喜您权限校验通过
						return true; 
					} else {
						// 在需要权限的情况下, 凭证为空。说明未授权
						return setErrorResponse(response, 40002, "不合法的凭证类型"); // 直接返回失败
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
//        // 释放threadLocal资源
//        threadLocalUtils.remove();
//    }

	/**
	 * 处理 HttpServletRequest 因为生成或者解析数字签名需要用到这些东西
	 */
	public String requestToDigitalSignature(HttpServletRequest request) throws Exception {
		String requestMethod = request.getMethod();

		if (requestMethod.equals("GET")) {
		System.out.println("request.getQueryString():" + request.getQueryString() + "\n");
			return request.getQueryString();
		}

		if (requestMethod.equals("POST")) {
			BufferedReader br = request.getReader();

			String str, wholeStr = "";
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}

			System.out.println(wholeStr);
			return wholeStr;
		}

		return null;
	}
	
	/**
	 * 返回固定的失败格式
	 * @param response
	 * @param code 错误代码
	 * @param message 错误信息
	 * @return boolean
	 * @throws Exception
	 */
	public boolean setErrorResponse(HttpServletResponse response, int code, String message) throws Exception {
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(consequencer.setResult(code).setMessage(message).getJsonStringMessage());
		out.flush();
		return false;
	}
}
