/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.firstsent.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.firstsent.entity.FirstSent;
import com.jeeplus.modules.firstsent.service.FirstSentService;

/**
 * firstsentController
 * @author mxc
 * @version 2017-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/firstsent/firstSent")
public class FirstSentController extends BaseController {

	@Autowired
	private FirstSentService firstSentService;
	
	@ModelAttribute
	public FirstSent get(@RequestParam(required=false) String id) {
		FirstSent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = firstSentService.get(id);
		}
		if (entity == null){
			entity = new FirstSent();
		}
		return entity;
	}
	
	/**
	 * 首次关注送余额列表页面
	 */
	@RequiresPermissions("firstsent:firstSent:list")
	@RequestMapping(value = {"list", ""})
	public String list(FirstSent firstSent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FirstSent> page = firstSentService.findPage(new Page<FirstSent>(request, response), firstSent); 
		model.addAttribute("page", page);
		return "modules/firstsent/firstSentList";
	}

	/**
	 * 查看，增加，编辑首次关注送余额表单页面
	 */
	@RequiresPermissions(value={"firstsent:firstSent:view","firstsent:firstSent:add","firstsent:firstSent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FirstSent firstSent, Model model) {
		model.addAttribute("firstSent", firstSent);
		return "modules/firstsent/firstSentForm";
	}

	/**
	 * 保存首次关注送余额
	 */
	@RequiresPermissions(value={"firstsent:firstSent:add","firstsent:firstSent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FirstSent firstSent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, firstSent)){
			return form(firstSent, model);
		}
		if(!firstSent.getIsNewRecord()){//编辑表单保存
			FirstSent t = firstSentService.get(firstSent.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(firstSent, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			firstSentService.save(t);//保存
		}else{//新增表单保存
			firstSentService.save(firstSent);//保存
		}
		addMessage(redirectAttributes, "保存首次关注送余额成功");
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
	}
	
	/**
	 * 删除首次关注送余额
	 */
	@RequiresPermissions("firstsent:firstSent:del")
	@RequestMapping(value = "delete")
	public String delete(FirstSent firstSent, RedirectAttributes redirectAttributes) {
		firstSentService.delete(firstSent);
		addMessage(redirectAttributes, "删除首次关注送余额成功");
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
	}
	
	/**
	 * 批量删除首次关注送余额
	 */
	@RequiresPermissions("firstsent:firstSent:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			firstSentService.delete(firstSentService.get(id));
		}
		addMessage(redirectAttributes, "删除首次关注送余额成功");
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("firstsent:firstSent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FirstSent firstSent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "首次关注送余额"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FirstSent> page = firstSentService.findPage(new Page<FirstSent>(request, response, -1), firstSent);
    		new ExportExcel("首次关注送余额", FirstSent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出首次关注送余额记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("firstsent:firstSent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FirstSent> list = ei.getDataList(FirstSent.class);
			for (FirstSent firstSent : list){
				try{
					firstSentService.save(firstSent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条首次关注送余额记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条首次关注送余额记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入首次关注送余额失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
    }
	
	/**
	 * 下载导入首次关注送余额数据模板
	 */
	@RequiresPermissions("firstsent:firstSent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "首次关注送余额数据导入模板.xlsx";
    		List<FirstSent> list = Lists.newArrayList(); 
    		new ExportExcel("首次关注送余额数据", FirstSent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/firstsent/firstSent/?repage";
    }
	
	
	

}