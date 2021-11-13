package pers.etherealss.common.enums;

import lombok.Getter;

/**
 * @author wtk
 * @date 2021-11-01
 */
@Getter
public enum NotifyType {
    /***/
    REQUEST_ADD_TEAM(
            "RequestAddTeam",
            "组队请求",
            NotifyPosition.TEAM
    ),
    RESPONSE_ADD_TEAM(
            "ResponseAddTeam",
            "组队结果",
            NotifyPosition.TEAM
    ),
    REQUEST_ADD_ORG(
            "RequestAddOrg",
            "申请加入组织",
            NotifyPosition.ORGANIZATION
    ),
    RESPONSE_ADD_ORG(
            "ResponseAddOrg",
            "申请结果",
            NotifyPosition.ORGANIZATION
    ),
    REGISTER_COMPETITION(
            "RegisterCompetition",
            "已报名比赛",
            NotifyPosition.COMPETITION
    ),
    NEW_REGISTER_COMPETITION(
            "NewRegisterCompetition",
            "新队伍报名比赛",
            NotifyPosition.COMPETITION
    ),
    REVIEW_COMPETITION(
            "ReviewCompetition",
            "赛事审核",
            NotifyPosition.COMPETITION
    ),
    MANAGER_REVIEW_COMPETITION(
            "ManagerReviewCompetition",
            "负责人赛事审核",
            NotifyPosition.COMPETITION
    ),
    RESPONSE_MANAGER_REVIEW_COMPETITION(
            "ResponseReviewCompetition",
            "负责人审核结果",
            NotifyPosition.COMPETITION
    ),
    ;
    /** 通知类型 */
    private String type;
    /** 通知标题 */
    private String title;
    /** 通知显示位置 */
    private String position;

    NotifyType(String type, String title, String position) {
        this.type = type;
        this.title = title;
        this.position = position;
    }
}
