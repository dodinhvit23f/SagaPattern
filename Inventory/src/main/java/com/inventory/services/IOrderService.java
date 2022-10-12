package com.inventory.services;

import com.dto.OrderDTO;

public interface IOrderService{
	OrderDTO comfirmOrder(OrderDTO order);
	void acceptedOrder(OrderDTO order);
	void rejectOrder(OrderDTO order);
	void cancelOrder(OrderDTO order);
}
