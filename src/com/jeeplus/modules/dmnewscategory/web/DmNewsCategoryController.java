/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmnewscategory.web;

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
import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import com.jeeplus.modules.dmnewscategory.service.DmNewsCategoryService;
import com.jeeplus.modules.goodscategory.entity.GoodsCategory;
import com.jeeplus.modules.news.entity.DmNews;

/**
 * 新闻分类Controller
 * @author admin
 * @version 2017-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/dmnewscategory/dmNewsCategory")
public class DmNewsCategoryController extends BaseController {

	@Autowired
	private DmNewsCategoryService dmNewsCategoryService;
	
	@ModelAttribute
	public DmNewsCategory get(@RequestParam(required=false) String id) {
		DmNewsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmNewsCategoryService.get(id);
		}
		if (entity == null){
			entity = new DmNewsCategory();
		}
		return entity;
	}
	
	/**
	 * 新闻分类列表页面
	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmNewsCategory dmNewsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmNewsCategory> page = dmNewsCategoryService.findPage(new Page<DmNewsCategory>(request, response), dmNewsCategory); 
		model.addAttribute("page", page);
		return "modules/dmnewscategory/dmNewsCategoryList";
	}

	/**
	 * 查看，增加，编辑新闻分类表单页面
	 */
	@RequiresPermissions(value={"dmnewscategory:dmNewsCategory:view","dmnewscategory:dmNewsCategory:add","dmnewscategory:dmNewsCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmNewsCategory dmNewsCategory, Model model) {
		//排序自动附最大值
		if(dmNewsCategory.getSort() == null){
			List<DmNewsCategory> lists = dmNewsCategoryService.findList(dmNewsCategory);
			int j = 0;
			if(lists.size()!=0){
				for(int i = 0; i < lists.size(); i++){
					if(lists.get(i).getSort() > j){
						j = lists.get(i).getSort();
						dmNewsCategory.setSort(j+1);
					}
				}
			}else{
				dmNewsCategory.setSort(j+1);
			}
		}
		model.addAttribute("dmNewsCategory", dmNewsCategory);
		return "modules/dmnewscategory/dmNewsCategoryForm";
	}

	/**
	 * 保存新闻分类
	 */
	@RequiresPermissions(value={"dmnewscategory:dmNewsCategory:add","dmnewscategory:dmNewsCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmNewsCategory dmNewsCategory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmNewsCategory)){
			return form(dmNewsCategory, model);
		}
		if(!dmNewsCategory.getIsNewRecord()){//编辑表单保存
			DmNewsCategory t = dmNewsCategoryService.get(dmNewsCategory.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmNewsCategory, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmNewsCategoryService.save(t);//保存
		}else{//新增表单保存
			dmNewsCategoryService.save(dmNewsCategory);//保存
		}
		addMessage(redirectAttributes, "保存新闻分类成功");
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
	}
	
	/**
	 * 删除新闻分类
	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:del")
	@RequestMapping(value = "delete")
	public String delete(DmNewsCategory dmNewsCategory, RedirectAttributes redirectAttributes) {
		dmNewsCategoryService.delete(dmNewsCategory);
		addMessage(redirectAttributes, "删除新闻分类成功");
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
	}
	
	/**
	 * 批量删除新闻分类
	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmNewsCategoryService.delete(dmNewsCategoryService.get(id));
		}
		addMessage(redirectAttributes, "删除新闻分类成功");
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmNewsCategory dmNewsCategory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻分类"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmNewsCategory> page = dmNewsCategoryService.findPage(new Page<DmNewsCategory>(request, response, -1), dmNewsCategory);
    		new ExportExcel("新闻分类", DmNewsCategory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出新闻分类记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmNewsCategory> list = ei.getDataList(DmNewsCategory.class);
			for (DmNewsCategory dmNewsCategory : list){
				try{
					dmNewsCategoryService.save(dmNewsCategory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条新闻分类记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条新闻分类记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入新闻分类失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
    }
	
	/**
	 * 下载导入新闻分类数据模板
	 */
	@RequiresPermissions("dmnewscategory:dmNewsCategory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻分类数据导入模板.xlsx";
    		List<DmNewsCategory> list = Lists.newArrayList(); 
    		new ExportExcel("新闻分类数据", DmNewsCategory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/dmnewscategory/dmNewsCategory/?repage";
    }
	
	
	

}