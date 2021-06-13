/**
 * 
 */
package com.sy.investment.service;

import java.util.List;

import com.sy.investment.domain.entity.RestEntity;
import com.sy.investment.domain.entity.SaleExchangeSettleEntity;
import com.sy.investment.domain.vo.ExchangeEggVo;
import com.sy.investment.domain.vo.PackageConvertVo;
import com.sy.investment.domain.vo.ReceivableAccountVo;
import com.sy.investment.domain.vo.StoreOrderVo;

/** 
* @ClassName: SaleServie 
* @Description: 销售业务
* @author albert
* @date 2018年8月28日 下午2:07:07 
*  
*/
public interface SaleServie {
	/**门店销售保存*/
	public RestEntity save(StoreOrderVo storeOrderVo);
	/**同步支付结果*/
	public RestEntity syncPayResult(String outTradeNo);
	/**撤销支付*/
	public void revoke(String outTradeNo);
	/**门店交班结算*/
	public void save(List<SaleExchangeSettleEntity> list);
	/**门店收款*/
	public void saveReceive(List<ReceivableAccountVo> list);
	/**卡片特权兑换*/
	public void savePackageConvert(PackageConvertVo vo);
	/**卡片兑换商品*/
	public void saveExchange(ExchangeEggVo vo);
}
