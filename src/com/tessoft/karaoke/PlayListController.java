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
public class PlayListController extends BaseController{
	
	protected static Logger logger = Logger.getLogger(PlayListController.class.getName());
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping( value ="/playlist/list.do")
	public ModelAndView list ( HttpServletRequest request )
	{
		List<HashMap> playlist = PlayListBiz.getInstance(sqlSession).getAllPlayList();
		return new ModelAndView("playlist/list");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlist/popularList.do")
	public @ResponseBody APIResponse popularList(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap info = new HashMap();
			
			List<HashMap> playlist = PlayListBiz.getInstance(sqlSession).getAllPlayList();
			info.put("popularList", playlist);
			
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
	@RequestMapping( value ="/playlist/myList.do")
	public @ResponseBody APIResponse myList(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap info = new HashMap();
			
			List<HashMap> myPlayList = PlayListBiz.getInstance(sqlSession).getPlayListByUser(param);
			
			if ( myPlayList.size() < 1 )
			{
				param.put("Name", "기본");
				PlayListBiz.getInstance(sqlSession).createNewPlayList(param);
				myPlayList.add(param);
			}

			info.put("myList", myPlayList);
			
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
	@RequestMapping( value ="/playlist/mainInfo.do")
	public @ResponseBody APIResponse mainInfo(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap info = new HashMap();
			/*
			List<HashMap> playlist = PlayListBiz.getInstance(sqlSession).getAllPlayList();
			info.put("popularList", playlist);
			
			List<HashMap> myPlayList = PlayListBiz.getInstance(sqlSession).getPlayListByUser(param);
			
			if ( myPlayList.size() < 1 )
			{
				param.put("Name", "기본");
				PlayListBiz.getInstance(sqlSession).createNewPlayList(param);
				myPlayList.add(param);
			}

			info.put("myPlayList", myPlayList);
			*/
			
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
	@RequestMapping( value ="/playlist/addSong.do")
	public @ResponseBody APIResponse addSong(HttpServletRequest request, @RequestBody String bodyString)
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
				PlayListBiz.getInstance(sqlSession).addSong(param);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlist/addSongs.do")
	public @ResponseBody APIResponse addSongs(HttpServletRequest request, @RequestBody String bodyString)
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
				List<HashMap> songList = (List<HashMap>) param.get("songList");
				
				for ( int i = 0; i < songList.size(); i++ )
				{
					HashMap song = songList.get(i);
					song.put("playListNo", Util.getStringFromHash(param, "playListNo"));
					PlayListBiz.getInstance(sqlSession).addSong( song );	
				}
			}
			
			HashMap info = new HashMap();
			
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
	@RequestMapping( value ="/playlist/playList.do")
	public @ResponseBody APIResponse playList(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			HashMap info = new HashMap();
			
			if ( Util.isEmptyForKey(param, "playListNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				List<HashMap> songList = PlayListBiz.getInstance(sqlSession).getPlayListByPlayList(param);
				info.put("songList", songList);
			}

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
	@RequestMapping( value ="/playlist/searchMySong.do")
	public @ResponseBody APIResponse searchMySong(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			HashMap info = new HashMap();
			
			if ( Util.isEmptyForKey(param, "playListNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				List<HashMap> songList = PlayListBiz.getInstance(sqlSession).searchMySong(param);
				info.put("songList", songList);
			}

			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("my song list 를 읽어오는 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	String YoutubeApiKey = "AIzaSyD0WWUaXGrcaV7DFAkaf2zyr11-q-iPx4w";
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping( value ="/playlist/searchSong.do")
	public @ResponseBody APIResponse searchSong(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});

			String title = Util.getStringFromHash(param, "title");
			
			param.put("keyword", title );
			
			PlayListBiz.getInstance(sqlSession).deleteExpiredSearchHistoryByKeyword(param);
			List<HashMap> searchHistory = PlayListBiz.getInstance(sqlSession).getSearchHistoryByKeyword(param);
			
			StringBuilder resultText = new StringBuilder();
			
			if ( searchHistory != null && searchHistory.size() > 0 )
			{
				String tmp = Util.getStringFromHash( searchHistory.get(0), "result");
				resultText.append(tmp);
			}
			else
			{
				String url = "https://www.googleapis.com/youtube/v3/search?part=id,snippet&q=" + URLEncoder.encode( title , "utf8") +
	                    "&type=video&key=" + YoutubeApiKey;
				
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet req = new HttpGet(url);
				// add request header
				HttpResponse res = null;

				res = client.execute(req);
				
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(res.getEntity().getContent(), "utf-8"));

				String line = "";
				while ((line = rd.readLine()) != null) {
					resultText.append(line);
				}
				
				param.put("result", resultText.toString());
				PlayListBiz.getInstance(sqlSession).insertSearch(param);
			}
			
			HashMap resultItem = mapper.readValue(resultText.toString(), new TypeReference<HashMap>(){});
			
			List<HashMap> items = (List<HashMap>) resultItem.get("items");
            
			HashMap info = new HashMap();
			info.put("items", items );
			
			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("youtube 검색도중 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlist/updateItem.do")
	public @ResponseBody APIResponse updateItem(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			HashMap info = new HashMap();
			
			if ( Util.isEmptyForKey(param, "playListNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				String type = Util.getStringFromHash(param, "type");
				if ("1".equals(type)) 
					param.put("videoID", Util.getStringFromHash(param, "videoID1"));
				else
					param.put("videoID", Util.getStringFromHash(param, "videoID2"));
				
				HashMap song = PlayListBiz.getInstance(sqlSession).selectSongByVideoID(param);
				if ( song == null )
					PlayListBiz.getInstance(sqlSession).insertSongItem(param);
				else
					param.put("songNo", Util.getStringFromHash(song, "songNo"));
				
				PlayListBiz.getInstance(sqlSession).insertSongPlayHistory(param);
				
				if ("1".equals(type))
					param.put("songNo1", Util.getStringFromHash(param, "songNo"));
				else
					param.put("songNo2", Util.getStringFromHash(param, "songNo"));
				
				PlayListBiz.getInstance(sqlSession).updatePlayListItemType(param);
			}

			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("updateItem 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping( value ="/playlist/detail.do")
	public @ResponseBody APIResponse detail(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap data = new HashMap();
			
			HashMap itemInfo = PlayListBiz.getInstance(sqlSession).getPlayListDetail(param);
			
			data.put("itemInfo", itemInfo );
			
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
	@RequestMapping( value ="/playlist/updatePlayListItem.do")
	public @ResponseBody APIResponse updatePlayListItem(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			
			HashMap data = new HashMap();
			
			int dbResult = PlayListBiz.getInstance(sqlSession).updatePlayListItem(param);
			
			data.put("dbResult", String.valueOf( dbResult ) );
			
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
	@RequestMapping( value ="/playlist/deleteItem.do")
	public @ResponseBody APIResponse deleteItem(HttpServletRequest request, @RequestBody String bodyString)
	{
		APIResponse response = new APIResponse();
		
		try
		{
			HashMap param = mapper.readValue(bodyString, new TypeReference<HashMap>(){});
			HashMap info = new HashMap();
			
			if ( Util.isEmptyForKey(param, "playListNo") )
			{
				response.setResCode( ErrorCode.INVALID_INPUT );
				response.setResMsg("요청값이 올바르지 않습니다.");
			}
			else
			{
				PlayListBiz.getInstance(sqlSession).deletePlayListItem(param);
				List<HashMap> songList = PlayListBiz.getInstance(sqlSession).getPlayListByPlayList(param);
				info.put("songList", songList);
			}

			response.setData(info);
		}
		catch( Exception ex )
		{
			response.setResCode( ErrorCode.UNKNOWN_ERROR );
			response.setResMsg("항목을 삭제하는 도중에 오류가 발생했습니다.");
			logger.error( ex );
		}
		
		return response;
	}
}