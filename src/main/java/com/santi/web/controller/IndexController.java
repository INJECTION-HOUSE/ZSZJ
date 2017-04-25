package com.santi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.santi.core.global.common.LoginInfoUtil;


@Controller
@RequestMapping("index")
public class IndexController {

	@RequestMapping("main")
	public String main(Model model){
		String cellphone = LoginInfoUtil.getLoginInfo().getLoginUser().getUserAccount();
		model.addAttribute("useraccount", cellphone);
		return "pclogin/index";
	}
	
}
