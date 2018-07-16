package com.itecheasy.core.amazon;

import com.itecheasy.core.amazon.vo.AddressVO;

/**
 * @Auther: liteng
 * @Date: 2018/6/28 15:22
 * @Description:
 */
public class InboundShipmentInfoVO {

    private String shipmentId;
    private String shipmentName;
    private AddressVO ShipFromAddress;
    private String destinationFulfillmentCenterId;
    private String labelPrepType;

//    WORKING - 卖家已创建货件，但未发货。
//    SHIPPED - 承运人已取件。
//    IN_TRANSIT - 承运人已通知亚马逊配送中心其已知晓货件的存在。
//    DELIVERED - 承运人已将货件配送至亚马逊配送中心。
//    CHECKED_IN - 货件已在亚马逊配送中心的收货装卸地点登记。
//    RECEIVING - 货件已到达亚马逊配送中心，但有部分商品尚未标记为已收到。
//    CLOSED - 货件已到达亚马逊配送中心，但有部分商品尚未标记为已收到。
//    CANCELLED - 卖家在货件发送至亚马逊配送中心后取消了货件。
//    DELETED - 卖家在将货件发送到亚马逊配送中心之前取消了货件。
//    ERROR - 货件出错，但不是由亚马逊处理的。
    private String shipmentStatus;

    private String areCasesRequired;

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public AddressVO getShipFromAddress() {
        return ShipFromAddress;
    }

    public void setShipFromAddress(AddressVO shipFromAddress) {
        ShipFromAddress = shipFromAddress;
    }

    public String getDestinationFulfillmentCenterId() {
        return destinationFulfillmentCenterId;
    }

    public void setDestinationFulfillmentCenterId(String destinationFulfillmentCenterId) {
        this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
    }

    public String getLabelPrepType() {
        return labelPrepType;
    }

    public void setLabelPrepType(String labelPrepType) {
        this.labelPrepType = labelPrepType;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getAreCasesRequired() {
        return areCasesRequired;
    }

    public void setAreCasesRequired(String areCasesRequired) {
        this.areCasesRequired = areCasesRequired;
    }
}
