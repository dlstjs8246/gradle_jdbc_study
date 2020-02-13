package gradle_jdbc_study.ui.exception;

@SuppressWarnings("serial")
public class InvalidCheckExcepation extends RuntimeException {

	public InvalidCheckExcepation() {
		super("공백이 존재합니다");
	}

	public InvalidCheckExcepation(Throwable cause) {
		super("공백이 존재합니다. ", cause);
	}
	
}
