/**
 * 
 */
package test;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.mapper.UserMapper;

/**
 * @author wang fu liang 
 *
 */
public class TestMapper {

	private ApplicationContext ioc=null;
	private DataSource ds=null;
	private UserMapper userMapper = null;
	
	{
		ioc=new ClassPathXmlApplicationContext("applicationContext.xml");
		ds=ioc.getBean(DataSource.class);
		userMapper = ioc.getBean(UserMapper.class);
	}
	
	@Test
	public void testGet(){
		userMapper.getByName("bcde");
	}
	
	
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(ds.getConnection());
	}

}
