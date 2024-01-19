package com.arsios.exchange.btcchina;

import org.springframework.context.annotation.Configuration;

//CHECKSTYLE:OFF
@Configuration
//CHECKSTYLE:ON
public class BTCChinaExchangerConfig {

	public static final String	PRICE_PRECISION		= "btcchina.exchanger.price_precision";
	public static final String	AMOUNT_PRECISION	= "btcchina.exchanger.amount_precision";
	public static final String	CHANGE_TOLERANCE	= "btcchina.exchanger.change_tolerance";
	public static final String	MINIMAL_OPERATION	= "btcchina.exchanger.minimal_operation";
	public static final String	STOP_LOSS_TOLERANCE	= "btcchina.exchanger.stop_loss_tolerance";
	public static final String	FEE					= "btcchina.exchanger.fee";
	public static final String	PROFIT				= "btcchina.exchanger.profit";
	public static final String	ORDER_AMOUNT		= "btcchina.exchanger.order_amount";
	public static final String	LOGDATA				= "btcchina.exchanger.log_data";
	public static final String	DEBUG				= "btcchina.exchanger.debug";

}
