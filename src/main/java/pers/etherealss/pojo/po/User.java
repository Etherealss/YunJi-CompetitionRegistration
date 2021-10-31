package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @description 用户
 * @date 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements UserDetails, Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(exist = false)
    private UserInfo userInfo;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private Date registerTime;

    private String avatar;

    /**
     * 用户身份：student等
     */
    private String userRole;
    /**
     * 权限
     */
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    @TableField(exist = false)
    private String token;

    /**
     * 账号是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否激活
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Student getStudentInfo() {
        if (userInfo instanceof Student) {
            return (Student) userInfo;
        }
        return null;
    }

    public Official getOfficialInfo() {
        if (userInfo instanceof Official) {
            return (Official) userInfo;
        }
        return null;
    }
}
