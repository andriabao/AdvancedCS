import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class Fib {
	
	static double phi = (1+Math.sqrt(5))/2;
	static BigDecimal phiInt = BigDecimal.valueOf(phi);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner s = new Scanner(System.in);
		
		int i = s.nextInt();
		
		System.out.println(fibCalc(i));
	}
	
	public static int fibCalc(int i) {
		
		return (int) Math.round(Math.pow(phi, i)
                / Math.sqrt(5));
		
//		BigDecimal neg = BigDecimal.valueOf(-1);
//		
//		
//		BigDecimal negChi = neg.multiply(chiInt);
//		
//		BigDecimal pow1 = chiInt.pow(i);
//						
//		BigDecimal pow2 = negChi.pow(i);
//		BigDecimal num = BigDecimal.valueOf(1).divide(pow2, 2, BigDecimal.ROUND_HALF_UP);
//				
//		BigDecimal diff = pow1.subtract(num);
//				
//		return diff.divide(BigDecimal.valueOf((long) Math.sqrt(5))).toBigInteger();
		
	}
	

}
