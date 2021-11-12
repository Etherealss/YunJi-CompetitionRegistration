package pers.etherealss.common.enums;

import lombok.Getter;
import pers.etherealss.common.exception.ServiceException;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.pojo.po.User;

/**
 * @author wtk
 * @date 2021-11-01
 */
@Getter
public enum NotificationElementType {

    /** */
    USER("user", User.class),
    TEAM("team", Team.class),
    COMPETITION("competition", Competition.class),
    ORGANIZATION("organization", Organization.class),
    ;

    /**
     * 原始类型
     */
    private Class<?> targetClass;
    /**
     * 键
     */
    private String key;

    NotificationElementType(String key, Class<?> targetClass) {
        this.targetClass = targetClass;
        this.key = key;
    }

    /**
     * 获取一个类型的key
     * @param object
     * @return 不存在时返回空
     * @throws ServiceException 没有对应的数据类型的key
     */
    public String getKey(Object object) {
        for (NotificationElementType item : NotificationElementType.values()){
            // 判断是否为该类型
            if (item.getTargetClass().isInstance(object)) {
                return item.getKey();
            }
        }
       throw new UnsupportedOperationException("没有对应的数据类型的key");
    }
}
