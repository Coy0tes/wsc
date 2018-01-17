/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmchongzhidangci.web;

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
import com.jeeplus.modules.dmchongzhidangci.entity.DmChongzhidangci;
import com.jeeplus.modules.dmchongzhidangci.service.DmChongzhidangciService;

/**
 * 充值金额档次： String id  double jine Controller
 * @author mxc
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/dmchongzhidangci/dmChongzhidangci")
public class DmChongzhidangciController extends BaseController {

	@Autowired
	private DmChongzhidangciService dmChongzhidangciService;
	
	@ModelAttribute
	public DmChongzhidangci get(@RequestParam(required=false) String id) {
		DmChongzhidangci entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmChongzhidangciService.get(id);
		}
		if (entity == null){
			entity = new DmChongzhidangci();
		}
		return entity;
	}
	
	/**
	 * 充值金额档次列表页面
	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmChongzhidangci dmChongzhidangci, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmChongzhidangci> page = dmChongzhidangciService.findPage(new Page<DmChongzhidangci>(request, response), dmChongzhidangci); 
		model.addAttribute("page", page);
		return "modules/dmchongzhidangci/dmChongzhidangciList";
	}

	/**
	 * 查看，增加，编辑充值金额档次表单页面
	 */
	@RequiresPermissions(value={"dmchongzhidangci:dmChongzhidangci:view","dmchongzhidangci:dmChongzhidangci:add","dmchongzhidangci:dmChongzhidangci:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmChongzhidangci dmChongzhidangci, Model model) {
		model.addAttribute("dmChongzhidangci", dmChongzhidangci);
		return "modules/dmchongzhidangci/dmChongzhidangciForm";
	}

	/**
	 * 保存充值金额档次
	 */
	@RequiresPermissions(value={"dmchongzhidangci:dmChongzhidangci:add","dmchongzhidangci:dmChongzhidangci:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmChongzhidangci dmChongzhidangci, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmChongzhidangci)){
			return form(dmChongzhidangci, model);
		}
		if(!dmChongzhidangci.getIsNewRecord()){//编辑表单保存
			DmChongzhidangci t = dmChongzhidangciService.get(dmChongzhidangci.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmChongzhidangci, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmChongzhidangciService.save(t);//保存
		}else{//新增表单保存
			dmChongzhidangciService.save(dmChongzhidangci);//保存
		}
		addMessage(redirectAttributes, "保存充值金额档次成功");
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
	}
	
	/**
	 * 删除充值金额档次
	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:del")
	@RequestMapping(value = "delete")
	public String delete(DmChongzhidangci dmChongzhidangci, RedirectAttributes redirectAttributes) {
		dmChongzhidangciService.delete(dmChongzhidangci);
		addMessage(redirectAttributes, "删除充值金额档次成功");
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
	}
	
	/**
	 * 批量删除充值金额档次
	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmChongzhidangciService.delete(dmChongzhidangciService.get(id));
		}
		addMessage(redirectAttributes, "删除充值金额档次成功");
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmChongzhidangci dmChongzhidangci, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "充值金额档次"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmChongzhidangci> page = dmChongzhidangciService.findPage(new Page<DmChongzhidangci>(request, response, -1), dmChongzhidangci);
    		new ExportExcel("充值金额档次", DmChongzhidangci.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出充值金额档次记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmChongzhidangci> list = ei.getDataList(DmChongzhidangci.class);
			for (DmChongzhidangci dmChongzhidangci : list){
				try{
					dmChongzhidangciService.save(dmChongzhidangci);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条充值金额档次记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条充值金额档次记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入充值金额档次失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
    }
	
	/**
	 * 下载导入充值金额档次数据模板
	 */
	@RequiresPermissions("dmchongzhidangci:dmChongzhidangci:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "充值金额档次数据导入模板.xlsx";
    		List<DmChongzhidangci> list = Lists.newArrayList(); 
    		new ExportExcel("充值金额档次数据", DmChongzhidangci.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmchongzhidangci/dmChongzhidangci/?repage";
    }
	
	
	

}