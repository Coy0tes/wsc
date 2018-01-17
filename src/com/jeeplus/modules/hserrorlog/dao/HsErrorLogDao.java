/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.hserrorlog.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.hserrorlog.entity.HsErrorLog;

/**
 * 系统日志DAO接口
 * @author mxc
 * @version 2017-05-28
 */
@MyBatisDao
public interface HsErrorLogDao extends CrudDao<HsErrorLog> {

	
}