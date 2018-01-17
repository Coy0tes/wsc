/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.xsejl.web;

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
import com.jeeplus.modules.xsejl.entity.DmXsejl;
import com.jeeplus.modules.xsejl.service.DmXsejlService;

/**
 * 销售额奖励Controller
 * @author zhaoliangdong
 * @version 2017-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/xsejl/dmXsejl")
public class DmXsejlController extends BaseController {

	@Autowired
	private DmXsejlService dmXsejlService;
	
	@ModelAttribute
	public DmXsejl get(@RequestParam(required=false) String id) {
		DmXsejl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmXsejlService.get(id);
		}
		if (entity == null){
			entity = new DmXsejl();
		}
		return entity;
	}
	
	/**
	 * 销售额列表页面
	 */
	@RequiresPermissions("xsejl:dmXsejl:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmXsejl dmXsejl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmXsejl> page = dmXsejlService.findPage(new Page<DmXsejl>(request, response), dmXsejl); 
		model.addAttribute("page", page);
		return "modules/xsejl/dmXsejlList";
	}

	/**
	 * 查看，增加，编辑销售额表单页面
	 */
	@RequiresPermissions(value={"xsejl:dmXsejl:view","xsejl:dmXsejl:add","xsejl:dmXsejl:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmXsejl dmXsejl, Model model) {
		model.addAttribute("dmXsejl", dmXsejl);
		return "modules/xsejl/dmXsejlForm";
	}

	/**
	 * 保存销售额
	 */
	@RequiresPermissions(value={"xsejl:dmXsejl:add","xsejl:dmXsejl:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmXsejl dmXsejl, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmXsejl)){
			return form(dmXsejl, model);
		}
		if(!dmXsejl.getIsNewRecord()){//编辑表单保存
			DmXsejl t = dmXsejlService.get(dmXsejl.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmXsejl, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmXsejlService.save(t);//保存
		}else{//新增表单保存
			dmXsejlService.save(dmXsejl);//保存
		}
		addMessage(redirectAttributes, "保存销售额成功");
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
	}
	
	/**
	 * 删除销售额
	 */
	@RequiresPermissions("xsejl:dmXsejl:del")
	@RequestMapping(value = "delete")
	public String delete(DmXsejl dmXsejl, RedirectAttributes redirectAttributes) {
		dmXsejlService.delete(dmXsejl);
		addMessage(redirectAttributes, "删除销售额成功");
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
	}
	
	/**
	 * 批量删除销售额
	 */
	@RequiresPermissions("xsejl:dmXsejl:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmXsejlService.delete(dmXsejlService.get(id));
		}
		addMessage(redirectAttributes, "删除销售额成功");
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("xsejl:dmXsejl:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmXsejl dmXsejl, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销售额"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmXsejl> page = dmXsejlService.findPage(new Page<DmXsejl>(request, response, -1), dmXsejl);
    		new ExportExcel("销售额", DmXsejl.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出销售额记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("xsejl:dmXsejl:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmXsejl> list = ei.getDataList(DmXsejl.class);
			for (DmXsejl dmXsejl : list){
				try{
					dmXsejlService.save(dmXsejl);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销售额记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条销售额记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入销售额失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
    }
	
	/**
	 * 下载导入销售额数据模板
	 */
	@RequiresPermissions("xsejl:dmXsejl:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销售额数据导入模板.xlsx";
    		List<DmXsejl> list = Lists.newArrayList(); 
    		new ExportExcel("销售额数据", DmXsejl.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xsejl/dmXsejl/?repage";
    }
	
	
	

}