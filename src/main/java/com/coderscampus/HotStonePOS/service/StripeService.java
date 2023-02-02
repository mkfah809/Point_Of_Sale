package com.coderscampus.HotStonePOS.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.model.Charge;

@Service
public class StripeService {
////	@Value("${STRIPE.SECRET.KEY}")
//	private String secretKey = "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";

	@Value("${STRIPE.SECRET.KEY}")
	private String secretKey;

	public void init() {
		Stripe.apiKey = secretKey;
	}

	public Charge charge(ChargeRequest chargeRequest) throws Exception {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("description", chargeRequest.getDescription());
		chargeParams.put("source", chargeRequest.getStripeToken());
		return Charge.create(chargeParams);
	}

}
