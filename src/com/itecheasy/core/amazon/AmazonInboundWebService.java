package com.itecheasy.core.amazon;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.itecheasy.core.amazon.vo.AddressVO;
import com.itecheasy.core.amazon.vo.InboundItemVO;
import com.itecheasy.core.amazon.vo.InboundShipmentVO;

/**
 * @author taozihao
 * @date 2018-6-6
 * @description 亚马逊补货计划以及创建补货单
 * @version
 */

@WebService
public interface AmazonInboundWebService {

	/**
	 * 创建fba补货计划
	 * @param api
	 * @param itemList
	 * @param shipFromAddress
	 * @param shipToCountryCode
	 * @return
	 */
	public List<InboundShipmentVO> createInboundShipmentPlan(@WebParam(name = "api") AmazonConfigInfo api, @WebParam(name = "itemList")List<InboundItemVO> itemList,
			@WebParam(name = "shipFromAddress")AddressVO shipFromAddress,@WebParam(name = "shipToCountryCode") String shipToCountryCode);

	/**
	 * 提交fba补货订单
	 * @param api
	 * @param inboundShipmentVO
	 * @return
	 */
	public String createInboundShipment(@WebParam(name = "api") AmazonConfigInfo api,@WebParam(name="inboundShipmentVO") InboundShipmentVO inboundShipmentVO);
}
