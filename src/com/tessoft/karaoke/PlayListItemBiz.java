package com.tessoft.karaoke;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

public class PlayListItemBiz extends CommonBiz {

	private static PlayListItemBiz playListBiz = null;

	private PlayListItemBiz( SqlSession sqlSession )
	{
		super(sqlSession);
	}
	
	public static PlayListItemBiz getInstance( SqlSession sqlSession )
	{
		if ( PlayListItemBiz.playListBiz == null )
			PlayListItemBiz.playListBiz = new PlayListItemBiz( sqlSession );

		return PlayListItemBiz.playListBiz;
	}
	
	public HashMap getPlayListItemDetail(HashMap param)
	{
		HashMap itemInfo = sqlSession.selectOne("com.tessoft.karaoke.playlist.item.getPlayListItemDetail", param);
		return itemInfo;
	}
	
	public HashMap getItemByTitleNSinger(HashMap param)
	{
		HashMap itemInfo = sqlSession.selectOne("com.tessoft.karaoke.playlist.item.getItemByTitleNSinger", param);
		return itemInfo;
	}
	
	public int updatePlayListItem(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.item.updatePlayListItem", param );
		return dbResult;
	}
	
	public HashMap addSong(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.item.insertSong", param );
		return param;
	}
	
	public int updateVideoIDAsNull(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.item.updateVideoIDAsNull", param );
		return dbResult;
	}
	
	public int updatePlayListItemType(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.item.updatePlayListItemType", param );
		return dbResult;
	}
}
