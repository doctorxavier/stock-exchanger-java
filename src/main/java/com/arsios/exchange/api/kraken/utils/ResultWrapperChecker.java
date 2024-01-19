package com.arsios.exchange.api.kraken.utils;

import com.arsios.exchange.api.kraken.model.ResultWrapper;
import com.arsios.exchange.api.kraken.service.impl.KrakenDataException;

public final class ResultWrapperChecker<T> {

	public T checkErrors(ResultWrapper<T> resultWrapper) throws KrakenDataException {
		if (resultWrapper != null) {
			for (String error : resultWrapper.getError()) {
				throw new KrakenDataException("Error: " + error);
			}
			return resultWrapper.getResult();
		}
		return null;
	}
	
}
