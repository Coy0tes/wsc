/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.news.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.modules.dmnewscategory.entity.DmNewsCategory;
import com.jeeplus.modules.goods.entity.Goods;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.news.entity.DmNews;
import com.jeeplus.modules.news.service.DmNewsService;

/**
 * 新闻管理Controller
 * @author mxc
 * @version 2017-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/news/dmNews")
public class DmNewsController extends BaseController {

	@Autowired
	private DmNewsService dmNewsService;
	
	@ModelAttribute
	public DmNews get(@RequestParam(required=false) String id) {
		DmNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dmNewsService.get(id);
		}
		if (entity == null){
			entity = new DmNews();
		}
		return entity;
	}
	
	/**
	 * 新闻信息列表页面
	 */
	@RequiresPermissions("news:dmNews:list")
	@RequestMapping(value = {"list", ""})
	public String list(DmNews dmNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmNews> page = dmNewsService.findPage(new Page<DmNews>(request, response), dmNews); 
		
		model.addAttribute("page", page);
		return "modules/news/dmNewsList";
	}

	/**
	 * 查看，增加，编辑新闻信息表单页面
	 */
	@RequiresPermissions(value={"news:dmNews:view","news:dmNews:add","news:dmNews:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DmNews dmNews, Model model) {
		//排序自动附最大值
		if(dmNews.getSort() == null){
			List<DmNews> lists = dmNewsService.findList(dmNews);
			int j = 0;
			if(lists.size()!=0){
				for(int i = 0; i < lists.size(); i++){
					if(lists.get(i).getSort() > j){
						j = lists.get(i).getSort();
						dmNews.setSort(j+1);
					}
				}
			}else{
				dmNews.setSort(j+1);
			}
		}
		model.addAttribute("dmNews", dmNews);
		return "modules/news/dmNewsForm";
	}

	/**
	 * 保存新闻信息
	 */
	@RequiresPermissions(value={"news:dmNews:add","news:dmNews:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DmNews dmNews, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dmNews)){
			return form(dmNews, model);
		}
		if(!dmNews.getIsNewRecord()){//编辑表单保存
			DmNews t = dmNewsService.get(dmNews.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dmNews, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dmNewsService.save(t);//保存
		}else{//新增表单保存
			dmNewsService.save(dmNews);//保存
		}
		addMessage(redirectAttributes, "保存新闻信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
	}
	
	/**
	 * 删除新闻信息
	 */
	@RequiresPermissions("news:dmNews:del")
	@RequestMapping(value = "delete")
	public String delete(DmNews dmNews, RedirectAttributes redirectAttributes) {
		dmNewsService.delete(dmNews);
		addMessage(redirectAttributes, "删除新闻信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
	}
	
	/**
	 * 批量删除新闻信息
	 */
	@RequiresPermissions("news:dmNews:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dmNewsService.delete(dmNewsService.get(id));
		}
		addMessage(redirectAttributes, "删除新闻信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("news:dmNews:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DmNews dmNews, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DmNews> page = dmNewsService.findPage(new Page<DmNews>(request, response, -1), dmNews);
    		new ExportExcel("新闻信息", DmNews.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出新闻信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("news:dmNews:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DmNews> list = ei.getDataList(DmNews.class);
			for (DmNews dmNews : list){
				try{
					dmNewsService.save(dmNews);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条新闻信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条新闻信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入新闻信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
    }
	
	/**
	 * 下载导入新闻信息数据模板
	 */
	@RequiresPermissions("news:dmNews:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻信息数据导入模板.xlsx";
    		List<DmNews> list = Lists.newArrayList(); 
    		new ExportExcel("新闻信息数据", DmNews.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/dmNews/?repage";
    }
	
	
	/**
	 * 选择所属分类
	 */
	@RequestMapping(value = "selectcategory")
	public String selectcategory(DmNewsCategory category, String url, String fieldLabels, String fieldKeys, String searchLabel, String searchKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DmNewsCategory> page = dmNewsService.findPageBycategory(new Page<DmNewsCategory>(request, response),  category);
		try {
			fieldLabels = URLDecoder.decode(fieldLabels, "UTF-8");
			fieldKeys = URLDecoder.decode(fieldKeys, "UTF-8");
			searchLabel = URLDecoder.decode(searchLabel, "UTF-8");
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("labelNames", fieldLabels.split("\\|"));
		model.addAttribute("labelValues", fieldKeys.split("\\|"));
		model.addAttribute("fieldLabels", fieldLabels);
		model.addAttribute("fieldKeys", fieldKeys);
		model.addAttribute("url", url);
		model.addAttribute("searchLabel", searchLabel);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("obj", category);
		model.addAttribute("page", page);
		return "modules/sys/gridselect";
	}
	

}