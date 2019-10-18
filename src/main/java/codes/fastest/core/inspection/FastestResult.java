package codes.fastest.core.inspection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastestResult {

	private List<String> errors = new ArrayList<>();
	
	public void addError(String error) {
		errors.add(error);
	}
	
	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}
	
}
