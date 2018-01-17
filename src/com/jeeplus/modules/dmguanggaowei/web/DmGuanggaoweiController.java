/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmguanggaowei.web;

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
import com.jeeplus.modules.dmguanggaowei.entity.DmGuanggaowei;
import com.jeeplus.modules.dmguanggaowei.service.DmGuanggaoweiService;

/**
 * 广告位管理Controller
 * @author mxc
 * @version 2017-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/dmguanggaowei/dmGuanggaowei")
public class DmGuanggaoweiController extends BaseController {

	@Autowired
	private DmGuanggaoweiService dmGuanggaoweiService;
	
	@ModelAttribute
	public DmGuanggaowei get(@RequestParam(required=false) String id) {
		DmGuanggaowei entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmGuanggaoweiService.get(id);
		}
		if (entity == null){
			entity = new DmGuanggaowei();
		}
		return entity;
	}
	
	/**
	 * 广告位管理列表页面
	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmGuanggaowei dmGuanggaowei, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmGuanggaowei> page = dmGuanggaoweiService.findPage(new Page<DmGuanggaowei>(request, response), dmGuanggaowei); 
		model.addAttribute("page", page);
		return "modules/dmguanggaowei/dmGuanggaoweiList";
	}

	/**
	 * 查看，增加，编辑广告位管理表单页面
	 */
	@RequiresPermissions(value={"dmguanggaowei:dmGuanggaowei:view","dmguanggaowei:dmGuanggaowei:add","dmguanggaowei:dmGuanggaowei:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmGuanggaowei dmGuanggaowei, Model model) {
		List<DmGuanggaowei> lists = dmGuanggaoweiService.findList(dmGuanggaowei);
		dmGuanggaowei.setSort((lists.size())+1);
		model.addAttribute("dmGuanggaowei", dmGuanggaowei);
		return "modules/dmguanggaowei/dmGuanggaoweiForm";
	}

	/**
	 * 保存广告位管理
	 */
	@RequiresPermissions(value={"dmguanggaowei:dmGuanggaowei:add","dmguanggaowei:dmGuanggaowei:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmGuanggaowei dmGuanggaowei, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmGuanggaowei)){
			return form(dmGuanggaowei, model);
		}
		if(!dmGuanggaowei.getIsNewRecord()){//编辑表单保存
			DmGuanggaowei t = dmGuanggaoweiService.get(dmGuanggaowei.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmGuanggaowei, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmGuanggaoweiService.save(t);//保存
		}else{//新增表单保存
			dmGuanggaoweiService.save(dmGuanggaowei);//保存
		}
		addMessage(redirectAttributes, "保存广告位管理成功");
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
	}
	
	/**
	 * 删除广告位管理
	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:del")
	@RequestMapping(value = "delete")
	public String delete(DmGuanggaowei dmGuanggaowei, RedirectAttributes redirectAttributes) {
		dmGuanggaoweiService.delete(dmGuanggaowei);
		addMessage(redirectAttributes, "删除广告位管理成功");
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
	}
	
	/**
	 * 批量删除广告位管理
	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmGuanggaoweiService.delete(dmGuanggaoweiService.get(id));
		}
		addMessage(redirectAttributes, "删除广告位管理成功");
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmGuanggaowei dmGuanggaowei, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "广告位管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmGuanggaowei> page = dmGuanggaoweiService.findPage(new Page<DmGuanggaowei>(request, response, -1), dmGuanggaowei);
    		new ExportExcel("广告位管理", DmGuanggaowei.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出广告位管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmGuanggaowei> list = ei.getDataList(DmGuanggaowei.class);
			for (DmGuanggaowei dmGuanggaowei : list){
				try{
					dmGuanggaoweiService.save(dmGuanggaowei);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条广告位管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条广告位管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入广告位管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
    }
	
	/**
	 * 下载导入广告位管理数据模板
	 */
	@RequiresPermissions("dmguanggaowei:dmGuanggaowei:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "广告位管理数据导入模板.xlsx";
    		List<DmGuanggaowei> list = Lists.newArrayList(); 
    		new ExportExcel("广告位管理数据", DmGuanggaowei.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmguanggaowei/dmGuanggaowei/?repage";
    }
	
	
	

}