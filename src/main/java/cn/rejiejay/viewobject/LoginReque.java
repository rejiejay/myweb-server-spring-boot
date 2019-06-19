package cn.rejiejay.viewobject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * login请求实体类
 * @author _rejeijay
 * @Date 2019年6月19日11:01:01
 */
public class LoginReque {
	@NotNull(message = "password cannot be null!")
	@Size(min = 2, max = 14)
    private String password;

    public String getPassword() {
        return password;
    } 

    public void setPassword(String password) {
        this.password = password;
    }
}
