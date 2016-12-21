package com.santi.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.MD5Util;
import com.santi.core.entity.AdminEntity;
import com.santi.core.entity.RoleEntity;
import com.santi.core.service.AdminUserService;

// http://139.196.152.50:8080/zszj/login/admin
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	@Autowired
    private AdminUserService adminService;
	
	@RequestMapping("console123")
	public String main(){
		return "commons/index";
	}
	
	@RequestMapping("goAdminConsole")
	public String adminView(){
		return "adminconsole/adminconsole";
	}
	
	@RequestMapping("/listAdmin")
	@ResponseBody
	public List<AdminEntity> getAdminList() {
		logger.info("try to load the admin user data list...");
		return adminService.list();
	}
	
	@RequestMapping("/listRole")
	@ResponseBody
	public List<RoleEntity> getRoleList() {
		logger.info("try to load the admin user data list...");
		List<RoleEntity> roleList = adminService.getRoleList();
		List<RoleEntity> adminRoles = new ArrayList<RoleEntity>();
		for(RoleEntity role : roleList) {
			if(DataEntityConstants.ROLE_ADMIN_TYPE.equals(role.getType()) || DataEntityConstants.ROLE_OPERATOR_TYPE.equals(role.getType())) {
				adminRoles.add(role);
			}
		}
		return adminRoles;
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("editAdmin")
	@ResponseBody
	public AdminEntity editAdmin(Integer id){
		logger.info("try to load the admin data entity by id : " + id);
		return adminService.getEntityByid(id);
	}
	
	/**
	 * 通过ajax保存
	 * @param user
	 * @param result 
	 * @return
	 */
	@RequestMapping(value="saveAdmin", method={RequestMethod.POST})
	@ResponseBody
	public RespJson save(@Valid AdminEntity entity ,BindingResult result ){
		if(result.hasErrors()){
			return RespJsonFactory.buildFailure(result.getAllErrors().get(0).getDefaultMessage(), "module.codetype.exist.error");
		}
		if(entity.getId() == null) {
			logger.info("create new code type...");
			entity.setGender(1); // default man
			entity.setPassword(MD5Util.getMD5String(entity.getPassword()));
			adminService.create(entity);
		}
		else {
			logger.info("update admin profile by id : " + entity.getId());
			adminService.update(entity);
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/deleteAdmin", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteAdminUser(@RequestBody AdminEntity dto) {
		if(dto != null)
		{
			adminService.delete(dto);
			logger.info("delete admin user from db with id : " + dto.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
}
