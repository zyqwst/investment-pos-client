/**
 * 
 */
package com.sy.investment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.vo.TemplateMessageImpl;

/** 
* @ClassName: WechatFeign 
* @Description: 
* @author albert
* @date 2018年9月29日 下午2:00:14 
*  
*/
@FeignClient(name="wechat-client", fallbackFactory=WechatFeighFallbackFactory.class)
public interface WechatFeign {
	/**
	 * @param 退款成功发送微信通知
	 * @return
	 */
	@Async
	@PostMapping("/tmplmsg/refund")
	public RestEntity refundMessage(@RequestBody TemplateMessageImpl tmpl);
	/**
	 * 门店消费成功通知
	 * @param tmpl
	 * @return
	 */
	@Async
	@PostMapping("/tmplmsg/sale")
	public RestEntity saleMessage(@RequestBody  TemplateMessageImpl tmpl);
}
