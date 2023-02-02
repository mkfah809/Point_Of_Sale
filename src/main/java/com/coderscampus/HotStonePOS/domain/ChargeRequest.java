package com.coderscampus.HotStonePOS.domain;

public class ChargeRequest {
	public enum Currency {
		EUR, USD;
	}

	private String description;
	private int amount;
	private com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency currency;
	private String stripeEmail;
	private String stripeToken;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency getCurrency() {
		return currency;
	}

	public void setCurrency(com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions.AcssDebit.Currency usd) {
		this.currency = usd;
	}

	public String getStripeEmail() {
		return stripeEmail;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

}
