package com.arsios.exchange.api.kraken.service;

import java.util.Map;

import com.arsios.exchange.api.kraken.model.AddOrderResult;
import com.arsios.exchange.api.kraken.model.CancelOrderResult;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;

public interface IUserTradingService {
	
	/*
	 *	Input:
	 *
	 *	pair = asset pair
	 *	type = type of order (buy/sell)
	 *	ordertype = order type:
	 *    	market
	 *    	limit (price = limit price)
	 *    	stop-loss (price = stop loss price)
	 *    	take-profit (price = take profit price)
	 *    	stop-loss-profit (price = stop loss price, price2 = take profit price)
	 *    	stop-loss-profit-limit (price = stop loss price, price2 = take profit price)
	 *    	stop-loss-limit (price = stop loss trigger price, price2 = triggered limit price)
	 *    	take-profit-limit (price = take profit trigger price, price2 = triggered limit price)
	 *    	trailing-stop (price = trailing stop offset)
	 *    	trailing-stop-limit (price = trailing stop offset, price2 = triggered limit offset)
	 *    	stop-loss-and-limit (price = stop loss price, price2 = limit price)
	 *	price = price (optional.  dependent upon ordertype)
	 *	price2 = secondary price (optional.  dependent upon ordertype)
	 *	volume = order volume in lots
	 *	leverage = amount of leverage desired (optional.  default = none)
	 *	position = position tx id to close (optional.  used to close positions)
	 *	oflags = comma delimited list of order flags (optional):
	 *    	viqc = volume in quote currency
	 *    	plbc = prefer profit/loss in base currency
	 *    	nompp = no market price protection
	 *	starttm = scheduled start time (optional):
	 *    	0 = now (default)
	 *    	+<n> = schedule start time <n> seconds from now
	 *    	<n> = unix timestamp of start time
	 *	expiretm = expiration time (optional):
	 *    	0 = no expiration (default)
	 *    	+<n> = expire <n> seconds from now
	 *    	<n> = unix timestamp of expiration time
	 *	userref = user reference id.  32-bit signed number.  (optional)
	 *	validate = validate inputs only.  do not submit order (optional)
	 *
	 *	optional closing order to add to system when order gets filled:
	 *    	close[ordertype] = order type
	 *    	close[price] = price
	 *    	close[price2] = secondary price
	 *	
	 *	Result:
	 *
	 *	descr = order description info
	 *		order = order description
	 *		close = conditional close order description (if conditional close set)
	 *	txid = array of transaction ids for order (if order was added successfully)
	 *
	 *	Errors: errors include (but are not limited to):
	 *
	 *		EGeneral:Invalid arguments
	 *		EService:Unavailable
	 *		ETrade:Invalid request
	 *		EOrder:Cannot open position
	 *		EOrder:Cannot open opposing position
	 *		EOrder:Margin allowance exceeded
	 *		EOrder:Margin level too low
	 *		EOrder:Insufficient margin (exchange does not have sufficient funds to allow margin trading)
	 *		EOrder:Insufficient funds (insufficient user funds)
	 *		EOrder:Order minimum not met (volume too low)
	 *		EOrder:Orders limit exceeded
	 *		EOrder:Positions limit exceeded
	 *		EOrder:Rate limit exceeded
	 *		EOrder:Scheduled orders limit exceeded
	 *		EOrder:Unknown position
	 *
	 *	Note:
	 *		See Get tradable asset pairs for specifications on asset pair prices, lots, and leverage.
     *		Prices can be preceded by +, -, or # to signify the price as a relative amount (with the 
     *		exception of trailing stops, which are always relative). + adds the amount to 
     *		the current offered price. - subtracts the amount from the current offered price. 
     *		# will either add or subtract the amount to the current offered price, depending on 
     *		the type and order type used. Relative prices can be suffixed with a % to signify 
     *		the relative amount as a percentage of the offered price.
     *		
     *		Position may be an order tx id representing all positions opened by that order. 
     *		When this is used, all open positions related to the given order that do not already have 
     *		an order assigned will have an order submitted at for the remaining volume of that position. 
     *		Example: Order OA opened up positions P1 @ 1 lot, P2 @ 2 lots, and P3 @ 3 lots. 
     *		An order was placed to close 2 lots of P3 that hasn't been filled. Submitting a market order 
     *		using a position of OA will create orders to close P1 @ 1 lot and P2 @ 2 lots. 
     *		No order will be placed for P3 since it already has an open order. 
     *		However, if the order to close 2 lots of P3 had filled and completed that order 
     *		before submitting the market order, an order to close P3 @ 1 lot would also be created.
     *
	 */
	AddOrderResult addOrder(Map<String, String> params) throws KrakenDataException;
	
	/*
	 * 	Input:
	 * 
	 * 	txid = transaction id
	 * 
	 * 	Result:
	 * 
	 * 	count = number of orders canceled
	 *	pending = if set, order(s) is/are pending cancellation
	 *
	 *	Note: 
	 *		txid may be an order tx id or a user reference id. An order tx id may be 
	 *		preceded by * to cancel all open orders related to positions opened by 
	 *		the given order tx id.
	 * 
	 */
	CancelOrderResult cancelOrder(Map<String, String> params) throws KrakenDataException;

}
