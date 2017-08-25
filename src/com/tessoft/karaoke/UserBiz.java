package com.tessoft.karaoke;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class UserBiz extends CommonBiz {

	private static UserBiz userBiz = null;

	private UserBiz( SqlSession sqlSession )
	{
		super(sqlSession);
	}
	
	public static UserBiz getInstance( SqlSession sqlSession )
	{
		if ( UserBiz.userBiz == null )
			UserBiz.userBiz = new UserBiz( sqlSession );

		return UserBiz.userBiz;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public HashMap createTempUser(HashMap param) {
		
		if ( Util.isEmptyForKey(param, "tempUserNo") )
		{
			sqlSession.insert("com.tessoft.karaoke.user.insertTempUser", param );	
		}
		else
		{
			sqlSession.update("com.tessoft.karaoke.user.updateTempUser", param );
		}
		
		return param;
	}
}
