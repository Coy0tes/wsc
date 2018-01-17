/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.member.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.member.entity.Member;

/**
 * 会员DAO接口
 * @author mxc
 * @version 2017-05-15
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {

	void updateYue(Member member);

	void updateYongjin(Member m);

	void jineSave(Member m);

}