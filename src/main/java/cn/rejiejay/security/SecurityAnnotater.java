package cn.rejiejay.security;

import java.lang.annotation.*;

/**
 * 自定义注解标识拦截
 * 
 * @author _rejiejay Created on 2019年7月3日11:15:33
 */
@Target(value = { // 注解的作用目标
		ElementType.METHOD, // 方法
		ElementType.TYPE // 接口、类、枚举、注解
})
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented // 说明该注解将被包含在 _javadoc 中
public @interface SecurityAnnotater {
	/**
	 * 权限 默认空权限 即未登录
     * 现阶段只有 _admin 和 anonymous 也就是游客
	 */
	String role() default "";
}
