package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.po.Registration;

/**
 * @author etherealss
 * @since 2021-10-03
 */
public interface RegistrationService extends IService<Registration> {
    void registerCompetition(Integer teamId, Integer competitionId);
}
