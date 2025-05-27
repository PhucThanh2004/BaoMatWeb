package vn.iotstar.models;

import java.time.LocalDateTime;
import java.util.List;

public class OrderWithDetails {
	private int orderId;
	private int accountId;
	private int shopId;
	private double totalAmount;
	private String paymentMethod;
	private String status;
	private String note;
	private LocalDateTime createdAt;
	private List<OrderDetailWithProduct> orderDetails;
	private String accountName;
	private String accountEmail;
	private int address_id;
	private AddressModel address; 
	
	 public AddressModel getAddress() {
	        return address;
	    }

	    public void setAddress(AddressModel address) {
	        this.address = address;
	    }

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<OrderDetailWithProduct> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailWithProduct> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
