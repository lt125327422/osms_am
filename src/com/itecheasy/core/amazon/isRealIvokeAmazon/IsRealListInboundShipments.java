package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.ListInboundShipmentsResultVO;
import com.itecheasy.core.amazon.vo.AmazonShipmentStatusListVO;

/**
 * @Auther: liteng
 * @Date: 2018/7/20 10:08
 * @Description:
 */
public interface IsRealListInboundShipments {


     ListInboundShipmentsResultVO listInboundShipments(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO);

     ListInboundShipmentsResultVO listInboundShipmentsByNextToken(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO);

}
