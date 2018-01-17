/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberchongzhi.web;

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
import com.jeeplus.modules.memberchongzhi.entity.MemberChongzhi;
import com.jeeplus.modules.memberchongzhi.service.MemberChongzhiService;

/**
 * 充值模块Controller
 * @author mxc
 * @version 2017-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/memberchongzhi/memberChongzhi")
public class MemberChongzhiController extends BaseController {

	@Autowired
	private MemberChongzhiService memberChongzhiService;
	
	@ModelAttribute
	public MemberChongzhi get(@RequestParam(required=false) String id) {
		MemberChongzhi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberChongzhiService.get(id);
		}
		if (entity == null){
			entity = new MemberChongzhi();
		}
		return entity;
	}
	
	/**
	 * 充值管理列表页面
	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:list")
	@RequestMapping(value = {"list", ""})
	public String list(MemberChongzhi memberChongzhi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberChongzhi> page = memberChongzhiService.findPage(new Page<MemberChongzhi>(request, response), memberChongzhi); 
		model.addAttribute("page", page);
		return "modules/memberchongzhi/memberChongzhiList";
	}

	/**
	 * 查看，增加，编辑充值管理表单页面
	 */
	@RequiresPermissions(value={"memberchongzhi:memberChongzhi:view","memberchongzhi:memberChongzhi:add","memberchongzhi:memberChongzhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberChongzhi memberChongzhi, Model model) {
		model.addAttribute("memberChongzhi", memberChongzhi);
		return "modules/memberchongzhi/memberChongzhiForm";
	}

	/**
	 * 保存充值管理
	 */
	@RequiresPermissions(value={"memberchongzhi:memberChongzhi:add","memberchongzhi:memberChongzhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberChongzhi memberChongzhi, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberChongzhi)){
			return form(memberChongzhi, model);
		}
		if(!memberChongzhi.getIsNewRecord()){//编辑表单保存
			MemberChongzhi t = memberChongzhiService.get(memberChongzhi.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(memberChongzhi, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			memberChongzhiService.save(t);//保存
		}else{//新增表单保存
			memberChongzhiService.save(memberChongzhi);//保存
		}
		addMessage(redirectAttributes, "保存充值管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
	}
	
	/**
	 * 删除充值管理
	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:del")
	@RequestMapping(value = "delete")
	public String delete(MemberChongzhi memberChongzhi, RedirectAttributes redirectAttributes) {
		memberChongzhiService.delete(memberChongzhi);
		addMessage(redirectAttributes, "删除充值管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
	}
	
	/**
	 * 批量删除充值管理
	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberChongzhiService.delete(memberChongzhiService.get(id));
		}
		addMessage(redirectAttributes, "删除充值管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MemberChongzhi memberChongzhi, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "充值管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberChongzhi> page = memberChongzhiService.findPage(new Page<MemberChongzhi>(request, response, -1), memberChongzhi);
    		new ExportExcel("充值管理", MemberChongzhi.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出充值管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberChongzhi> list = ei.getDataList(MemberChongzhi.class);
			for (MemberChongzhi memberChongzhi : list){
				try{
					memberChongzhiService.save(memberChongzhi);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条充值管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条充值管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入充值管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
    }
	
	/**
	 * 下载导入充值管理数据模板
	 */
	@RequiresPermissions("memberchongzhi:memberChongzhi:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "充值管理数据导入模板.xlsx";
    		List<MemberChongzhi> list = Lists.newArrayList(); 
    		new ExportExcel("充值管理数据", MemberChongzhi.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberchongzhi/memberChongzhi/?repage";
    }
	
	
	

}