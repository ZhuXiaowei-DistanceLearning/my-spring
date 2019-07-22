package com.zxw.web;

import com.zxw.service.UserService;
import org.smart4j.framework.beans.factory.annotation.Autowire;
import org.smart4j.framework.stereotype.Controller;

@Controller
public class VoteServlet {
    @Autowire
    private UserService userService;
}
