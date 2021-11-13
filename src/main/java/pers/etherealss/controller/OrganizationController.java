package pers.etherealss.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.OrganizationService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @Secured("ROLE_official")
    @GetMapping("my")
    public Msg<List<Organization>> getMyOrganizations(HttpServletRequest req) {
        User user = TokenUtil.getUserByToken(req);
        List<Organization> myOrganizations = service.getMyOrganizations(user.getId());
        return Msg.ok(myOrganizations);
    }

    @Secured("ROLE_official")
    @PostMapping("add")
    public Msg<?> requestAddOrg(HttpServletRequest req, @RequestBody Integer orgId) {
        User user = TokenUtil.getUserByToken(req);
        return service.requestAddOrg(user.getId(), orgId);
    }

    @Secured("ROLE_official")
    @PostMapping("respAddOrg")
    public Msg<?> respAddOrg(HttpServletRequest req, @RequestBody Map<String, String> map) {
        Long notificationId = Long.valueOf(map.get("notificationId"));
        Boolean action = Boolean.valueOf(map.get("action"));
        log.debug("notificationId = {}, action = {}", notificationId, action);
        User user = TokenUtil.getUserByToken(req);
        return service.respAddOrg(user.getId(), notificationId, action);
    }

    @Secured("ROLE_official")
    @PostMapping("reviewComp")
    public Msg<?> reviewComp(HttpServletRequest req, @RequestBody Map<String, String> map) {
        User user = TokenUtil.getUserByToken(req);
        Long notiId = Long.parseLong(map.get("notificationId"));
        Boolean action = Boolean.parseBoolean(map.get("action"));
        return service.reviewComp(user.getId(), notiId, action);
    }
}

