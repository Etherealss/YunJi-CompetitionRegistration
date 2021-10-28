package pers.etherealss.service.impl;

import pers.etherealss.pojo.po.Group;
import pers.etherealss.mapper.GroupMapper;
import pers.etherealss.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

}
