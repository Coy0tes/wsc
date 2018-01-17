/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yjtx.web;

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
import com.jeeplus.modules.yjtx.entity.Yjtx;
import com.jeeplus.modules.yjtx.service.YjtxService;

/**
 * 佣金提现申请Controller
 * @author zhaoliangdong
 * @version 2017-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/yjtx/yjtx")
public class YjtxController extends BaseController {

	@Autowired
	private YjtxService yjtxService;
	
	@ModelAttribute
	public Yjtx get(@RequestParam(required=false) String id) {
		Yjtx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = yjtxService.get(id);
		}
		if (entity == null){
			entity = new Yjtx();
		}
		return entity;
	}
	
	/**
	 * 提现列表页面
	 */
	@RequiresPermissions("yjtx:yjtx:list")
	@RequestMapping(value = {"list", ""})
	public String list(Yjtx yjtx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Yjtx> page = yjtxService.findPage(new Page<Yjtx>(request, response), yjtx); 
		model.addAttribute("page", page);
		return "modules/yjtx/yjtxList";
	}

	/**
	 * 查看，增加，编辑提现表单页面
	 */
	@RequiresPermissions(value={"yjtx:yjtx:view","yjtx:yjtx:add","yjtx:yjtx:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Yjtx yjtx, Model model) {
		model.addAttribute("yjtx", yjtx);
		return "modules/yjtx/yjtxForm";
	}

	/**
	 * 保存提现
	 */
	@RequiresPermissions(value={"yjtx:yjtx:add","yjtx:yjtx:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Yjtx yjtx, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, yjtx)){
			return form(yjtx, model);
		}
		if(!yjtx.getIsNewRecord()){//编辑表单保存
			Yjtx t = yjtxService.get(yjtx.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(yjtx, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			yjtxService.save(t);//保存
		}else{//新增表单保存
			yjtxService.save(yjtx);//保存
		}
		addMessage(redirectAttributes, "保存提现成功");
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
	}
	
	/**
	 * 删除提现
	 */
	@RequiresPermissions("yjtx:yjtx:del")
	@RequestMapping(value = "delete")
	public String delete(Yjtx yjtx, RedirectAttributes redirectAttributes) {
		yjtxService.delete(yjtx);
		addMessage(redirectAttributes, "删除提现成功");
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
	}
	
	/**
	 * 审核通过
	 * @param yjtx
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("yjtx:yjtx:view")
	@RequestMapping(value = "shtg")
	public String shtg(Yjtx yjtx, RedirectAttributes redirectAttributes) {
		yjtxService.shtg(yjtx);
		addMessage(redirectAttributes, "审核通过完毕");
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
	}
	
	@RequiresPermissions("yjtx:yjtx:view")
	@RequestMapping(value = "shbtg")
	public String shbtg(Yjtx yjtx, RedirectAttributes redirectAttributes) {
		yjtxService.shbtg(yjtx);
		addMessage(redirectAttributes, "审核拒绝完毕");
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
	}
	
	/**
	 * 批量删除提现
	 */
	@RequiresPermissions("yjtx:yjtx:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			yjtxService.delete(yjtxService.get(id));
		}
		addMessage(redirectAttributes, "删除提现成功");
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("yjtx:yjtx:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Yjtx yjtx, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "提现"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Yjtx> page = yjtxService.findPage(new Page<Yjtx>(request, response, -1), yjtx);
    		new ExportExcel("提现", Yjtx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出提现记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("yjtx:yjtx:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Yjtx> list = ei.getDataList(Yjtx.class);
			for (Yjtx yjtx : list){
				try{
					yjtxService.save(yjtx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条提现记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条提现记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入提现失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
    }
	
	/**
	 * 下载导入提现数据模板
	 */
	@RequiresPermissions("yjtx:yjtx:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "提现数据导入模板.xlsx";
    		List<Yjtx> list = Lists.newArrayList(); 
    		new ExportExcel("提现数据", Yjtx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/yjtx/yjtx/?repage";
    }
	
	
	

}