package cn.rejiejay.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个就是拦截的过滤器
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    /**
     * 使用我们自己开发的JWTAuthenticationManager
     * @param authenticationManager 我们自己开发的JWTAuthenticationManager
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        // 传入了 AuthenticationManager
        super(authenticationManager); // super指向自己超（父）类对象的一个指针 调用父类中的某一个构造函数
    }

    /**
    * 这个里是使用到了 Filter 拦截客户的HttpServletRequest 
    * 据需要检查HttpServletRequest ，也可以修改HttpServletRequest 头和数据。 
    * HttpServletResponse 到达客户端之前，拦截HttpServletResponse 。 
    * JWTAuthenticationManager 是鉴定 token 并且 抛出错误
    * 这里作用是过滤掉HTTP请求
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        /**
         * 这里要划分几个接口不进行过滤，
         * 不过好像是使用了注解的，所以不需要
         */

        /**
         * startsWith() 方法用于检测字符串是否以指定的前缀开始。
         * 前端应该格式都是由 {"Authorization": "Bearer " + token};
         * 这个好像是 OAuth 2.0 (RFC 6749)  的规范 Bearer Token（ Json Web Token简称JWT
         */
        if (header == null || !header.toLowerCase().startsWith("bearer ")) {
            chain.doFilter(request, response); // chain.doFilter将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
            return;
        }

        try {
            String token = header.split(" ")[1];
            JWTAuthenticationToken JWToken = new JWTAuthenticationToken(token);
            // 鉴定权限，如果鉴定失败，AuthenticationManager会抛出异常被我们捕获
            Authentication authResult = getAuthenticationManager().authenticate(JWToken); // getAuthenticationManager 应该是继承 BasicAuthenticationFilter 里面的方法
            // 将鉴定成功后的Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            // 返回鉴权失败
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }
}
