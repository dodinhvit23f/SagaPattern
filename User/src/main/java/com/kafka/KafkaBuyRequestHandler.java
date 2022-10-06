package com.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.common.KafkaTopic;
import com.dto.OrderDTO;

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

}
