package demo.product.exception;

public class ProductApiException extends RuntimeException {
    public ProductApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
