package cn.rejiejay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.rejiejay.security.handler.MyAccessDeniedHandler;
import cn.rejiejay.security.handler.MyAuthenticationEntryPointHandler;
import cn.rejiejay.security.handler.MyAuthenticationFailHandler;
import cn.rejiejay.security.handler.MyAuthenticationSuccessHandler;
import cn.rejiejay.security.handler.MyLogoutSuccessHandler;
import cn.rejiejay.service.UserDetailsServiceImpl;
import cn.rejiejay.utils.JwtTokenUtil;

/**
 * spring-security java配置 主要配置项有： - 对每个URL进行验证 - 生成一个登陆表单 - 许使用用户名 Username user
 * 和密码 Password password 使用验证表单进行验证。 - 允许用户登出 - CSRF attack CSPF攻击防范 - Session
 * Fixation Session保护 - 安全 Header 集成 - 以及 Servlet API 方法集成
 *
 * @author _rejeijay
 * @Date 2019年6月30日14:12:54
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 开启方法级的权限注解,性设置后控制器层的方法前的@PreAuthorize("hasRole('admin')")
																			// 注解才能起效
@Configuration // 启动web安全性
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl myUserDetailService;

	@Autowired
	private MyAuthenticationSuccessHandler myLoginSuccessHandler;

	@Autowired
	private MyAuthenticationFailHandler myLoginFailHandler;

	@Autowired
	private MyAuthenticationEntryPointHandler myAuthEntryPointHandler;

	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;

	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;

	@Autowired
	private JWTAuthenticationManager jwtAuthenticationManager;

	/**
	 * 置user-detail服务
	 *
	 * 方法描述 accountExpired(boolean) 定义账号是否已经过期 accountLocked(boolean) 定义账号是否已经锁定
	 * and() 用来连接配置 authorities(GrantedAuthority...) 授予某个用户一项或多项权限 authorities(List)
	 * 授予某个用户一项或多项权限 authorities(String...) 授予某个用户一项或多项权限 disabled(boolean)
	 * 定义账号是否已被禁用 withUser(String) 定义用户的用户名 password(String) 定义用户的密码
	 * roles(String...) 授予某个用户一项或多项角色
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// super.configure(auth);
		// 配置指定用户权限信息 通常生产环境都是从数据库中读取用户权限信息而不是在这里配置
		// auth.inMemoryAuthentication().withUser("username1").password("123456").roles("USER").and().withUser("username2").password("123456").roles("USER","AMDIN");

		// 基于数据库中的用户权限信息进行认证
		// 指定密码加密所使用的加密器为bCryptPasswordEncoder()
		// 需要将密码加密后写入数据库
		// myUserDetailService
		// 类中获取了用户的用户名、密码以及是否启用的信息，查询用户所授予的权限，用来进行鉴权，查询用户作为群组成员所授予的权限
		auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
		// 不删除凭据，以便记住用户
		// auth.eraseCredentials(false);
	}

	/**
	 * 这里主要是通过重写父类，配置和关闭一些东西
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// restful具有先天的防范csrf攻击，所以关闭这功能
		http.csrf().disable()
				// 默认允许所有的请求通过，后序我们通过方法注解的方式来粒度化控制权限
				.authorizeRequests().anyRequest().permitAll().and()
				// 添加过滤器
				// （添加属于我们自己的过滤器，注意因为我们没有开启formLogin()，所以UsernamePasswordAuthenticationFilter根本不会被调用
				// addFilterAt(javax.servlet.Filter filter, java.lang.Class<? extends
				// javax.servlet.Filter> atFilter)
				.addFilterAt(new JWTAuthenticationFilter(jwtAuthenticationManager),
						UsernamePasswordAuthenticationFilter.class)
				// 前后端分离本身就是无状态的，所以我们不需要cookie和session这类东西。所有的信息都保存在一个token之中。
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
