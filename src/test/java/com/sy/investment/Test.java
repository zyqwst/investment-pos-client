/**
 * 
 */
package com.sy.investment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.ListUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.sy.investment.domain.table.Member;
import com.sy.investment.domain.vo.GoodsPriceVo;
import com.sy.investment.domain.vo.MemberVo;
import com.sy.investment.domain.vo.UserVo;
import static java.util.stream.Collectors.groupingBy;

/** 
* @ClassName: Test 
* @Description: 
* @author albert
* @date 2018年8月23日 上午10:11:05 
*  
*/
public class Test {
	public static void main(String[] args) {
//		System.out.println(DigestUtils.md5DigestAsHex("007".getBytes()).toUpperCase());
		List<UserVo> list = new ArrayList<>();
		list.add(UserVo.builder().id(1l).build());
		list.add(UserVo.builder().id(1l).build());
		list.add(UserVo.builder().id(2l).build());
		list.add(UserVo.builder().id(1l).build());
		
//		System.out.println(list.stream().filter(item -> item>0 ).collect(Collectors.toList()));
		list.stream().collect(groupingBy(UserVo::getId)).forEach((key,val) -> System.out.println(val));;
//		System.out.println(Optional.ofNullable(2).orElse(1));
	}
}
