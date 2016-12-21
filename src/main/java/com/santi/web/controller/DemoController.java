package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.entity.DemoEntity;
import com.santi.core.service.DemoService;

@Controller
@RequestMapping("demo")
public class DemoController {
	private static final Logger logger = Logger.getLogger(DemoController.class);
	
	@Autowired
    private DemoService demoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String hello(Locale locale, Model model) {
		logger.info("Hello Spring MVC");
		return "demo";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public List<DemoEntity> getList() {
		logger.info("try to load the demo data list...");
		return demoService.list();
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteDemoEntity(@RequestBody DemoEntity dto) {
		if(dto != null)
		{
			demoService.delete(dto);
			logger.info("delete DemoEntity data from db...");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
	
}
