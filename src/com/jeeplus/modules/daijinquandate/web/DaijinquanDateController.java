/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.daijinquandate.web;

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
import com.jeeplus.modules.daijinquandate.entity.DaijinquanDate;
import com.jeeplus.modules.daijinquandate.service.DaijinquanDateService;

/**
 * 代金券活动期限设置Controller
 * @author mxc
 * @version 2017-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/daijinquandate/daijinquanDate")
public class DaijinquanDateController extends BaseController {

	@Autowired
	private DaijinquanDateService daijinquanDateService;
	
	@ModelAttribute
	public DaijinquanDate get(@RequestParam(required=false) String id) {
		DaijinquanDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = daijinquanDateService.get(id);
		}
		if (entity == null){
			entity = new DaijinquanDate();
		}
		return entity;
	}
	
	/**
	 * 代金券活动期限列表页面
	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:list")
	@RequestMapping(value = {"list", ""})
	public String list(DaijinquanDate daijinquanDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DaijinquanDate> page = daijinquanDateService.findPage(new Page<DaijinquanDate>(request, response), daijinquanDate); 
		model.addAttribute("page", page);
		return "modules/daijinquandate/daijinquanDateList";
	}

	/**
	 * 查看，增加，编辑代金券活动期限表单页面
	 */
	@RequiresPermissions(value={"daijinquandate:daijinquanDate:view","daijinquandate:daijinquanDate:add","daijinquandate:daijinquanDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DaijinquanDate daijinquanDate, Model model) {
		model.addAttribute("daijinquanDate", daijinquanDate);
		return "modules/daijinquandate/daijinquanDateForm";
	}

	/**
	 * 保存代金券活动期限
	 */
	@RequiresPermissions(value={"daijinquandate:daijinquanDate:add","daijinquandate:daijinquanDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DaijinquanDate daijinquanDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, daijinquanDate)){
			return form(daijinquanDate, model);
		}
		if(!daijinquanDate.getIsNewRecord()){//编辑表单保存
			DaijinquanDate t = daijinquanDateService.get(daijinquanDate.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(daijinquanDate, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			daijinquanDateService.save(t);//保存
		}else{//新增表单保存
			daijinquanDateService.save(daijinquanDate);//保存
		}
		addMessage(redirectAttributes, "保存代金券活动期限成功");
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
	}
	
	/**
	 * 删除代金券活动期限
	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:del")
	@RequestMapping(value = "delete")
	public String delete(DaijinquanDate daijinquanDate, RedirectAttributes redirectAttributes) {
		daijinquanDateService.delete(daijinquanDate);
		addMessage(redirectAttributes, "删除代金券活动期限成功");
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
	}
	
	/**
	 * 批量删除代金券活动期限
	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			daijinquanDateService.delete(daijinquanDateService.get(id));
		}
		addMessage(redirectAttributes, "删除代金券活动期限成功");
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DaijinquanDate daijinquanDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "代金券活动期限"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DaijinquanDate> page = daijinquanDateService.findPage(new Page<DaijinquanDate>(request, response, -1), daijinquanDate);
    		new ExportExcel("代金券活动期限", DaijinquanDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出代金券活动期限记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DaijinquanDate> list = ei.getDataList(DaijinquanDate.class);
			for (DaijinquanDate daijinquanDate : list){
				try{
					daijinquanDateService.save(daijinquanDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条代金券活动期限记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条代金券活动期限记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入代金券活动期限失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
    }
	
	/**
	 * 下载导入代金券活动期限数据模板
	 */
	@RequiresPermissions("daijinquandate:daijinquanDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "代金券活动期限数据导入模板.xlsx";
    		List<DaijinquanDate> list = Lists.newArrayList(); 
    		new ExportExcel("代金券活动期限数据", DaijinquanDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/daijinquandate/daijinquanDate/?repage";
    }
	
	
	

}