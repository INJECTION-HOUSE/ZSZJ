package com.santi.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.MD5Util;
import com.santi.core.dao.AdminUserDao;
import com.santi.core.dao.MemberDao;
import com.santi.core.entity.AdminEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.global.common.IPUtil;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.ResetPasswordParam;
import com.santi.core.service.LoginService;

@Service
public class LoginserviceImpl implements LoginService {
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
    private MemberDao memberDao;
	
	@Autowired
    private AdminUserDao adminDao;
	
	@Override
	@Transactional(readOnly = false)
	public RespJson login(HttpServletRequest request, UserEntity entity) {			
		//账号密码判定
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAccount", entity.getUserAccount());
		UserEntity user = memberDao.findUser(map);
		if(user!=null){
			log.debug("账号验证成功");
			try {
				String password = MD5Util.getMD5String(entity.getPassword());
				if(user.getPassword().equals(password)){
					log.debug("密码验证成功");
					LoginInfoUtil.setLoginInfoToSession(request, user, "");
					return RespJsonFactory.buildSuccess();
				}else {
					return RespJsonFactory.buildFailure("login.error");
				}
			} catch (Exception e) {
				log.debug("账号密码验证失败", e);
			}
		}
		return RespJsonFactory.buildFailure("login.error");
	}
	
	@Override
	@Transactional(readOnly = false)
	public RespJson passwordUpdate(HttpServletRequest request, String password, String newpassword) {
		//获取session中存入的用户名
		String username = LoginInfoUtil.getUserName(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAccount", username);
		UserEntity user = memberDao.findUser(map);
		System.out.println(user);
		//获取原密码
		String userPW = user.getPassword();
		if(userPW.equals(MD5Util.getMD5String(password))){
			log.debug("密码验证成功");
			//密码修改
			newpassword = MD5Util.getMD5String(newpassword);
			map.put("newpassword", newpassword);
			memberDao.updatePassword(map);
			
			return RespJsonFactory.buildSuccess();
		}else{
			//密码不正确
			log.debug("密码验证失败");
			return RespJsonFactory.buildFailure("login.passwordError");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public RespJson loginAdmin(HttpServletRequest request, UserEntity entity) {
		//账号密码判定
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAccount", entity.getUserAccount());
		AdminEntity adminUser = adminDao.getAdminInfoByCellphone(entity.getUserAccount());
		if(adminUser!=null){
			UserEntity user = new UserEntity();
			user.setUserAccount(entity.getUserAccount());
			log.debug("账号验证成功");
			try {
				String password = MD5Util.getMD5String(entity.getPassword());
				if(adminUser.getPassword().equals(password)){
					user.setPassword(entity.getPassword());
					user.setUsername(adminUser.getUserName());
					user.setGender(adminUser.getGender());
					user.setId(adminUser.getId());
					String ip = IPUtil.getIp(request);
					adminUser.setLoginIP(ip);
					adminUser.setLoginTime(String.valueOf(System.currentTimeMillis()));
					adminDao.update(adminUser);
					log.debug("密码验证成功");
					LoginInfoUtil.setLoginInfoToSession(request, user, "");
					return RespJsonFactory.buildSuccess();
				}else {
					return RespJsonFactory.buildFailure("login.error");
				}
			} catch (Exception e) {
				log.debug("账号密码验证失败", e);
			}
		}
		return RespJsonFactory.buildFailure("login.error");
	}

	@Override
	public void resetPassword(ResetPasswordParam param) {
		String newPwd = MD5Util.getMD5String(param.getPwd());
		param.setPwd(newPwd);
		memberDao.resetPassword(param);
	}

}
