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
}
