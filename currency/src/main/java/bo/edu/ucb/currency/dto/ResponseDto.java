package bo.edu.ucb.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {
    private boolean success;
    private RequestDto query;
    private String date;
    private BigDecimal result;

    public ResponseDto() {
    }

    public ResponseDto(boolean success, RequestDto query, String date, BigDecimal result) {
        this.success = success;
        this.query = query;
        this.date = date;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RequestDto getQuery() {
        return query;
    }

    public void setQuery(RequestDto query) {
        this.query = query;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "success=" + success +
                ", query=" + query +
                ", date='" + date + '\'' +
                ", result=" + result +
                '}';
    }
}
