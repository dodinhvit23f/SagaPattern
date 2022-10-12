package com.kafka;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.common.KafkaTopic;
import com.common.OrderStatus;
import com.dto.OrderDTO;
import com.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaBuyRequestHandler {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendOrder(OrderDTO order, String key) {

		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaTopic.ORDER, key, order);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("Send order " + String.valueOf(result.getProducerRecord().value()));
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Send order fail: " + ex.getMessage());
			}

		});
	}

	@KafkaListener(topics = KafkaTopic.CUSTOMER, groupId = "${spring.kafka.consumer.group-id}")
	public void orderListener(ConsumerRecord<String, OrderDTO> record) {
		final OrderDTO order = record.value();

		switch (order.getStatus()) {

		case OrderStatus.CONFIRM:
			final OrderDTO orderRequest = UserService.ORDER_REQUEST.get(record.key());

			if (!orderRequest.equals(order)) {
				orderRequest.setStatus(OrderStatus.FAIL);
			}
			break;
		case OrderStatus.REJECTED:

		}
	}

}
