package com.tessoft.karaoke;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class CommonBiz {

	protected static Logger logger = Logger.getLogger(CommonBiz.class.getName());

	protected ObjectMapper mapper = null;
	protected SqlSession sqlSession = null;
	
	public CommonBiz( SqlSession sqlSession )
	{
		mapper = new ObjectMapper();
		this.sqlSession = sqlSession;
	}
}
