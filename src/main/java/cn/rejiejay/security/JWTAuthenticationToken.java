package cn.rejiejay.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 应该是 规范 要返回的东西 的一个方法 （重写 AuthenticationManager 父类的方法 需要返回的值 Authentication 
 * 继承了 AbstractAuthenticationToken 的抽象类 
 * 鉴定成功后调用的方法，返回的JWTAuthenticationToken供Controller里面调用。
 * 有两个地方用到 JWTAuthenticationFilter 和 JWTAuthenticationManager
 */
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private final Object credentials;

    /**
     * 鉴定token前使用的方法，因为还没有鉴定token是否合法，所以要setAuthenticated(false)
     * @param token JWT密钥
     */
    public JWTAuthenticationToken(String token) {
        super(null);
        this.principal = null;
        this.credentials = token;
        setAuthenticated(false);
    }

    /**
     * 鉴定成功后调用的方法，返回的JWTAuthenticationToken供Controller里面调用。
     * 因为已经鉴定成功，所以要setAuthenticated(true)
     * @param token JWT密钥
     * @param userInfo 一些用户的信息，比如username, id等
     * @param authorities 所拥有的权限
     */
    public JWTAuthenticationToken(String token, Object userInfo, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userInfo;
        this.credentials = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
