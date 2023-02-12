package bo.edu.ucb.currency.dto;

public class ErrorDto {
    private ErrorResponseDto error;

    public ErrorDto() {
    }

    public ErrorDto(ErrorResponseDto error) {
        this.error = error;
    }

    public ErrorResponseDto getError() {
        return error;
    }

    public void setError(ErrorResponseDto error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "error=" + error +
                '}';
    }
}
