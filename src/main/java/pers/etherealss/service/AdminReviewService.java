package pers.etherealss.service;

import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;

/**
 * @author wtk
 * @date 2021-11-09
 */
public interface AdminReviewService {

    Msg<?> reviewCompetition(User admin, Competition comp, Boolean action);
}
