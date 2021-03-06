package com.yoflying.drivingschool.coachStudents.controller;

import com.yoflying.drivingschool.coachStudents.BaseCsController;
import com.yoflying.drivingschool.constdef.ErrorDef;
import com.yoflying.drivingschool.domain.model.CoachStudentUser;
import com.yoflying.drivingschool.entity.DSInfoEntity;
import com.yoflying.drivingschool.infrastructure.realm.RoleSign;
import com.yoflying.drivingschool.infrastructure.token.ManageToken;
import com.yoflying.drivingschool.management.facade.ManageService;
import com.yoflying.drivingschool.utils.json.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liqiang on 16/12/13.
 */
@Controller
@RequestMapping("/coachstudent")
public class CoachStudentUserController extends BaseCsController{
    private final Logger logger = LoggerFactory.getLogger(CoachStudentUserController.class);

    @Autowired
    ManageService manageServiceFacade;


    @RequestMapping(value = "/login")
    public String login() {

        return "/coachSt/login.ftl";
    }

    /**
     * 获取当前学员or教练信息
     * @return
     */
    @RequestMapping(value = "/coachstudentInfo")
    @RequiresRoles(value = {RoleSign.STUDENT, RoleSign.COACH }, logical = Logical.OR )
    @ResponseBody
    public JsonResult coachstudentInfo() {

        return new JsonResult<CoachStudentUser>(ErrorDef.SUCCESS, "获取用户信息成功", getCoachStudentUser());
    }

    //  username  password  host Ip地址
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Integer> loginPost (@RequestBody ManageToken token) {
        logger.info("coachstudent" + token.getUsername() + "---------" + token.getHost());

        if (StringUtils.isEmpty(token.getUsername()) || StringUtils.isEmpty(token.getUsername())) {
            return new JsonResult<Integer>("用户密码不能为空", ErrorDef.USER_PASS_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            CoachStudentUser coachStudentUser = getCoachStudentUser();
            if (coachStudentUser.getDiscern() == CoachStudentUser.COACH ) {
                return new JsonResult<Integer>(ErrorDef.SUCCESS, "欢迎教练登录", CoachStudentUser.COACH);
            }
            if (coachStudentUser.getDiscern() == CoachStudentUser.STUDENT ) {
                return new JsonResult<Integer>(ErrorDef.SUCCESS, "欢迎学员登录", CoachStudentUser.STUDENT);
            }

        }catch (AuthenticationException e) {
            token.clear();
            return new JsonResult<Integer>("用户密码错误", ErrorDef.USER_PASS_ERROR);
        }
        return new JsonResult<Integer>("非法用户", ErrorDef.USER_PASS_ERROR);
    }

    /**
     * 学员教练获取驾校基础信息
     *
     * @return
     */
    @ResponseBody
    @RequiresRoles(value = {RoleSign.COACH, RoleSign.STUDENT},logical = Logical.OR)
    @RequestMapping(value = "/getDSInfo")
    public JsonResult getDSInfo() {

        DSInfoEntity dsInfoEntity = manageServiceFacade.getDSInfo(getCoachStudentUser().getDsId());

        return new JsonResult<DSInfoEntity>(ErrorDef.SUCCESS, "查询成功", dsInfoEntity);
    }

}
