package pers.etherealss.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wtk
 * @since 2021-10-03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录
     * @param userId
     * @param password
     * @return
     */
    User selectLoginUser(@Param("userId") int userId, @Param("password") String password);

    /**
     *
     * @param username
     * @return
     */
    User selectUserByUsername(@Param("username") String username);

    /**
     * 查询用户权限
     * @param username
     * @return
     */
    List<String> selectUserPermissions(@Param("username") String username);

}
