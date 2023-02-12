package bo.edu.ucb.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDto {
    private String to;
    private String from;
    private BigDecimal amount;

    public RequestDto() {
    }

    public RequestDto(String to, String from, BigDecimal amount) {
        this.to = to;
        this.from = from;
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", amount=" + amount +
                '}';
    }
}
