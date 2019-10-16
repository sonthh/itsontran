package test;

public class TestNumber {
	public static void main(String[] args) {
		float fNumber1 = 0;
		float fNumber2 = 0;
		int iNumber = 0;
		System.out.println(1 / fNumber1); 			/* Infinity		*/
		System.out.println(fNumber1 / fNumber2); 	/* NaN       	*/
		System.out.println(1 / iNumber);  			/* Exception	*/
		/* WHY?*/
		/* Are you understand? */
	}
}
