package com.tessoft.karaoke;

import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	@Autowired
	public SqlSession sqlSession;
	
	protected ObjectMapper mapper = null;
	
	public BaseController()
	{
		mapper = new ObjectMapper();
	}
}