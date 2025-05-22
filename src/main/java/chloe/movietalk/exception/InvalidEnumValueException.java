package chloe.movietalk.exception;

public class InvalidEnumValueException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidEnumValueException();

    private InvalidEnumValueException() {
        super(GlobalErrorCode.INVALID_ENUM_VALUE);
    }
}
