/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsyunfeishezhi.web;

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
import com.jeeplus.modules.goodsyunfeishezhi.entity.GoodsYunfeishezhi;
import com.jeeplus.modules.goodsyunfeishezhi.service.GoodsYunfeishezhiService;

/**
 * 运费管理Controller
 * @author mxc
 * @version 2017-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/goodsyunfeishezhi/goodsYunfeishezhi")
public class GoodsYunfeishezhiController extends BaseController {

	@Autowired
	private GoodsYunfeishezhiService goodsYunfeishezhiService;
	
	@ModelAttribute
	public GoodsYunfeishezhi get(@RequestParam(required=false) String id) {
		GoodsYunfeishezhi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsYunfeishezhiService.get(id);
		}
		if (entity == null){
			entity = new GoodsYunfeishezhi();
		}
		return entity;
	}
	
	/**
	 * 运费管理列表页面
	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:list")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsYunfeishezhi goodsYunfeishezhi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsYunfeishezhi> page = goodsYunfeishezhiService.findPage(new Page<GoodsYunfeishezhi>(request, response), goodsYunfeishezhi); 
		model.addAttribute("page", page);
		return "modules/goodsyunfeishezhi/goodsYunfeishezhiList";
	}

	/**
	 * 查看，增加，编辑运费管理表单页面
	 */
	@RequiresPermissions(value={"goodsyunfeishezhi:goodsYunfeishezhi:view","goodsyunfeishezhi:goodsYunfeishezhi:add","goodsyunfeishezhi:goodsYunfeishezhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsYunfeishezhi goodsYunfeishezhi, Model model) {
		List<GoodsYunfeishezhi> list = goodsYunfeishezhiService.findList(goodsYunfeishezhi);
		goodsYunfeishezhi = list.get(0);
		model.addAttribute("goodsYunfeishezhi", goodsYunfeishezhi);
		return "modules/goodsyunfeishezhi/goodsYunfeishezhiForm";
	}

	/**
	 * 保存运费管理
	 */
	@RequiresPermissions(value={"goodsyunfeishezhi:goodsYunfeishezhi:add","goodsyunfeishezhi:goodsYunfeishezhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(GoodsYunfeishezhi goodsYunfeishezhi, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, goodsYunfeishezhi)){
			return form(goodsYunfeishezhi, model);
		}
		if(!goodsYunfeishezhi.getIsNewRecord()){//编辑表单保存
			GoodsYunfeishezhi t = goodsYunfeishezhiService.get(goodsYunfeishezhi.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(goodsYunfeishezhi, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			goodsYunfeishezhiService.save(t);//保存
		}else{//新增表单保存
			goodsYunfeishezhiService.save(goodsYunfeishezhi);//保存
		}
		addMessage(redirectAttributes, "保存运费管理成功");
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/form?repage";
	}
	
	/**
	 * 删除运费管理
	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:del")
	@RequestMapping(value = "delete")
	public String delete(GoodsYunfeishezhi goodsYunfeishezhi, RedirectAttributes redirectAttributes) {
		goodsYunfeishezhiService.delete(goodsYunfeishezhi);
		addMessage(redirectAttributes, "删除运费管理成功");
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/?repage";
	}
	
	/**
	 * 批量删除运费管理
	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsYunfeishezhiService.delete(goodsYunfeishezhiService.get(id));
		}
		addMessage(redirectAttributes, "删除运费管理成功");
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(GoodsYunfeishezhi goodsYunfeishezhi, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运费管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsYunfeishezhi> page = goodsYunfeishezhiService.findPage(new Page<GoodsYunfeishezhi>(request, response, -1), goodsYunfeishezhi);
    		new ExportExcel("运费管理", GoodsYunfeishezhi.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出运费管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsYunfeishezhi> list = ei.getDataList(GoodsYunfeishezhi.class);
			for (GoodsYunfeishezhi goodsYunfeishezhi : list){
				try{
					goodsYunfeishezhiService.save(goodsYunfeishezhi);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条运费管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条运费管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入运费管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/?repage";
    }
	
	/**
	 * 下载导入运费管理数据模板
	 */
	@RequiresPermissions("goodsyunfeishezhi:goodsYunfeishezhi:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "运费管理数据导入模板.xlsx";
    		List<GoodsYunfeishezhi> list = Lists.newArrayList(); 
    		new ExportExcel("运费管理数据", GoodsYunfeishezhi.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsyunfeishezhi/goodsYunfeishezhi/?repage";
    }
	
	
	

}