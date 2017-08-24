package com.tessoft.karaoke;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController{
	
	protected static Logger logger = Logger.getLogger(UserController.class.getName());
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/user/guestLogin.do")
	public @ResponseBody APIResponse guestLogin(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap tempUser = UserBiz.getInstance(sqlSession).createTempUser(param);
			
			HashMap info = new HashMap();
			
			info.put("tempUser", tempUser);
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("Guest 로그인중 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
}