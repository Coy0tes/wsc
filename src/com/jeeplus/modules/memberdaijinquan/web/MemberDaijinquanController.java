/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.memberdaijinquan.web;

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
import com.jeeplus.modules.memberdaijinquan.entity.MemberDaijinquan;
import com.jeeplus.modules.memberdaijinquan.service.MemberDaijinquanService;

/**
 * 代金券使用Controller
 * @author mxc
 * @version 2017-05-15
 */
@Controller
@RequestMapping(value = "${adminPath}/memberdaijinquan/memberDaijinquan")
public class MemberDaijinquanController extends BaseController {

	@Autowired
	private MemberDaijinquanService memberDaijinquanService;
	
	@ModelAttribute
	public MemberDaijinquan get(@RequestParam(required=false) String id) {
		MemberDaijinquan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberDaijinquanService.get(id);
		}
		if (entity == null){
			entity = new MemberDaijinquan();
		}
		return entity;
	}
	
	/**
	 * 代金券管理列表页面
	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:list")
	@RequestMapping(value = {"list", ""})
	public String list(MemberDaijinquan memberDaijinquan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberDaijinquan> page = memberDaijinquanService.findPage(new Page<MemberDaijinquan>(request, response), memberDaijinquan); 
		model.addAttribute("page", page);
		return "modules/memberdaijinquan/memberDaijinquanList";
	}

	/**
	 * 查看，增加，编辑代金券管理表单页面
	 */
	@RequiresPermissions(value={"memberdaijinquan:memberDaijinquan:view","memberdaijinquan:memberDaijinquan:add","memberdaijinquan:memberDaijinquan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberDaijinquan memberDaijinquan, Model model) {
		model.addAttribute("memberDaijinquan", memberDaijinquan);
		return "modules/memberdaijinquan/memberDaijinquanForm";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "sendAllform")
	public String sendAllform(MemberDaijinquan memberDaijinquan, Model model) {
		model.addAttribute("memberDaijinquan", memberDaijinquan);
		return "modules/memberdaijinquan/memberDaijinquanSendAllForm";
	}

	/**
	 * 保存代金券管理
	 */
	@RequiresPermissions(value={"memberdaijinquan:memberDaijinquan:add","memberdaijinquan:memberDaijinquan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberDaijinquan memberDaijinquan, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberDaijinquan)){
			return form(memberDaijinquan, model);
		}
		if(!memberDaijinquan.getIsNewRecord()){//编辑表单保存
			MemberDaijinquan t = memberDaijinquanService.get(memberDaijinquan.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(memberDaijinquan, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			memberDaijinquanService.save(t);//保存
		}else{//新增表单保存
			memberDaijinquanService.save(memberDaijinquan);//保存
		}
		addMessage(redirectAttributes, "保存代金券管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
	}
	
	/**
	 * 删除代金券管理
	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:del")
	@RequestMapping(value = "delete")
	public String delete(MemberDaijinquan memberDaijinquan, RedirectAttributes redirectAttributes) {
		memberDaijinquanService.delete(memberDaijinquan);
		addMessage(redirectAttributes, "删除代金券管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
	}
	
	/**
	 * 批量发放代金券
	 * @param memberDaijinquan
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "sendAll")
	public String sendAll(MemberDaijinquan memberDaijinquan, RedirectAttributes redirectAttributes) {
		memberDaijinquanService.sendAll(memberDaijinquan);
		addMessage(redirectAttributes, "批量发放成功");
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
	}
	
	/**
	 * 批量删除代金券管理
	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberDaijinquanService.delete(memberDaijinquanService.get(id));
		}
		addMessage(redirectAttributes, "删除代金券管理成功");
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MemberDaijinquan memberDaijinquan, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "代金券管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberDaijinquan> page = memberDaijinquanService.findPage(new Page<MemberDaijinquan>(request, response, -1), memberDaijinquan);
    		new ExportExcel("代金券管理", MemberDaijinquan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出代金券管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberDaijinquan> list = ei.getDataList(MemberDaijinquan.class);
			for (MemberDaijinquan memberDaijinquan : list){
				try{
					memberDaijinquanService.save(memberDaijinquan);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条代金券管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条代金券管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入代金券管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
    }
	
	/**
	 * 下载导入代金券管理数据模板
	 */
	@RequiresPermissions("memberdaijinquan:memberDaijinquan:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "代金券管理数据导入模板.xlsx";
    		List<MemberDaijinquan> list = Lists.newArrayList(); 
    		new ExportExcel("代金券管理数据", MemberDaijinquan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/memberdaijinquan/memberDaijinquan/?repage";
    }
	
	
	

}