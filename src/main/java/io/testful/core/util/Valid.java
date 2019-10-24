package io.testful.core.util;

public class Valid {

	public static boolean onlyOneTrue(boolean ... values) {
		
		int cnt = 0;
		
	    for (boolean value : values) {
	        if (value) cnt++;
	    }
		
		return cnt == 1;
	}
	
	
}
