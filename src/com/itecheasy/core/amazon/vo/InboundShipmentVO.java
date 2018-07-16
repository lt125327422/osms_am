package com.itecheasy.core.amazon.vo;

import java.util.List;

/**
 * @author taozihao
 * @date 2018-6-6
 * @description 创建亚马逊补货计划后返回的结果进行封装,也可以将补货订单的请求数据封装
 * @version
 */

public class InboundShipmentVO {
	private String shipmentId;
	private String shipmentName;
	private String destinationFulfillmentCenterId;
	private AddressVO shipToAddress;
	private AddressVO shipFromAddress;
	private String labelPrepType = "SELLER_LABEL";//业务这一块定为卖家贴标
	private String shipmentStatus = "WORKING"; //业务这一块定为working
	private List<InboundItemVO> items;

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

	public String getDestinationFulfillmentCenterId() {
		return destinationFulfillmentCenterId;
	}

	public void setDestinationFulfillmentCenterId(String destinationFulfillmentCenterId) {
		this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
	}

	public AddressVO getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(AddressVO shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public AddressVO getShipFromAddress() {
		return shipFromAddress;
	}

	public void setShipFromAddress(AddressVO shipFromAddress) {
		this.shipFromAddress = shipFromAddress;
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

	public List<InboundItemVO> getItems() {
		return items;
	}

	public void setItems(List<InboundItemVO> items) {
		this.items = items;
	}

}
