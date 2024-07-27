
package com.example.indexBuild;

public class customExceptions {

	// custom Messages service
	public static class IndexNotFoundException extends Exception {
	    public IndexNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public static class StockNotFoundException extends Exception {
	    public StockNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public static class IndexAlreadyExistsException extends Exception {
	    public IndexAlreadyExistsException(String message) {
	        super(message);
	    }
	}
	
	
	public static class StockAlreadyExistsException extends Exception {
	    public StockAlreadyExistsException(String message) {
	        super(message);
	    }
	}
	
	public static class ValidationException extends Exception {
	    public ValidationException(String message) {
	        super(message);
	    }
	}
	
	public static class TwoMembersException extends Exception {
	    public TwoMembersException(String message) {
	        super(message);
	    }
	}
}