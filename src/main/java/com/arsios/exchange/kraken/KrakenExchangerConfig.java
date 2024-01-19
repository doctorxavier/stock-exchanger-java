package com.arsios.exchange.kraken;

import org.springframework.context.annotation.Configuration;

// CHECKSTYLE:OFF
@Configuration
// CHECKSTYLE:ON
public class KrakenExchangerConfig {

	public static final String	PRICE_PRECISION		= "kraken.exchanger.price_precision";
	public static final String	AMOUNT_PRECISION	= "kraken.exchanger.amount_precision";
	public static final String	CHANGE_TOLERANCE	= "kraken.exchanger.change_tolerance";
	public static final String	MINIMAL_OPERATION	= "kraken.exchanger.minimal_operation";
	public static final String	STOP_LOSS_TOLERANCE	= "kraken.exchanger.stop_loss_tolerance";
	public static final String	FEE					= "kraken.exchanger.fee";
	public static final String	PROFIT				= "kraken.exchanger.profit";
	public static final String	ORDER_AMOUNT		= "kraken.exchanger.order_amount";
	public static final String	ATTEMPTS			= "kraken.exchanger.operation_attempts";
	public static final String	ATTEMPT_DELAY		= "kraken.exchanger.attempt_delay";
	public static final String	ITERATION_DELAY		= "kraken.exchanger.iteration_delay";
	public static final String	REFRESH_DATA		= "kraken.exchanger.refresh_data";
	public static final String	ERROR_TOLERANCE		= "kraken.exchanger.error_tolerance";
	public static final String	LOGDATA				= "kraken.exchanger.log_data";
	public static final String	DEBUG				= "kraken.exchanger.debug";

}
