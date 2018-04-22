package com.tessoft.karaoke;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		List<HashMap> workers = sqlSession.selectList("com.tessoft.alba.selectAllWorkers" );
		model.addAttribute("workers", workers);
		return new ModelAndView("alba/index", model);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/getWorkSeq.do")
	public @ResponseBody APIResponse getWorkSeq(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();

		try
		{
			HashMap hash = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			sqlSession.insert("com.tessoft.alba.insertWorkHistory", hash);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c1 = Calendar.getInstance();
	        String strToday = sdf.format(c1.getTime());
	        hash.put("workDate", strToday);
	        
			response.setData(hash);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("데이터 전송 도중 오류가 발생했습니다.\r\n다시 시도해 주십시오.");
			logger.error( ex );
		}

		return response;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/getEtcStatus.do")
	public @ResponseBody APIResponse getEtcStatus(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			List<HashMap> roomList = sqlSession.selectList("com.tessoft.alba.selectEtcStatus" );
			
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
	@RequestMapping( value ="/alba/updateRoomStatus.do")
	public @ResponseBody APIResponse updateRoomStatus(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap hash = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			if ( hash.containsKey("workSeq") && !"".equals(hash.get("workSeq"))) {
				sqlSession.delete("com.tessoft.alba.deleteRoomStatus", hash);
				
				if ("Y".equals(hash.get("cleanYN"))) {
					sqlSession.insert("com.tessoft.alba.updateRoomStatus", hash);	
				}
			}
			else throw new Exception("workSeq 가 올바르지 않습니다.");
			
			HashMap info = new HashMap();
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg(ex.getMessage());
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/updateEtcStatus.do")
	public @ResponseBody APIResponse updateEtcStatus(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap hash = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			if ( hash.containsKey("workSeq") && !"".equals(hash.get("workSeq"))) {
				sqlSession.delete("com.tessoft.alba.deleteEtcStatus", hash);
				
				if ("Y".equals(hash.get("cleanYN"))) {
					sqlSession.insert("com.tessoft.alba.updateEtcStatus", hash);	
				}
			}
			else throw new Exception("workSeq 가 올바르지 않습니다.");
			
			HashMap info = new HashMap();
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg(ex.getMessage());
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/updateMoneyHistory.do")
	public @ResponseBody APIResponse updateMoneyHistory(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap hash = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			if ( hash.containsKey("workSeq") && !"".equals(hash.get("workSeq"))) {
				sqlSession.delete("com.tessoft.alba.deleteMoneyHistory", hash);
				sqlSession.insert("com.tessoft.alba.updateMoneyHistory", hash);	
			}
			else throw new Exception("workSeq 가 올바르지 않습니다.");
			
			HashMap info = new HashMap();
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg(ex.getMessage());
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/alba/finishWork.do")
	public @ResponseBody APIResponse finishWork(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap hash = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			if ( hash.containsKey("workSeq") && !"".equals(hash.get("workSeq"))) {
				sqlSession.update("com.tessoft.alba.updateWorkHistory", hash);	
			}
			else throw new Exception("workSeq 가 올바르지 않습니다.");
			
			HashMap info = new HashMap();
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg(ex.getMessage());
			logger.error( ex );
		}
		
		return response;
	}
}