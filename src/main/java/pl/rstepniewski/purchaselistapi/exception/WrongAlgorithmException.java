package pl.rstepniewski.purchaselistapi.exception;

public class WrongAlgorithmException extends RuntimeException{
    public WrongAlgorithmException(String message) {
        super(message);
    }
}