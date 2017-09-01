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
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.playlist.getAllPlayList");
		return playList;
	}

	public List<HashMap> getPlayListByUser(HashMap param)
	{
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.playlist.getPlayListByUser", param);
		return playList;
	}
	
	public List<HashMap> getPlayListByPlayList(HashMap param)
	{
		List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.playlist.getPlayListSongs", param);
		return playList;
	}
	
	public HashMap createNewPlayList(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.insertPlayList", param );
		return param;
	}
	
	public HashMap addSong(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.insertSong", param );
		return param;
	}
	
	public HashMap insertSearch(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.insertSearch", param );
		return param;
	}
	
	public HashMap deleteExpiredSearchHistoryByKeyword(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.delete("com.tessoft.karaoke.playlist.deleteExpiredSearchHistoryByKeyword", param );
		return param;
	}
	
	public List<HashMap> getSearchHistoryByKeyword(HashMap param) {
		// TODO Auto-generated method stub
		List<HashMap> data = sqlSession.selectList("com.tessoft.karaoke.playlist.getSearchHistoryByKeyword", param );
		return data;
	}
	
	public List<HashMap> searchMySong(HashMap param)
	{
		List<HashMap> songList = sqlSession.selectList("com.tessoft.karaoke.playlist.searchMySong", param);
		return songList;
	}
	
	public HashMap searchSong(HashMap param)
	{
		HashMap song = sqlSession.selectOne("com.tessoft.karaoke.playlist.searchSong", param);
		return song;
	}
	
	public HashMap selectSongByVideoID(HashMap param)
	{
		HashMap song = sqlSession.selectOne("com.tessoft.karaoke.playlist.selectSongByVideoID", param);
		return song;
	}
	
	public int insertSongItem(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.insertSongItem", param );
		return dbResult;
	}
	
	public int insertSongPlayHistory(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.playlist.insertSongPlayHistory", param );
		return dbResult;
	}
	
	public int updatePlayListItemType(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.updatePlayListItemType", param );
		return dbResult;
	}
	
	public HashMap getPlayListDetail(HashMap param)
	{
		HashMap itemInfo = sqlSession.selectOne("com.tessoft.karaoke.playlist.getPlayListDetail", param);
		return itemInfo;
	}
	
	public int updatePlayListItem(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.updatePlayListItem", param );
		return dbResult;
	}
}
