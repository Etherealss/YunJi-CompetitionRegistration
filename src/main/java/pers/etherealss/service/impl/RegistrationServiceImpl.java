package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.exception.SimpleException;
import pers.etherealss.mapper.RegistrationMapper;
import pers.etherealss.pojo.po.Registration;
import pers.etherealss.service.RegistrationService;

/**
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public void registerCompetition(Integer teamId, Integer competitionId) {
        Registration registration = registrationMapper.selectOne(
                new QueryWrapper<Registration>()
                        .eq("team_id", teamId)
                        .eq("competition_id", competitionId)
        );
        if (registration != null) {
            throw new SimpleException(ApiInfo.TEAM_HAS_REGISTER);
        }
        registration = new Registration(teamId, competitionId);
        registrationMapper.insert(registration);
    }
}
