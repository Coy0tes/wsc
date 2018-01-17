/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.zfxelq.web;

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
import com.jeeplus.modules.zfxelq.entity.Zfxelq;
import com.jeeplus.modules.zfxelq.service.ZfxelqService;

/**
 * 总分销额领取Controller
 * @author zhaoliangdong
 * @version 2017-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/zfxelq/zfxelq")
public class ZfxelqController extends BaseController {

	@Autowired
	private ZfxelqService zfxelqService;
	
	@ModelAttribute
	public Zfxelq get(@RequestParam(required=false) String id) {
		Zfxelq entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zfxelqService.get(id);
		}
		if (entity == null){
			entity = new Zfxelq();
		}
		return entity;
	}
	
	/**
	 * 分销额列表页面
	 */
	@RequiresPermissions("zfxelq:zfxelq:list")
	@RequestMapping(value = {"list", ""})
	public String list(Zfxelq zfxelq, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Zfxelq> page = zfxelqService.findPage(new Page<Zfxelq>(request, response), zfxelq); 
		model.addAttribute("page", page);
		return "modules/zfxelq/zfxelqList";
	}

	/**
	 * 查看，增加，编辑分销额表单页面
	 */
	@RequiresPermissions(value={"zfxelq:zfxelq:view","zfxelq:zfxelq:add","zfxelq:zfxelq:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Zfxelq zfxelq, Model model) {
		model.addAttribute("zfxelq", zfxelq);
		return "modules/zfxelq/zfxelqForm";
	}

	/**
	 * 保存分销额
	 */
	@RequiresPermissions(value={"zfxelq:zfxelq:add","zfxelq:zfxelq:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Zfxelq zfxelq, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, zfxelq)){
			return form(zfxelq, model);
		}
		if(!zfxelq.getIsNewRecord()){//编辑表单保存
			Zfxelq t = zfxelqService.get(zfxelq.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(zfxelq, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			zfxelqService.save(t);//保存
		}else{//新增表单保存
			zfxelqService.save(zfxelq);//保存
		}
		addMessage(redirectAttributes, "保存分销额成功");
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
	}
	
	/**
	 * 删除分销额
	 */
	@RequiresPermissions("zfxelq:zfxelq:del")
	@RequestMapping(value = "delete")
	public String delete(Zfxelq zfxelq, RedirectAttributes redirectAttributes) {
		zfxelqService.delete(zfxelq);
		addMessage(redirectAttributes, "删除分销额成功");
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
	}
	
	/**
	 * 批量删除分销额
	 */
	@RequiresPermissions("zfxelq:zfxelq:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			zfxelqService.delete(zfxelqService.get(id));
		}
		addMessage(redirectAttributes, "删除分销额成功");
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("zfxelq:zfxelq:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Zfxelq zfxelq, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分销额"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Zfxelq> page = zfxelqService.findPage(new Page<Zfxelq>(request, response, -1), zfxelq);
    		new ExportExcel("分销额", Zfxelq.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出分销额记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("zfxelq:zfxelq:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Zfxelq> list = ei.getDataList(Zfxelq.class);
			for (Zfxelq zfxelq : list){
				try{
					zfxelqService.save(zfxelq);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条分销额记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分销额记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分销额失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
    }
	
	/**
	 * 下载导入分销额数据模板
	 */
	@RequiresPermissions("zfxelq:zfxelq:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分销额数据导入模板.xlsx";
    		List<Zfxelq> list = Lists.newArrayList(); 
    		new ExportExcel("分销额数据", Zfxelq.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/zfxelq/zfxelq/?repage";
    }
	
	
	

}