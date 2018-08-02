package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.amazon.client.AmazonInboundClient;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.ListInboundShipmentsResultVO;
import com.itecheasy.core.amazon.vo.AmazonShipmentStatusListVO;

/**
 * @Auther: liteng
 * @Date: 2018/7/20 10:08
 * @Description: real access amazon
 */
public class RealListInboundShipmentsImpl implements IsRealListInboundShipments {


    @Override
    public ListInboundShipmentsResultVO listInboundShipments(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO) {
        ListInboundShipmentsResultVO resultVO = AmazonInboundClient.listInboundShipments(api, amazonShipmentStatusListVO);
        return resultVO;
    }

    @Override
    public ListInboundShipmentsResultVO listInboundShipmentsByNextToken(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO) {
        ListInboundShipmentsResultVO resultVO = AmazonInboundClient.listInboundShipmentsByNextToken(api, amazonShipmentStatusListVO);
        return resultVO;
    }
}
