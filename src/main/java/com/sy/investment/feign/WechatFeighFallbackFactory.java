/**
 * 
 */
package com.sy.investment.feign;

import org.springframework.stereotype.Component;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.TemplateMessageImpl;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: WechatFeighFallbackFactory 
* @Description: 
* @author albert
* @date 2018年9月29日 下午2:02:51 
*  
*/
@Component
@Slf4j
public class WechatFeighFallbackFactory  implements FallbackFactory<WechatFeign> {
	@Override
	public WechatFeign create(Throwable cause) {
		return new WechatFeign() {
			@Override
			public RestEntity refundMessage(TemplateMessageImpl tmpl) {
				log.error("微信退款通知Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}

			@Override
			public RestEntity saleMessage(TemplateMessageImpl tmpl) {
				log.error("微信消费通知Feign异常",cause);
				return RestEntity.failed(cause.getMessage());
			}
		};
	}
	

}
