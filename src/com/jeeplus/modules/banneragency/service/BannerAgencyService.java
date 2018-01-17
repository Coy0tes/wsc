/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.banneragency.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.banneragency.dao.BannerAgencyDao;
import com.jeeplus.modules.banneragency.entity.BannerAgency;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 商城轮播图管理Service
 * @author zhaoliangdong
 * @version 2017-05-25
 */
@Service
@Transactional(readOnly = true)
public class BannerAgencyService extends CrudService<BannerAgencyDao, BannerAgency> {

	public BannerAgency get(String id) {
		return super.get(id);
	}
	
	public List<BannerAgency> findList(BannerAgency bannerAgency) {
		return super.findList(bannerAgency);
	}
	
	public Page<BannerAgency> findPage(Page<BannerAgency> page, BannerAgency bannerAgency) {
		return super.findPage(page, bannerAgency);
	}
	
	@Transactional(readOnly = false)
	public void save(BannerAgency bannerAgency) {
		super.save(bannerAgency);
	}
	
	@Transactional(readOnly = false)
	public void delete(BannerAgency bannerAgency) {
		super.delete(bannerAgency);
	}
	
	
	
	
}