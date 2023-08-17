package projects.exception;

@SuppressWarnings("serial")
public class DbException extends RuntimeException{

	public DbException() {
		// TODO Auto-generated constructor stub
	}

	public DbException(String string) {
		super(string);
	}	
	public DbException(Throwable cause) {
		super(cause);
	}
	public DbException(String string, Throwable cause) {
		super(string, cause);
	}
	
	
	
}
