package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.amazon.client.AmazonInboundClient;
import com.itecheasy.core.amazon.AmazonConfigInfo;
import com.itecheasy.core.amazon.ListInboundShipmentsResultVO;
import com.itecheasy.core.amazon.vo.AmazonShipmentStatusListVO;
import com.printMethod.annotations.LoggerNameDescription;
import org.apache.log4j.Logger;

/**
 * @Auther: liteng
 * @Date: 2018/7/20 10:08
 * @Description: real call amazon
 */
public class RealListInboundShipmentsImpl implements IsRealListInboundShipments {

    private static final Logger LOGGER = Logger.getLogger(RealListInboundShipmentsImpl.class);

    @Override
    @LoggerNameDescription(methodNameDescription = "listInboundShipments")
    public ListInboundShipmentsResultVO listInboundShipments(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO) {
       return AmazonInboundClient.listInboundShipments(api, amazonShipmentStatusListVO);

    }

    @Override
    @LoggerNameDescription(methodNameDescription = "listInboundShipmentsByNextToken")
    public ListInboundShipmentsResultVO listInboundShipmentsByNextToken(AmazonConfigInfo api, AmazonShipmentStatusListVO amazonShipmentStatusListVO) {
        return AmazonInboundClient.listInboundShipmentsByNextToken(api, amazonShipmentStatusListVO);

    }
}
