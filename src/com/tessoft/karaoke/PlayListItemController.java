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
public class PlayListItemController extends BaseController{
	
	protected static Logger logger = Logger.getLogger(PlayListItemController.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlistItem/detail.do")
	public @ResponseBody APIResponse detail(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap data = new HashMap();

			HashMap itemInfo = PlayListItemBiz.getInstance(sqlSession).getPlayListItemDetail(param);
			data.put("itemInfo", itemInfo );
			
			// 변경할 수 있는 재생목록 제공
			List<HashMap> myPlayList = PlayListBiz.getInstance(sqlSession).getPlayListByUser(param);
			
			String playListNo = Util.getStringFromHash(itemInfo, "playListNo");
			
			for ( int i = myPlayList.size() - 1; i >= 0; i-- ) {
				if ( playListNo.equals( Util.getStringFromHash( myPlayList.get(i), "playListNo") ) ) {
					myPlayList.remove(i);
				}
			}
			
			data.put("myPlayList", myPlayList );
			
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
	@RequestMapping( value ="/playlistItem/update.do")
	public @ResponseBody APIResponse updatePlayListItem(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap data = new HashMap();
			
			int dbResult = 0;
			
			if ( Util.isEmptyForKey(param, "mode") || "2".equals( Util.getStringFromHash(param, "mode"))){
				// 아이템 update or 재생목록 변경
				dbResult = PlayListItemBiz.getInstance(sqlSession).updatePlayListItem(param);	
			}
			else {
				// 재생목록 복사
				PlayListItemBiz.getInstance(sqlSession).addSong(param);
			}
			
			data.put("dbResult", String.valueOf( dbResult ) );
			
			response.setData(data);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("노래정보를 저장 하는 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlistItem/add.do")
	public @ResponseBody APIResponse add(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			if ( Util.isEmptyForKey(param, "playListNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				PlayListItemBiz.getInstance(sqlSession).addSong(param);
			}
			
			HashMap info = new HashMap();
			info.put("song", param);
			
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
}