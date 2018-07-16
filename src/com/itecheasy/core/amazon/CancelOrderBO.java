package com.itecheasy.core.amazon;

/**
 * @author wanghw
 * @date 2015-6-1
 * @description TODO
 * @version
 */
public class CancelOrderBO {

	private String orderId;
	private String cancelReason;
	private String itemCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

}
