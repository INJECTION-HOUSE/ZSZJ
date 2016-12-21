package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.StringUtil;
import com.santi.core.entity.CodeItemEntity;
import com.santi.core.entity.CodeTypeEntity;
import com.santi.core.service.CodeService;

@Controller
@RequestMapping("code")
public class CodeController extends BaseController {
	private static final Logger logger = Logger.getLogger(CodeController.class);
	
	@Autowired
    private CodeService codeService;
	
	@RequestMapping(value = "tview", method = RequestMethod.GET)
	public String goTypeView(Locale locale, Model model) {
		logger.info("go to code type page");
		return "codes/codetype";
	}
	
	@RequestMapping("/listType")
	@ResponseBody
	public List<CodeTypeEntity> getTypeList() {
		logger.info("try to load the code type data list...");
		return codeService.listTypes();
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("editType")
	@ResponseBody
	public CodeTypeEntity editType(Integer id){
		logger.info("try to load the code type data entity by id : " + id);
		return codeService.findCodeTypeById(id);
	}
	
	/**
	 * 通过ajax保存
	 * @param user
	 * @param result 
	 * @return
	 */
	@RequestMapping(value="saveType", method={RequestMethod.POST})
	@ResponseBody
	public RespJson save(@Valid CodeTypeEntity codeType,BindingResult result ){
		if(result.hasErrors()){
			return RespJsonFactory.buildFailure(result.getAllErrors().get(0).getDefaultMessage(), "module.codetype.exist.error");
		}
		if(codeType.getId() == null) {
			logger.info("create new code type...");
			codeService.createType(codeType);
		}
		else {
			logger.info("update code type by id : " + codeType.getId());
			codeService.updateType(codeType);			
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/deleteType", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteTypeEntity(@RequestBody CodeTypeEntity dto) {
		if(dto != null)
		{
			codeService.deleteType(dto);
			logger.info("delete Code type data from db with id : " + dto.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
	
	
	@RequestMapping(value = "cview", method = RequestMethod.GET)
	public String goCodeView(Locale locale, Model model) {
		logger.info("go to code type page");
		return "codes/codes";
	}
	
	@RequestMapping("listItems")
	@ResponseBody
	public List<CodeItemEntity> list(String typeName,String codeName){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("typeName", StringUtil.isEmpty(typeName) ? null : typeName);
		paramMap.put("codeName", StringUtil.isEmpty(codeName) ? null : codeName);
		return codeService.listCodeItems(paramMap);
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("editItem")
	@ResponseBody
	public CodeItemEntity editCodeItem(Integer id){
		logger.info("try to load the code item data entity by id : " + id);
		return codeService.findCodeItemByid(id);
	}
	
	/**
	 * 通过ajax保存
	 * @param user
	 * @param result 
	 * @return
	 */
	@RequestMapping(value="saveItem", method={RequestMethod.POST})
	@ResponseBody
	public RespJson saveItem(@Valid CodeItemEntity item,BindingResult result ){
		if(result.hasErrors()){
			return RespJsonFactory.buildFailure(result.getAllErrors().get(0).getDefaultMessage(), "module.codeitem.exist.error");
		}
		if(item.getId() == null) {
			logger.info("create new code item...");
			codeService.createCodeItem(item);
		}
		else {
			logger.info("update code item by id : " + item.getId());
			codeService.updateCodeItem(item);		
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/deleteItem", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteCodeItem(@RequestBody CodeItemEntity dto) {
		if(dto != null)
		{
			codeService.deleteCodeItem(dto);
			logger.info("delete Code item type data from db with id : " + dto.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}

}
