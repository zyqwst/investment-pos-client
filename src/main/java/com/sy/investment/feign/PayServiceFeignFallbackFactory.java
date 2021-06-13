/**
 * 
 */
package com.sy.investment.feign;

import org.springframework.stereotype.Component;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.TradePayVo;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: PayServiceFeignFallbackFactory 
* @Description: 
* @author albert
* @date 2019年1月3日 下午1:36:45 
*  
*/
@Component
@Slf4j
public class PayServiceFeignFallbackFactory implements FallbackFactory<PayServiceFeign>{

	@Override
	public PayServiceFeign create(Throwable cause) {
		return new PayServiceFeign() {
			@Override
			public RestEntity alipay(TradePayVo tradePayVo) {
				log.error("支付宝扫码支付Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}

			@Override
			public RestEntity micopay(TradePayVo tradePayVo) {
				log.error("微信扫码支付Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}

			@Override
			public RestEntity syncPayResult(String outTradeNo) {
				log.error("同步订单状态Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}

			@Override
			public RestEntity revoke(String outTradeNo) {
				log.error("支付撤销Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}
		};
	}

}
