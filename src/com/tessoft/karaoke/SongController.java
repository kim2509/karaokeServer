package com.tessoft.karaoke;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SongController extends BaseController{
	
	protected static Logger logger = Logger.getLogger(SongController.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/song/popularList.do")
	public @ResponseBody APIResponse popularList(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap info = new HashMap();
			
			List<HashMap> songList = SongBiz.getInstance(sqlSession).getPopularList(param);
			info.put("popularList", songList);
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("Play list 를 읽어오는 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/song/detail.do")
	public @ResponseBody APIResponse detail(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap data = new HashMap();
			
			HashMap songInfo = SongBiz.getInstance(sqlSession).getSongDetail(param);
			
			data.put("songInfo", songInfo );
			
			response.setData(data);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("노래정보를 읽어오는 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/song/updatePlayHistory.do")
	public @ResponseBody APIResponse updatePlayHistory(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			HashMap info = new HashMap();
			
			if ( Util.isEmptyForKey(param, "songNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				SongBiz.getInstance(sqlSession).insertSongPlayHistory(param);				
			}

			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("updatePlayHistory 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
}