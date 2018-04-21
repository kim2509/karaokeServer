package com.tessoft.karaoke;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlbaController extends BaseController{
	
	protected static Logger logger = Logger.getLogger(AlbaController.class.getName());
	
	@SuppressWarnings({ "unused", "unused", "rawtypes", "unchecked" })
	@RequestMapping( value ="/alba/index.do")
	public ModelAndView index ( HttpServletRequest request, HttpServletResponse response , ModelMap model ) throws IOException
	{
		return new ModelAndView("alba/index", model);
	}
	
	@SuppressWarnings({ "unused", "unused", "rawtypes", "unchecked" })
	@RequestMapping( value ="/alba/room.do")
	public ModelAndView room ( HttpServletRequest request, HttpServletResponse response , ModelMap model ) throws IOException
	{
		return new ModelAndView("alba/room", model);
	}
	
	@SuppressWarnings({ "unused", "unused", "rawtypes", "unchecked" })
	@RequestMapping( value ="/alba/cash.do")
	public ModelAndView cash ( HttpServletRequest request, HttpServletResponse response , ModelMap model ) throws IOException
	{
		return new ModelAndView("alba/cash", model);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/getAllWorkers.do")
	public @ResponseBody APIResponse guestLogin(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			List<HashMap> workers = sqlSession.selectList("com.tessoft.alba.selectAllWorkers" );
			
			HashMap info = new HashMap();
			
			info.put("data", workers);
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("근무자 조회도중 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/getRoomList.do")
	public @ResponseBody APIResponse getRoomList(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			List<HashMap> roomList = sqlSession.selectList("com.tessoft.alba.selectRoomList" );
			
			HashMap info = new HashMap();
			
			info.put("data", roomList);
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("근무자 조회도중 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/getRoomStatus.do")
	public @ResponseBody APIResponse getRoomStatus(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			List<HashMap> roomList = sqlSession.selectList("com.tessoft.alba.selectRoomStatus" );
			
			HashMap info = new HashMap();
			
			info.put("data", roomList);
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("근무자 조회도중 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
}