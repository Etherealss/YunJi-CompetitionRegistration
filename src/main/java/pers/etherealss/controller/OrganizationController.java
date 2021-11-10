package pers.etherealss.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.OrganizationService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wtk
 * @since 2021-10-03
 */
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
}

