package com.inventory.kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.common.KafkaTopic;
import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.inventory.entity.Product;
import com.inventory.services.IProductService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class KafkaOrderHandler {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private IProductService productSevice;

	public double getPromotion(double promotion) {
		return ((promotion == 0) ? 0 : 100 - promotion) / 100;
	}

	@KafkaListener(topics = KafkaTopic.ORDER, groupId = "${spring.kafka.consumer.group-id}")
	public void orderListener(ConsumerRecord<String, OrderDTO> record) {
		final OrderDTO order = record.value();

		final Optional<Product> searchedProduct = productSevice.findByIdAndRetailerId(order.getProductId(),
				order.getRetailerId());

		if (searchedProduct.isEmpty()) {
			order.setStatus(OrderStatus.FAIL);
			kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
			return;
		}

		Product product = searchedProduct.get();

		if (product.getQuantity() < order.getQuantity()) {
			order.setStatus(OrderStatus.FAIL);
			kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
			return;
		}
		if (product.getPromotion() != order.getPromotion()) {
			order.setStatus(OrderStatus.FAIL);
			kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
			return;
		}
		double saleAmount = order.getQuantity() * product.getPrice() * getPromotion(product.getPromotion());
		double orderPAmount = order.getPrice() * order.getQuantity() * order.getPromotion() / 100;

		if (saleAmount != orderPAmount) {
			order.setStatus(OrderStatus.FAIL);
			kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
			return;
		}

		order.setStatus(OrderStatus.CONFIRM);
		kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
		log.info("Confirm + " + order.toString());
	}

}
