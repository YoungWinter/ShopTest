package cn.itcast.shop.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cart {
	private Map<String, CartItem> CartItemMap = new HashMap<String, CartItem>();
	private double total;

	public Map<String, CartItem> getCartItemMap() {
		return CartItemMap;
	}

	public void setCartItemMap(Map<String, CartItem> cartItemMap) {
		CartItemMap = cartItemMap;
		double total = 0;
		for (Entry<String, CartItem> entry : cartItemMap.entrySet()) {
			CartItem item = (CartItem) entry.getValue();
			total += item.getSubTotal();
		}
		setTotal(total);
	}

	public double getTotal() {
		return total;
	}

	private void setTotal(double total) {
		this.total = total;
	}

}
