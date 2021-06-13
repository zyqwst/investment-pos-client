/**
 * 
 */
package com.sy.investment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.TradePayVo;

/** 
* @ClassName: PayServiceFeign 
* @Description: 
* @author albert
* @date 2019年1月3日 下午1:36:19 
*  
*/
@FeignClient(name="pay-service", fallbackFactory=PayServiceFeignFallbackFactory.class)
public interface PayServiceFeign {
	/**
	 * 微信扫码支付
	 * @param tradePayVo
	 * @return
	 */
	@PostMapping("/wxpay/micropay")
	public RestEntity micopay(@RequestBody TradePayVo tradePayVo);
	/**
	 * 支付宝扫码支付
	 * @param tradePayVo
	 * @return
	 */
	@PostMapping("/alipay/tradepay")
	public RestEntity alipay(@RequestBody TradePayVo tradePayVo);
	/**
	 * 扫码支付同步订单
	 * @param outTradeNo
	 * @return
	 */
	@GetMapping("/order/sync")
	public RestEntity syncPayResult(@RequestParam(value="outTradeNo") String outTradeNo);
	@GetMapping("/order/revoke")
	public RestEntity revoke(@RequestParam(value="outTradeNo") String outTradeNo);
}
