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
	
	public List<HashMap> searchPlayListSong(HashMap param)
	{
		List<HashMap> songList = sqlSession.selectList("com.tessoft.karaoke.playlist.searchPlayListSong", param);
		return songList;
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
	
	public HashMap getPlayListDetail(HashMap param)
	{
		HashMap playListInfo = sqlSession.selectOne("com.tessoft.karaoke.playlist.getPlayListDetail", param);
		return playListInfo;
	}
	
	public int updatePlaylistShare(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.updatePlaylistShare", param );
		return dbResult;
	}
	
	public int updatePlaylist(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.update("com.tessoft.karaoke.playlist.updatePlaylist", param );
		return dbResult;
	}
	
	public int deletePlayListItem(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.delete("com.tessoft.karaoke.playlist.deletePlayListItem", param );
		return dbResult;
	}
	
	public int deletePlayList(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.delete("com.tessoft.karaoke.playlist.deletePlayList", param );
		return dbResult;
	}
}
