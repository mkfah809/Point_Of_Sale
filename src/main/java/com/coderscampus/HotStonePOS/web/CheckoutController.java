package com.coderscampus.HotStonePOS.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.HotStonePOS.domain.ChargeRequest;
import com.coderscampus.HotStonePOS.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency;

@Controller
public class CheckoutController {

//	@Value("${STRIPE.PUBLIC.KEY}")
//	private String stripePublicKey = "pk_live_51MRH2RCxBMeiMaI5c4rRHqd5C1mV7JtAADnP5alIFY6ePvNV4CLQtgqtAuDgz7AqVYFHaaFG15RntDUDRzOPKKWl00AS0JVv1u";

	@Value("${STRIPE.PUBLIC.KEY}")
	private String stripePublicKey;
	@Autowired
	private StripeService paymentsService;

	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("amount", 50 * 100); // in cents
		model.addAttribute("stripePublicKey", stripePublicKey);
		model.addAttribute("currency", ChargeRequest.Currency.EUR);
		return "checkout";
	}

	// controller that will receive the POST request
	// made by the checkout form and submit the charge to Stripe via StripeService
	@PostMapping("/charge")
	public String charge(ChargeRequest chargeRequest, Model model) throws Exception {
		chargeRequest.setDescription("Example charge");
		chargeRequest.setCurrency(Currency.USD);
		Charge charge = paymentsService.charge(chargeRequest);
		model.addAttribute("id", charge.getId());
		model.addAttribute("status", charge.getStatus());
		model.addAttribute("chargeId", charge.getId());
		model.addAttribute("balance_transaction", charge.getBalanceTransaction());
		return "result";
	}

	@ExceptionHandler(StripeException.class)
	public String handleError(Model model, StripeException ex) {
		model.addAttribute("error", ex.getMessage());
		return "result";
	}
}
