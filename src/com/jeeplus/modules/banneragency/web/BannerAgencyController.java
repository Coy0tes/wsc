/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.banneragency.web;

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
import com.jeeplus.modules.banneragency.entity.BannerAgency;
import com.jeeplus.modules.banneragency.service.BannerAgencyService;
import com.jeeplus.modules.news.entity.DmNews;

/**
 * 商城轮播图管理Controller
 * @author zhaoliangdong
 * @version 2017-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/banneragency/bannerAgency")
public class BannerAgencyController extends BaseController {

	@Autowired
	private BannerAgencyService bannerAgencyService;
	
	@ModelAttribute
	public BannerAgency get(@RequestParam(required=false) String id) {
		BannerAgency entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bannerAgencyService.get(id);
		}
		if (entity == null){
			entity = new BannerAgency();
		}
		return entity;
	}
	
	/**
	 * 轮播图列表页面
	 */
	@RequiresPermissions("banneragency:bannerAgency:list")
	@RequestMapping(value = {"list", ""})
	public String list(BannerAgency bannerAgency, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BannerAgency> page = bannerAgencyService.findPage(new Page<BannerAgency>(request, response), bannerAgency); 
		model.addAttribute("page", page);
		return "modules/banneragency/bannerAgencyList";
	}

	/**
	 * 查看，增加，编辑轮播图表单页面
	 */
	@RequiresPermissions(value={"banneragency:bannerAgency:view","banneragency:bannerAgency:add","banneragency:bannerAgency:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BannerAgency bannerAgency, Model model) {
		//排序自动附最大值
		if(bannerAgency.getSort() == null){
			List<BannerAgency> lists = bannerAgencyService.findList(bannerAgency);
			int j = 0;
			if(lists.size()!=0){
				for(int i = 0; i < lists.size(); i++){
					if(lists.get(i).getSort() > j){
						j = lists.get(i).getSort();
						bannerAgency.setSort(j+1);
					}
				}
			}else{
				bannerAgency.setSort(j+1);
			}
		}
		model.addAttribute("bannerAgency", bannerAgency);
		return "modules/banneragency/bannerAgencyForm";
	}

	/**
	 * 保存轮播图
	 */
	@RequiresPermissions(value={"banneragency:bannerAgency:add","banneragency:bannerAgency:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BannerAgency bannerAgency, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bannerAgency)){
			return form(bannerAgency, model);
		}
		if(!bannerAgency.getIsNewRecord()){//编辑表单保存
			BannerAgency t = bannerAgencyService.get(bannerAgency.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bannerAgency, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bannerAgencyService.save(t);//保存
		}else{//新增表单保存
			bannerAgencyService.save(bannerAgency);//保存
		}
		addMessage(redirectAttributes, "保存轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
	}
	
	/**
	 * 删除轮播图
	 */
	@RequiresPermissions("banneragency:bannerAgency:del")
	@RequestMapping(value = "delete")
	public String delete(BannerAgency bannerAgency, RedirectAttributes redirectAttributes) {
		bannerAgencyService.delete(bannerAgency);
		addMessage(redirectAttributes, "删除轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
	}
	
	/**
	 * 批量删除轮播图
	 */
	@RequiresPermissions("banneragency:bannerAgency:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bannerAgencyService.delete(bannerAgencyService.get(id));
		}
		addMessage(redirectAttributes, "删除轮播图成功");
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("banneragency:bannerAgency:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BannerAgency bannerAgency, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "轮播图"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BannerAgency> page = bannerAgencyService.findPage(new Page<BannerAgency>(request, response, -1), bannerAgency);
    		new ExportExcel("轮播图", BannerAgency.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出轮播图记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("banneragency:bannerAgency:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BannerAgency> list = ei.getDataList(BannerAgency.class);
			for (BannerAgency bannerAgency : list){
				try{
					bannerAgencyService.save(bannerAgency);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条轮播图记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条轮播图记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入轮播图失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
    }
	
	/**
	 * 下载导入轮播图数据模板
	 */
	@RequiresPermissions("banneragency:bannerAgency:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "轮播图数据导入模板.xlsx";
    		List<BannerAgency> list = Lists.newArrayList(); 
    		new ExportExcel("轮播图数据", BannerAgency.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/banneragency/bannerAgency/?repage";
    }
	
	
	

}