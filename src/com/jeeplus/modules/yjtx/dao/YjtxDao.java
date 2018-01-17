/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.yjtx.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.yjtx.entity.Yjtx;

/**
 * 佣金提现申请DAO接口
 * @author zhaoliangdong
 * @version 2017-06-10
 */
@MyBatisDao
public interface YjtxDao extends CrudDao<Yjtx> {

	void updateShtg(Yjtx yjtx);

	void updateShbtg(Yjtx yjtx);

}