package com.coderscampus.HotStonePOS.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.HotStonePOS.domain.Order;
import com.coderscampus.HotStonePOS.dto.ChargeResponse;
import com.coderscampus.HotStonePOS.service.ChargeService;
import com.coderscampus.HotStonePOS.service.OrderService;
import com.stripe.model.Coupon;
@Controller
public class ChargeController {
	@Value("${stripe.key.public}")
	private String API_PUBLIC_KEY;

	private ChargeService ChargeService;
	
	@Autowired
	OrderService orderService;

	public ChargeController(ChargeService ChargeService) {
		this.ChargeService = ChargeService;
	}


	@GetMapping("/subscription")
	public String subscriptionPage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "subscription";
	}

	@GetMapping("/charge/order/{orderId}")
	public String chargePage(Model model, @PathVariable Long orderId) {
		Order order = orderService.findById(orderId);
		model.addAttribute("order", order);
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "charge";
	}

	@PostMapping("/create-subscription")
	public @ResponseBody ChargeResponse createSubscription(String email, String token, String plan, String coupon) {

		if (token == null || plan.isEmpty()) {
			return new ChargeResponse(false, "Stripe payment token is missing. Please try again later.");
		}

		String customerId = ChargeService.createCustomer(email, token);

		if (customerId == null) {
			return new ChargeResponse(false, "An error accurred while trying to create customer");
		}

		String subscriptionId = ChargeService.createSubscription(customerId, plan, coupon);

		if (subscriptionId == null) {
			return new ChargeResponse(false, "An error accurred while trying to create subscription");
		}

		return new ChargeResponse(true, "Success! your subscription id is " + subscriptionId);
	}

	@PostMapping("/cancel-subscription")
	public @ResponseBody ChargeResponse cancelSubscription(String subscriptionId) {

		boolean subscriptionStatus = ChargeService.cancelSubscription(subscriptionId);

		if (!subscriptionStatus) {
			return new ChargeResponse(false, "Faild to cancel subscription. Please try again later");
		}

		return new ChargeResponse(true, "Subscription cancelled successfully");
	}

	@PostMapping("/coupon-validator")
	public @ResponseBody ChargeResponse couponValidator(String code) {

		Coupon coupon = ChargeService.retriveCoupon(code);

		if (coupon != null && coupon.getValid()) {
			String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
					: coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
			return new ChargeResponse(true, details);
		}
		return new ChargeResponse(false, "This coupon code is not available. This may be because it has expired or has "
				+ "already been applied to your account.");
	}

	@PostMapping("/create-charge")
	public @ResponseBody ChargeResponse createCharge(String email, String token) {

		if (token == null) {
			return new ChargeResponse(false, "Stripe payment token is missing. please try again later.");
		}

		String chargeId = ChargeService.createCharge(email, token, 105);// 9.99 usd

		if (chargeId == null) {
			return new ChargeResponse(false, "An error accurred while trying to charge.");
		}

		// You may want to store charge id along with order information

		return new ChargeResponse(true, "Success your charge id is " + chargeId);
	}
}
