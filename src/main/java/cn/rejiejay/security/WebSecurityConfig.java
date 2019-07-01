package cn.rejiejay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring-security java配置
 * 主要配置项有：
 * - 对每个URL进行验证
 * - 生成一个登陆表单
 * - 许使用用户名 Username user 和密码 Password password 使用验证表单进行验证。
 * - 允许用户登出
 * - CSRF attack CSPF攻击防范
 * - Session Fixation Session保护
 * - 安全 Header 集成
 * - 以及 Servlet API 方法集成
 *
 * @author _rejeijay
 * @Date 2019年6月30日14:12:54
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationManager jwtAuthenticationManager;

    /**
     * 这里主要是通过重写父类，配置和关闭一些东西
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // restful具有先天的防范csrf攻击，所以关闭这功能
        http.csrf().disable()
            // 默认允许所有的请求通过，后序我们通过方法注解的方式来粒度化控制权限
            .authorizeRequests().anyRequest().permitAll()
            .and()
            // 添加过滤器 （添加属于我们自己的过滤器，注意因为我们没有开启formLogin()，所以UsernamePasswordAuthenticationFilter根本不会被调用
            // addFilterAt(javax.servlet.Filter filter, java.lang.Class<? extends javax.servlet.Filter> atFilter)
            .addFilterAt(new JWTAuthenticationFilter(jwtAuthenticationManager), UsernamePasswordAuthenticationFilter.class)
            // 前后端分离本身就是无状态的，所以我们不需要cookie和session这类东西。所有的信息都保存在一个token之中。
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
