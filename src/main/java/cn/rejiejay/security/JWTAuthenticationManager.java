package cn.rejiejay.security;

import cn.rejiejay.service.UserServerImpl;
import cn.rejiejay.utils.JWTUtil;

//import org.inlighting.spa.datasource.UserEntity;
//import org.inlighting.spa.datasource.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * AuthenticationManager - 处理来自框架其他部分的认证请求。
 * 这个类会注入到 WebSecurityConfig.java
 * 
 * 大概的主要目的是 鉴定失败的时候 抛出异常
 *
 * @author _rejeijay
 * @Date 2019年6月30日14:12:54
 */
@Component
public class JWTAuthenticationManager implements AuthenticationManager {

    // 这个是Service层注入, 应该是主要用来获取用户数据
    @Autowired
    private UserServerImpl userServerImpl;

    /**
     * 进行token鉴定，抛出错误
     * 通过重写父类的方法 改造 判断用户 token 是否合法
     * 
     * @param authentication 待鉴定的JWTAuthenticationToken
     * @return 鉴定完成的JWTAuthenticationToken，供Controller使用
     * @throws AuthenticationException 如果鉴定失败，抛出
     */
   @Override // 重写父类的方法
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = authentication.getCredentials().toString(); // 不知道哪里能获取到这个， 反正就是能够获取 大概率是请求进来的 headers: {"Authorization": token};
        String username = JWTUtil.getUsername(token); // 通过token获取username

        JSONObject userEntity = userServerImpl.getUser(username);
        
        if (userEntity.getInteger("result") != 1) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        
        /*
         * 官方推荐在本方法中必须要处理三种异常，
         * DisabledException、LockedException、BadCredentialsException
         * 这里为了方便就只处理了BadCredentialsException，大家可以根据自己业务的需要进行定制
         * 详情看AuthenticationManager的JavaDoc
         */
        boolean isAuthenticatedSuccess = JWTUtil.verify(token, username, userEntity.getJSONObject("data").getString("password"));
        if (!isAuthenticatedSuccess) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        
        JWTAuthenticationToken authenticatedAuth = new JWTAuthenticationToken(
            token, 
            userEntity, 
            AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getJSONObject("data").getString("role"))
        );
        
        return authenticatedAuth;
   }
}
