/**
 * 
 */
package com.sy.investment.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.TagsStore;
import com.sy.investment.utils.ParamValue;
import com.sy.investment.utils.UserThreadLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: TagsController 
* @Description: 
* @author albert
* @date 2018年8月27日 下午2:32:05 
*  
*/
@RestController
@RequestMapping("/tags")
@Api(tags="标签管理API")
@Validated
public class TagsController extends BaseController {
	@ApiOperation(value="获取门店挂单标签")
	@GetMapping("/guadan")
	public RestEntity guadan() {
		List<TagsStore> list = commonService.findAll(TagsStore.class, " where storeId=:storeId and type=1 ", ParamValue.build().add("storeId", UserThreadLocal.get().getStoreid()));
		List<String> arr = new ArrayList<>();
		list.forEach(item -> arr.add(item.getTag()));
		return RestEntity.success(arr);
	}
	@ApiOperation(value="新增门店挂单标签")
	@PostMapping("/guadan")
	@ApiImplicitParams({
		@ApiImplicitParam(name="tag",value="标签",dataType="String",required=true),
	})
	public RestEntity guadanSave(@Length(max=100,min=1)@RequestBody String tag) {
		System.out.println(tag);
		commonService.save(TagsStore.builder()
									.storeId(UserThreadLocal.get().getStoreid())
									.tag(tag)
									.type(TagsStore.TYPE_GUADAN)
									.build());
		return RestEntity.success();
	}
}
