package cn.itcast.shop.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cart {
	private Map<Integer, CartItem> CartItemMap = new HashMap<Integer, CartItem>();
	private double total;

	public Map<Integer, CartItem> getCartItemMap() {
		return CartItemMap;
	}

	public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
		CartItemMap = cartItemMap;
		double total = 0;
		for (Entry<Integer, CartItem> entry : cartItemMap.entrySet()) {
			CartItem item = entry.getValue();
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
