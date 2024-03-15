package MJ.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExtraFeeCalculationRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tempSmallFeeValue;
    private Double tempBigFeeValue;
    private Double warningWindFeeValue;
    private Double windFeeValue;

    private Double tempSmallFee;
    private Double tempBigFee;
    private Double windWarning;
    private Double windFee;

    public Double getTempSmallFeeValue() {
        return tempSmallFeeValue;
    }

    public Double getTempBigFeeValue() {
        return tempBigFeeValue;
    }

    public Double getWarningWindFeeValue() {
        return warningWindFeeValue;
    }

    public Double getWindFeeValue() {
        return windFeeValue;
    }

    public Double getTempSmallFee() {
        return tempSmallFee;
    }

    public Double getTempBigFee() {
        return tempBigFee;
    }

    public Double getWindWarning() {
        return windWarning;
    }

    public Double getWindFee() {
        return windFee;
    }
}

