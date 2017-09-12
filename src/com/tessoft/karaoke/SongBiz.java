package com.tessoft.karaoke;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class SongBiz extends CommonBiz {

	private static SongBiz songBiz = null;

	private SongBiz( SqlSession sqlSession )
	{
		super(sqlSession);
	}
	
	public static SongBiz getInstance( SqlSession sqlSession )
	{
		if ( SongBiz.songBiz == null )
			SongBiz.songBiz = new SongBiz( sqlSession );

		return SongBiz.songBiz;
	}
	
	public HashMap getSongDetail(HashMap param)
	{
		HashMap songInfo = sqlSession.selectOne("com.tessoft.karaoke.song.getSongDetail", param);
		return songInfo;
	}
	
	public List<HashMap> getPopularList(HashMap param)
	{
		List<HashMap> songList = sqlSession.selectList("com.tessoft.karaoke.song.getPopularList", param);
		return songList;
	}
	
	public int insertSongItem(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.song.insertSongItem", param );
		return dbResult;
	}
	
	public HashMap selectSongByVideoID(HashMap param)
	{
		HashMap song = sqlSession.selectOne("com.tessoft.karaoke.song.selectSongByVideoID", param);
		return song;
	}
	
	public int updateThumbnailURL(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.song.updateThumbnailURL", param );
		return dbResult;
	}
	
	public int insertSongPlayHistory(HashMap param) {
		// TODO Auto-generated method stub
		int dbResult = sqlSession.insert("com.tessoft.karaoke.song.insertSongPlayHistory", param );
		return dbResult;
	}
	
	public List<HashMap> getSongByKeyword(HashMap param)
	{
		List<HashMap> songList = sqlSession.selectList("com.tessoft.karaoke.song.getSongByKeyword", param);
		return songList;
	}
}
