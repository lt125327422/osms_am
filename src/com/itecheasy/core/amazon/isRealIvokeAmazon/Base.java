package com.itecheasy.core.amazon.isRealIvokeAmazon;

import com.itecheasy.common.util.DeployProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liteng
 * @Date: 2018/7/18 08:47
 * @Description:
 */
public abstract class Base {
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    protected final static int MOCK = 0;
    protected final static int REAL_INVOKE = 1;
    protected static int IS_REAL_INVOKE =-1; //修改这个来用于判断是否真实调用亚马逊

    protected IsRealGetStockReportFromAmazon isRealGetStockReportFromAmazon;
    protected Map<String,IsRealGetStockReportFromAmazon> isRealInvokeAmazonMap = new HashMap<String, IsRealGetStockReportFromAmazon>();
    protected IsRealListInboundShipments isRealListInboundShipments;
    protected Map<String,IsRealListInboundShipments> isRealListInboundShipmentsMap = new HashMap<String, IsRealListInboundShipments>();

    public void setIsRealInvokeAmazonMap(Map<String, IsRealGetStockReportFromAmazon> isRealInvokeAmazonMap) {
        this.isRealInvokeAmazonMap = isRealInvokeAmazonMap;
    }

    public void setIsRealListInboundShipmentsMap(Map<String, IsRealListInboundShipments> isRealListInboundShipmentsMap) {
        this.isRealListInboundShipmentsMap = isRealListInboundShipmentsMap;
    }

    public Base() {
        IS_REAL_INVOKE = Integer.parseInt(DeployProperties.getInstance().getProperty("amazon.stock.report.invoke"));
    }





}
