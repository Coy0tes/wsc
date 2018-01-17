/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmfenyongbili.web;

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
import com.jeeplus.modules.dmfenyongbili.entity.DmFenyongbili;
import com.jeeplus.modules.dmfenyongbili.service.DmFenyongbiliService;

/**
 * 分佣比例Controller
 * @author admin
 * @version 2017-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/dmfenyongbili/dmFenyongbili")
public class DmFenyongbiliController extends BaseController {

	@Autowired
	private DmFenyongbiliService dmFenyongbiliService;
	
	@ModelAttribute
	public DmFenyongbili get(@RequestParam(required=false) String id) {
		DmFenyongbili entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmFenyongbiliService.get(id);
		}
		if (entity == null){
			entity = new DmFenyongbili();
		}
		return entity;
	}
	
	/**
	 * 分佣比例列表页面
	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmFenyongbili dmFenyongbili, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmFenyongbili> page = dmFenyongbiliService.findPage(new Page<DmFenyongbili>(request, response), dmFenyongbili); 
		model.addAttribute("page", page);
		return "modules/dmfenyongbili/dmFenyongbiliList";
	}

	/**
	 * 查看，增加，编辑分佣比例表单页面
	 */
	@RequiresPermissions(value={"dmfenyongbili:dmFenyongbili:view","dmfenyongbili:dmFenyongbili:add","dmfenyongbili:dmFenyongbili:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmFenyongbili dmFenyongbili, Model model) {
		//如果数据库中存在参数配置数据，从数据库中取出数据赋值到 dmFenyongbili 中
		//如果数据库没有值，则直接执行下一行
		List<DmFenyongbili> list = dmFenyongbiliService.findList(new DmFenyongbili());
		if(list.size() > 0){
			dmFenyongbili = list.get(0);
		}
		model.addAttribute("dmFenyongbili", dmFenyongbili);
		return "modules/dmfenyongbili/dmFenyongbiliForm";
	}

	/**
	 * 保存分佣比例
	 */
	@RequiresPermissions(value={"dmfenyongbili:dmFenyongbili:add","dmfenyongbili:dmFenyongbili:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmFenyongbili dmFenyongbili, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmFenyongbili)){
			return form(dmFenyongbili, model);
		}
		if(!dmFenyongbili.getIsNewRecord()){//编辑表单保存
			DmFenyongbili t = dmFenyongbiliService.get(dmFenyongbili.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmFenyongbili, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmFenyongbiliService.save(t);//保存
		}else{//新增表单保存
			dmFenyongbiliService.save(dmFenyongbili);//保存
		}
		addMessage(redirectAttributes, "保存分佣比例成功");
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/form"; //函数执行完毕后的跳转页面。
	}
	
	/**
	 * 删除分佣比例
	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:del")
	@RequestMapping(value = "delete")
	public String delete(DmFenyongbili dmFenyongbili, RedirectAttributes redirectAttributes) {
		dmFenyongbiliService.delete(dmFenyongbili);
		addMessage(redirectAttributes, "删除分佣比例成功");
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/?repage";
	}
	
	/**
	 * 批量删除分佣比例
	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmFenyongbiliService.delete(dmFenyongbiliService.get(id));
		}
		addMessage(redirectAttributes, "删除分佣比例成功");
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmFenyongbili dmFenyongbili, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分佣比例"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmFenyongbili> page = dmFenyongbiliService.findPage(new Page<DmFenyongbili>(request, response, -1), dmFenyongbili);
    		new ExportExcel("分佣比例", DmFenyongbili.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出分佣比例记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmFenyongbili> list = ei.getDataList(DmFenyongbili.class);
			for (DmFenyongbili dmFenyongbili : list){
				try{
					dmFenyongbiliService.save(dmFenyongbili);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条分佣比例记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分佣比例记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分佣比例失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/?repage";
    }
	
	/**
	 * 下载导入分佣比例数据模板
	 */
	@RequiresPermissions("dmfenyongbili:dmFenyongbili:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分佣比例数据导入模板.xlsx";
    		List<DmFenyongbili> list = Lists.newArrayList(); 
    		new ExportExcel("分佣比例数据", DmFenyongbili.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmfenyongbili/dmFenyongbili/?repage";
    }
	
	
	

}