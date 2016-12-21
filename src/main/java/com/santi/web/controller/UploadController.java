package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.santi.core.common.util.FileUtils;


/**
 * 文件上传
 * @author Administrator
 *
 */
@Controller
@RequestMapping("fileUpload")
public class UploadController {

	@Value("#{configProperties['upload.location.path']}")
	private String basePath;
	
	@RequestMapping(value="picture",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(MultipartHttpServletRequest request){
		
		List<MultipartFile> files = request.getFiles("imgs");
		String[] path = FileUtils.flushFileStore(files.toArray(new MultipartFile[files.size()]), basePath, "picture");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", path[0]);
		return map;
	}
	
}
