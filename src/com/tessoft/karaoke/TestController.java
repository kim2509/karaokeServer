package com.tessoft.karaoke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@Autowired
	public SqlSession sqlSession;

	@Autowired
	private Environment env;

	 @Autowired
	public ApplicationContext context;

	protected ObjectMapper mapper = null;
	
	protected static Logger logger = Logger.getLogger(TestController.class.getName());

	public TestController()
	{
		mapper = new ObjectMapper();
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping( value ="/test/test.do")
	public ModelAndView list ( HttpServletRequest request )
	{
		try {
			String abc = "abc";
			if ( 1 == 1 )
			{
				abc = env.getProperty("jdbc.url");
				logger.info( abc );
				
				abc = env.getProperty("mode");
				logger.info( abc );
				
				List<String> files = getResourceFiles("/");
				
				for ( int i = 0; i < files.size(); i++ )
					logger.info( files.get(i) );
				
//				PropertyPlaceholderConfigurer config = (PropertyPlaceholderConfigurer) context.getBean("propertyPlaceholderConfigurer");
				
				List<HashMap> playList = sqlSession.selectList("com.tessoft.karaoke.test.test");
				
				for ( int i = 0; i < playList.size(); i++ )
					logger.info( mapper.writeValueAsString(playList.get(i)) );
			}	
		} 
		catch( Exception ex )
		{
			logger.error(ex);
		}
		
		return new ModelAndView("playlist/list");
	}

	private List<String> getResourceFiles( String path ) throws IOException {
		List<String> filenames = new ArrayList<>();

		try(
				InputStream in = getResourceAsStream( path );
				BufferedReader br = new BufferedReader( new InputStreamReader( in ) ) ) {
			String resource;

			while( (resource = br.readLine()) != null ) {
				filenames.add( resource );
			}
		}

		return filenames;
	}

	private InputStream getResourceAsStream( String resource ) {
		final InputStream in
		= getContextClassLoader().getResourceAsStream( resource );

		return in == null ? getClass().getResourceAsStream( resource ) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
