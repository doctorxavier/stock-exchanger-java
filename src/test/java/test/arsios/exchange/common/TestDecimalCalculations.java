package test.arsios.exchange.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arsios.exchange.utils.Utilities;


public class TestDecimalCalculations {
	
	private static final Logger		LOGGER		= LoggerFactory.getLogger(TestDecimalCalculations.class);
	private static final long		ITERS 		= 300000000L;
	
	@Test
	@Ignore
	public void testBigDecimal() {
		long start = 0L;
		long elapsed = 0L;
		
		start = System.currentTimeMillis();
		
		int res = 0;
		final BigDecimal orig = new BigDecimal("362.2");
		// 1.5%
		final BigDecimal mult = new BigDecimal("0.015");
		for (int i = 0; i < ITERS; ++i) {
			final BigDecimal result = orig.multiply(mult, MathContext.DECIMAL64);
			if (result != null) {
				res++;
			}
		}
		LOGGER.info(Integer.valueOf(res).toString());
		
		elapsed = System.currentTimeMillis() - start;
		LOGGER.info("Time elapsed: " + Utilities.parseMilliseconds(elapsed));
	}
	
	@Test
	@Ignore
	public void testDoubleLong() {
		long start = 0L;
		long elapsed = 0L;
		
		start = System.currentTimeMillis();
		
		int res = 0;
		// 362.2 in cents
		final double orig = 362200;
		// 1.5%
		final double mult = 0.015;
		for (int i = 0; i < ITERS; ++i) {
			final double result = Math.nextUp(orig * mult);
			if (result != 543) {
				// 543.3 cents actually
				res++; 
			}
		}
		LOGGER.info(Integer.valueOf(res).toString());
		
		elapsed = System.currentTimeMillis() - start;
		LOGGER.info("Time elapsed: " + Utilities.parseMilliseconds(elapsed));
	}
	
	@Test
	@Ignore
	public void test2() {		
		MathContext mc1 = new MathContext(8, RoundingMode.CEILING);
		MathContext mc2 = new MathContext(8, RoundingMode.DOWN);
		MathContext mc3 = new MathContext(8, RoundingMode.FLOOR);
		MathContext mc4 = new MathContext(8, RoundingMode.HALF_DOWN);
		MathContext mc5 = new MathContext(8, RoundingMode.HALF_EVEN);
		MathContext mc6 = new MathContext(8, RoundingMode.HALF_UP);
		MathContext mc7 = new MathContext(8, RoundingMode.UNNECESSARY);
		MathContext mc8 = new MathContext(8, RoundingMode.UP);
		
		BigDecimal n1 = new BigDecimal(7.0);
		BigDecimal n2 = new BigDecimal(3.0);
		//BigDecimal n3 = n1.divide(n2, MathContext.DECIMAL128).toPlainString();
		
		LOGGER.info(n1.divide(n2, MathContext.DECIMAL128).toPlainString());
		
		LOGGER.info("");
		
		LOGGER.info(n1.divide(n2, mc1).toPlainString());
		LOGGER.info(n1.divide(n2, mc2).toPlainString());
		LOGGER.info(n1.divide(n2, mc3).toPlainString());
		LOGGER.info(n1.divide(n2, mc4).toPlainString());
		LOGGER.info(n1.divide(n2, mc5).toPlainString());
		LOGGER.info(n1.divide(n2, mc6).toPlainString());
		//LOGGER.info(n1.divide(n2, mc7).toPlainString());
		LOGGER.info(n1.divide(n2, mc8).toPlainString());
		
		LOGGER.info("");
		
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_CEILING).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_DOWN).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_FLOOR).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_HALF_UP).toPlainString());
		LOGGER.info(n1.divide(n2, BigDecimal.ROUND_UP).toPlainString());
		
		LOGGER.info("");
		
		double n3 = 7.0 / 3;
		
		LOGGER.info(Double.valueOf(n3).toString());
		
		//LOGGER.info(new BigDecimal(new BigDecimal(0.01), BigDecimal.ROUND_DOWN));
		
	}
	
	@Test
	public void testFormat() {
		double n1 = 281.83992114802;
		BigDecimal n2 = BigDecimal.valueOf(n1);
		double amount = formatAmount(n2);
		
		LOGGER.info(n2.toPlainString());
		LOGGER.info(Double.valueOf(amount).toString());
	}
	
	public static double formatAmount(BigDecimal bigd) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(4);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.DOWN);
		return Double.valueOf(formater.format(bigd.doubleValue()));
	}
	
	public static double formatPrice(BigDecimal bigd) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.DOWN);
		return Double.valueOf(formater.format(bigd.doubleValue()));
	}
}
