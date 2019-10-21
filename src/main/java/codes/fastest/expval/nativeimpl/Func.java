package codes.fastest.expval.nativeimpl;

public class Func {

	public static boolean isInt(Object value) {
		if(value != null) {
			try {
				Integer.valueOf(value.toString());
				return true;
			} catch(NumberFormatException nfe) {
				return false;
			}
		}
		return false;
	}

	public static boolean notNull(Object value) {
		return value != null;
	}

	public static boolean notEmpty(Object value) {
		return value != null && value.toString().trim().length() > 0;
	}
	
	public static boolean min(int min, Object value) {
		
		if(value == null) return false;
		
		if(value.getClass() == String.class) {
			return value.toString().length() > min; 
		} else if(value.getClass().isAssignableFrom(Number.class)) {
			int val = ((Number)value).intValue();
			return val > min;
		}
		
		return false;
	}
	
	public static boolean size(int min, int max, Object value) {
		
		if(value == null) return false;
		
		if(value.getClass() == String.class) {
			return value.toString().length() > min && value.toString().length() < max; 
		} else if(value.getClass().isAssignableFrom(Number.class)) {
			int val = ((Number)value).intValue();
			return val > min && val < max;
		}
		
		return false;
	}
	
}
