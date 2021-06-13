/**
 * 
 */
package com.sy.investment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sy.investment.reponsitory.CommonDao;
import com.sy.investment.utils.ParamValue;

/** 
* @ClassName: PosApplicationTest 
* @Description: 
* @author albert
* @date 2018年7月26日 上午10:27:11 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTest {
	@Resource CommonDao commonDao;
	@org.junit.Test
	public void test() {
		List<Map<String,Object>> list = commonDao.findMapsBySql("select * from user_t ", ParamValue.build());
		list.forEach(item -> System.out.println(item));
	}
}
