package com.amazon.test.testVO;

import java.util.Date;

/**
 * @Auther: liteng
 * @Date: 2018/7/5 13:48
 * @Description:
 */
public class AmazonStockReportVO {
    private int id;
    private Integer shopId;
    private Date syncFirst;
    private Date syncLast;
    private String sku;

    private Integer afnFulfillableQuantity; //在库可用库存

    private Integer afnReservedQuantity;    //订单锁定&入库未完成

    private Integer afnInboundWorkingQuantity;  //已建单

    private Integer afnInboundShippedQuantity;  //已发货

    private Integer afnInboundReceivingQuantity;    //签收中

    private Integer afnUnsellableQuantity;  //已损坏

    /**
     * 可用库存及在途总量=在库可用库存+订单锁定&入库未完成+已建单+已发货+签收中
     */
    private Integer afnTotalQuantity;

    /**
     * 海运在途  求和（查FBA补货订单，FBA货运方式是海运，平台订单状态为非RECEIVING，CLOSED，FBA订单状态非取消）
     */
    private Integer fbaSeaTransit;   //在主系统中计算

    public Integer getFbaSeaTransit() {
        return fbaSeaTransit;
    }

    public void setFbaSeaTransit(Integer fbaSeaTransit) {
        this.fbaSeaTransit = fbaSeaTransit;
    }

    private Integer afnWarehouseQuantity;   //from amazon report form

    public Date getSyncFirst() {
        return syncFirst;
    }

    public void setSyncFirst(Date syncFirst) {
        this.syncFirst = syncFirst;
    }

    public Date getSyncLast() {
        return syncLast;
    }

    public void setSyncLast(Date syncLast) {
        this.syncLast = syncLast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }



    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getAfnFulfillableQuantity() {
        return afnFulfillableQuantity;
    }

    public void setAfnFulfillableQuantity(Integer afnFulfillableQuantity) {
        this.afnFulfillableQuantity = afnFulfillableQuantity;
    }

    public Integer getAfnReservedQuantity() {
        return afnReservedQuantity;
    }

    public void setAfnReservedQuantity(Integer afnReservedQuantity) {
        this.afnReservedQuantity = afnReservedQuantity;
    }

    public Integer getAfnUnsellableQuantity() {
        return afnUnsellableQuantity;
    }

    public void setAfnUnsellableQuantity(Integer afnUnsellableQuantity) {
        this.afnUnsellableQuantity = afnUnsellableQuantity;
    }

    public Integer getAfnInboundWorkingQuantity() {
        return afnInboundWorkingQuantity;
    }

    public void setAfnInboundWorkingQuantity(Integer afnInboundWorkingQuantity) {
        this.afnInboundWorkingQuantity = afnInboundWorkingQuantity;
    }

    public Integer getAfnInboundShippedQuantity() {
        return afnInboundShippedQuantity;
    }

    public void setAfnInboundShippedQuantity(Integer afnInboundShippedQuantity) {
        this.afnInboundShippedQuantity = afnInboundShippedQuantity;
    }

    public Integer getAfnInboundReceivingQuantity() {
        return afnInboundReceivingQuantity;
    }

    public void setAfnInboundReceivingQuantity(Integer afnInboundReceivingQuantity) {
        this.afnInboundReceivingQuantity = afnInboundReceivingQuantity;
    }

    public Integer getAfnTotalQuantity() {
        return afnTotalQuantity;
    }

    public void setAfnTotalQuantity(Integer afnTotalQuantity) {
        this.afnTotalQuantity = afnTotalQuantity;
    }

    public Integer getAfnWarehouseQuantity() {
        return afnWarehouseQuantity;
    }

    public void setAfnWarehouseQuantity(Integer afnWarehouseQuantity) {
        this.afnWarehouseQuantity = afnWarehouseQuantity;
    }
}
