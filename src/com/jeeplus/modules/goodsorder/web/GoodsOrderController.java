/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.goodsorder.web;

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
import com.jeeplus.modules.goodsorder.entity.GoodsOrder;
import com.jeeplus.modules.goodsorder.service.GoodsOrderService;

/**
 * 订单管理Controller
 * @author mxc
 * @version 2017-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/goodsorder/goodsOrder")
public class GoodsOrderController extends BaseController {

	@Autowired
	private GoodsOrderService goodsOrderService;
	
	@ModelAttribute
	public GoodsOrder get(@RequestParam(required=false) String id) {
		GoodsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsOrderService.get(id);
		}
		if (entity == null){
			entity = new GoodsOrder();
		}
		return entity;
	}
	
	/**
	 * 订单管理列表页面
	 */
	@RequiresPermissions("goodsorder:goodsOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsOrder goodsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsOrder> page = goodsOrderService.findPage(new Page<GoodsOrder>(request, response), goodsOrder); 
		model.addAttribute("page", page);
		return "modules/goodsorder/goodsOrderList";
	}

	/**
	 * 查看，增加，编辑订单管理表单页面
	 */
	@RequiresPermissions(value={"goodsorder:goodsOrder:view","goodsorder:goodsOrder:add","goodsorder:goodsOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsOrder goodsOrder, Model model) {
		model.addAttribute("goodsOrder", goodsOrder);
		return "modules/goodsorder/goodsOrderForm";
	}
	
	/**
	 * 新增 添加
	 * 物流单号
	 */
	@RequiresPermissions(value={"member:member:view","member:member:add","member:member:edit"},logical=Logical.OR)
	@RequestMapping(value = "formWuliudanhao")
	public String fromWuliudanhao(GoodsOrder goodsOrder, Model model){
		List<GoodsOrder> list = goodsOrderService.findList(goodsOrder);
		for(GoodsOrder go:list){
			if(go.getId().equals(goodsOrder.getId())){
				goodsOrder = go;
				break;
			}
		}
		model.addAttribute("goodsOrder", goodsOrder);
		return "modules/goodsorder/goodsOrderDanhaoForm";
	}
	
	
	/**
	 * 保存订单管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save")
	public String save(GoodsOrder goodsOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, goodsOrder)){
			return form(goodsOrder, model);
		}
		if(!goodsOrder.getIsNewRecord()){//编辑表单保存
			GoodsOrder t = goodsOrderService.get(goodsOrder.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(goodsOrder, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setStatus("待收货");
			goodsOrderService.save(t);//保存
		}else{//新增表单保存
			goodsOrderService.save(goodsOrder);//保存
		}
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
	}
	
	/**
	 * 删除订单管理
	 */
	@RequiresPermissions("goodsorder:goodsOrder:del")
	@RequestMapping(value = "delete")
	public String delete(GoodsOrder goodsOrder, RedirectAttributes redirectAttributes) {
		goodsOrderService.delete(goodsOrder);
		addMessage(redirectAttributes, "删除订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
	}
	
	/**
	 * 批量删除订单管理
	 */
	@RequiresPermissions("goodsorder:goodsOrder:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsOrderService.delete(goodsOrderService.get(id));
		}
		addMessage(redirectAttributes, "删除订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("goodsorder:goodsOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(GoodsOrder goodsOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsOrder> page = goodsOrderService.findPage(new Page<GoodsOrder>(request, response, -1), goodsOrder);
    		new ExportExcel("订单管理", GoodsOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出订单管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("goodsorder:goodsOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsOrder> list = ei.getDataList(GoodsOrder.class);
			for (GoodsOrder goodsOrder : list){
				try{
					goodsOrderService.save(goodsOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
    }
	
	/**
	 * 下载导入订单管理数据模板
	 */
	@RequiresPermissions("goodsorder:goodsOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单管理数据导入模板.xlsx";
    		List<GoodsOrder> list = Lists.newArrayList(); 
    		new ExportExcel("订单管理数据", GoodsOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/goodsorder/goodsOrder/?repage";
    }
	
	
	

}