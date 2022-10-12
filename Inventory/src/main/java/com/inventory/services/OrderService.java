package com.inventory.services;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.inventory.entity.Order;
import com.inventory.entity.Product;
import com.inventory.repository.OrderRepository;

@Service
public class OrderService implements IOrderService {
	
	public static final Queue<String> deque = new ArrayDeque<>(0);
	public static final Map<String, Long> time = new HashMap<>(0);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private IProductService productSevice;

	private double getPromotion(double promotion) {
		return ((promotion == 0) ? 0 : 100 - promotion) / 100;
	}

	@Override
	public OrderDTO comfirmOrder(OrderDTO order) {

		final Optional<Product> searchedProduct = productSevice.findByIdAndRetailerId(order.getProductId(),
				order.getRetailerId());

		if (searchedProduct.isEmpty()) {
			order.setStatus(OrderStatus.FAIL);
			return order;
		}

		final Product product = searchedProduct.get();

		if (product.getQuantity() < order.getQuantity()) {
			order.setStatus(OrderStatus.FAIL);
			return order;
		}

		if (product.getPromotion() != order.getPromotion()) {
			order.setStatus(OrderStatus.FAIL);
			return order;
		}

		double saleAmount = order.getQuantity() * product.getPrice() * getPromotion(product.getPromotion());
		double orderPAmount = order.getPrice() * order.getQuantity() * order.getPromotion() / 100;

		if (saleAmount != orderPAmount) {
			order.setStatus(OrderStatus.FAIL);
			return order;
		}
		
		final Order newOrder = new Order();

		newOrder.setDeleted(false);
		newOrder.setPrice(order.getPrice());
		newOrder.setStatus(OrderStatus.CONFIRM);
		newOrder.setProduct(product);
		newOrder.setPromotion(order.getPromotion());
		newOrder.setCode(order.getId());
		
		orderRepository.save(newOrder);	
		
		order.setStatus(OrderStatus.CONFIRM);

		return order;
	}

	@Override
	public void acceptedOrder(OrderDTO order) {

		final Product product = productSevice.findByIdAndRetailerId(order.getProductId(), order.getRetailerId()).get();

		product.setQuantity(product.getQuantity() - order.getQuantity());

		final Order newOrder = new Order();

		newOrder.setDeleted(false);
		newOrder.setPrice(order.getPrice());
		newOrder.setStatus(OrderStatus.SUCCESS);
		newOrder.setProduct(product);
		newOrder.setPromotion(order.getPromotion());
		newOrder.setCode(order.getId());

		orderRepository.save(newOrder);
	}

	@Override
	public void rejectOrder(OrderDTO order) {
		final Order orderRejected = orderRepository.findByCode(order.getId()).get();
		final Product product = productSevice.findByIdAndRetailerId(order.getProductId(), order.getRetailerId()).get();

		product.setQuantity(product.getQuantity() + order.getQuantity());

		orderRejected.setStatus(OrderStatus.REJECTED);
		orderRejected.setDeleted(true);
		orderRepository.save(orderRejected);
	}
	
	@Override
	public void cancelOrder(OrderDTO order) {
		final Order orderRejected = orderRepository.findByCode(order.getId()).get();
		orderRejected.setStatus(OrderStatus.FAIL);
		orderRejected.setDeleted(true);
		orderRepository.save(orderRejected);
	}

}
