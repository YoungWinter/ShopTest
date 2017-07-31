package cn.itcast.shop.domain;

public class CartItem {

	private Product product;
	private int buyNum;
	private double subTotal;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
		setSubTotal(buyNum);
	}

	public double getSubTotal() {
		return subTotal;
	}

	private void setSubTotal(int buyNum) {
		this.subTotal = buyNum * getProduct().getShop_price();
	}

}
