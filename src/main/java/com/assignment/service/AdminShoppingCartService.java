package com.assignment.service;

import com.assignment.DTO.AdminCartItem;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface AdminShoppingCartService {

	double getAmount();

	int getCount();

	Set<Integer> keySet();

    Map<Integer, AdminCartItem> getMap();

    Collection<AdminCartItem> getItems();

	void clear();


	void remove(Integer id);

//	void update(Long id, int quantity, double unitPrice, Long colorId, Long sizeId);

	void update(Integer id, Integer quantity, Double unitPrice, Integer colorId, Integer sizeId);

	void update(AdminCartItem cartItem);

	void add(AdminCartItem cartItem);
}
