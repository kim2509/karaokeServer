package com.tessoft.karaoke;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class PlayListBiz extends CommonBiz {

	private static PlayListBiz playListBiz = null;

	private PlayListBiz( SqlSession sqlSession )
	{
		super(sqlSession);
	}
	
	public static PlayListBiz getInstance( SqlSession sqlSession )
	{
		if ( PlayListBiz.playListBiz == null )
			PlayListBiz.playListBiz = new PlayListBiz( sqlSession );

		return PlayListBiz.playListBiz;
	}
	
	public List<HashMap> getAllPlayList()
	{
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.getAllPlayList");
		return playList;
	}

	public List<HashMap> getPlayListByUser(HashMap param)
	{
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.getPlayListByUser", param);
		return playList;
	}
	
	public List<HashMap> getPlayListByPlayList(HashMap param)
	{
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.getPlayListSongs", param);
		return playList;
	}
	
	public HashMap createNewPlayList(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.insertPlayList", param );
		return param;
	}
	
	public HashMap addSong(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.insertSong", param );
		return param;
	}
	
	public HashMap insertSearch(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.insertSearch", param );
		return param;
	}
	
	public HashMap deleteExpiredSearchHistoryByKeyword(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.delete("com.tessoft.karaoke.deleteExpiredSearchHistoryByKeyword", param );
		return param;
	}
	
	public List<HashMap> getSearchHistoryByKeyword(HashMap param) {
		// TODO Auto-generated method stub
		List<HashMap> data = sqlSession.selectList("com.tessoft.karaoke.getSearchHistoryByKeyword", param );
		return data;
	}
	
	public List<HashMap> searchMySong(HashMap param)
	{
		List<HashMap> songList = sqlSession.selectList("com.tessoft.karaoke.searchMySong", param);
		return songList;
	}
	
	public HashMap searchSong(HashMap param)
	{
		HashMap song = sqlSession.selectOne("com.tessoft.karaoke.searchSong", param);
		return song;
	}
}
