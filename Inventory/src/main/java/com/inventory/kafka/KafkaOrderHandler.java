package com.inventory.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.common.KafkaTopic;
import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.inventory.services.IOrderService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class KafkaOrderHandler {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private IOrderService orderService;

	@KafkaListener(topics = KafkaTopic.ORDER, groupId = "${spring.kafka.consumer.group-id}")
	public void orderListener(ConsumerRecord<String, OrderDTO> record) {
		final OrderDTO order = record.value();

		switch (order.getStatus()) {
		case OrderStatus.PROCESS:
			orderService.comfirmOrder(order);
			break;

		case OrderStatus.CONFIRM:
			orderService.acceptedOrder(order);
			break;

		case OrderStatus.REJECTED:
			orderService.rejectOrder(order);
			break;
			
		case OrderStatus.FAIL:
			orderService.cancelOrder(order);
			break;
		}

	
		kafkaTemplate.send(KafkaTopic.CUSTOMER, record.key(), order);
		log.info("Confirm + " + order.toString());

	}

}
