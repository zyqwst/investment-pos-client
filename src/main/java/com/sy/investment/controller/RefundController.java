/**
 * 
 */
package com.sy.investment.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.table.Sale;
import com.sy.investment.domain.table.Store;
import com.sy.investment.domain.view.MemberAmountView;
import com.sy.investment.domain.vo.StoreRefundVo;
import com.sy.investment.domain.vo.TemplateMessageImpl;
import com.sy.investment.feign.WechatFeign;
import com.sy.investment.service.RefundService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
* @ClassName: RefundController 
* @Description: 
* @author albert
* @date 2018年9月28日 下午12:54:54 
*  
*/
@Api(tags="退款相关API")
@Validated
@RestController
@RequestMapping("/refund")
public class RefundController extends BaseController {
	@Resource RefundService refundService;
	@Resource WechatFeign wechatFeign;
	@ApiOperation(value="门店销售退款")
	@PostMapping("")
	public RestEntity post(@ApiParam(name = "storeRefundVo", value = "订单信息", required =true)
	@Validated @RequestBody StoreRefundVo storeRefundVo) {
		log.debug(storeRefundVo.toString());
		Sale refund = refundService.save(storeRefundVo);
		if(refund.getMemberId()!=null)
		try {
			MemberAmountView member = commonService.findEntityById(MemberAmountView.class, refund.getMemberId());
			wechatFeign.refundMessage(TemplateMessageImpl.builder()
					.first("消费退款成功，请注意查收")
					.keyword1(commonService.findEntityById(Store.class, refund.getStoreId()).getName())
					.keyword2(refund.getBillno())
					.keyword3(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm"))
					.keyword4("￥"+refund.getAmount().stripTrailingZeros().toPlainString())
					.keyword5("￥"+member.getAmount().add(member.getBackAmount()).stripTrailingZeros().toPlainString())
					.openId(member.getOpenId())
					.remark("（退款明细）现金：￥" + storeRefundVo.getCashAmount().stripTrailingZeros().toPlainString()
							+"，会员卡：￥"+storeRefundVo.getCardAmount().stripTrailingZeros().toPlainString()
							+"，POS机：￥"+storeRefundVo.getPosAmount().stripTrailingZeros().toPlainString())
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RestEntity.success();
	}
}
