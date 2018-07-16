package com.itecheasy.core.amazon;

import com.amazonservices.mws.FulfillmentInboundShipment._2010_10_01.model.InboundShipmentInfo;

import java.util.List;

/**
 * @Auther: liteng
 * @Date: 2018/6/28 14:42
 * @Description:用于返回osms
 */
public class ListInboundShipmentsResultVO {

    private String nextToken;

    private List<InboundShipmentInfoVO> inboundShipmentInfoVOList;

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public List<InboundShipmentInfoVO> getInboundShipmentInfoVOList() {
        return inboundShipmentInfoVOList;
    }

    public void setInboundShipmentInfoVOList(List<InboundShipmentInfoVO> inboundShipmentInfoVOList) {
        this.inboundShipmentInfoVOList = inboundShipmentInfoVOList;
    }
}
