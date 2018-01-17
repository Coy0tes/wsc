/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.haibao.web;

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
import com.jeeplus.modules.haibao.entity.Haibao;
import com.jeeplus.modules.haibao.service.HaibaoService;

/**
 * haibaoController
 * @author mxc
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/haibao/haibao")
public class HaibaoController extends BaseController {

	@Autowired
	private HaibaoService haibaoService;
	
	@ModelAttribute
	public Haibao get(@RequestParam(required=false) String id) {
		Haibao entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = haibaoService.get(id);
		}
		if (entity == null){
			entity = new Haibao();
		}
		return entity;
	}
	
	/**
	 * 海报管理列表页面
	 */
	@RequiresPermissions("haibao:haibao:list")
	@RequestMapping(value = {"list", ""})
	public String list(Haibao haibao, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Haibao> page = haibaoService.findPage(new Page<Haibao>(request, response), haibao); 
		model.addAttribute("page", page);
		return "modules/haibao/haibaoList";
	}

	/**
	 * 查看，增加，编辑海报管理表单页面
	 */
	@RequiresPermissions(value={"haibao:haibao:view","haibao:haibao:add","haibao:haibao:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Haibao haibao, Model model) {
		model.addAttribute("haibao", haibao);
		return "modules/haibao/haibaoForm";
	}

	/**
	 * 保存海报管理
	 */
	@RequiresPermissions(value={"haibao:haibao:add","haibao:haibao:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Haibao haibao, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, haibao)){
			return form(haibao, model);
		}
		if(!haibao.getIsNewRecord()){//编辑表单保存
			Haibao t = haibaoService.get(haibao.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(haibao, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			haibaoService.save(t);//保存
		}else{//新增表单保存
			List<Haibao> h = haibaoService.findList(haibao);
			if(h.size()>0){
				addMessage(redirectAttributes, "只能上传一张！");
			}else{
				haibaoService.save(haibao);//保存
				addMessage(redirectAttributes, "保存海报管理成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
	}
	
	/**
	 * 删除海报管理
	 */
	@RequiresPermissions("haibao:haibao:del")
	@RequestMapping(value = "delete")
	public String delete(Haibao haibao, RedirectAttributes redirectAttributes) {
		haibaoService.delete(haibao);
		addMessage(redirectAttributes, "删除海报管理成功");
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
	}
	
	/**
	 * 批量删除海报管理
	 */
	@RequiresPermissions("haibao:haibao:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			haibaoService.delete(haibaoService.get(id));
		}
		addMessage(redirectAttributes, "删除海报管理成功");
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("haibao:haibao:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Haibao haibao, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "海报管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Haibao> page = haibaoService.findPage(new Page<Haibao>(request, response, -1), haibao);
    		new ExportExcel("海报管理", Haibao.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出海报管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("haibao:haibao:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Haibao> list = ei.getDataList(Haibao.class);
			for (Haibao haibao : list){
				try{
					haibaoService.save(haibao);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条海报管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条海报管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入海报管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
    }
	
	/**
	 * 下载导入海报管理数据模板
	 */
	@RequiresPermissions("haibao:haibao:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "海报管理数据导入模板.xlsx";
    		List<Haibao> list = Lists.newArrayList(); 
    		new ExportExcel("海报管理数据", Haibao.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/haibao/haibao/?repage";
    }
	
	
	

}