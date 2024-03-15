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
    private Double snowFeeValue;
    private Double rainFeeValue;

    private Double tempSmallFee;
    private Double tempBigFee;
    private Double windWarning;
    private Double windFee;


    public Double getTempSmallFeeValue() {
        return tempSmallFeeValue;
    }

    public void setTempSmallFeeValue(Double tempSmallFeeValue) {
        this.tempSmallFeeValue = tempSmallFeeValue;
    }

    public Double getTempBigFeeValue() {
        return tempBigFeeValue;
    }

    public void setTempBigFeeValue(Double tempBigFeeValue) {
        this.tempBigFeeValue = tempBigFeeValue;
    }

    public Double getWindFeeValue() {
        return windFeeValue;
    }

    public void setWindFeeValue(Double windFeeValue) {
        this.windFeeValue = windFeeValue;
    }

    public Double getTempSmallFee() {
        return tempSmallFee;
    }

    public void setTempSmallFee(Double tempSmallFee) {
        this.tempSmallFee = tempSmallFee;
    }

    public Double getTempBigFee() {
        return tempBigFee;
    }

    public void setTempBigFee(Double tempBigFee) {
        this.tempBigFee = tempBigFee;
    }

    public Double getWindWarning() {
        return windWarning;
    }

    public void setWindWarning(Double windWarning) {
        this.windWarning = windWarning;
    }

    public Double getWindFee() {
        return windFee;
    }

    public void setWindFee(Double windFee) {
        this.windFee = windFee;
    }

    public Double getSnowFeeValue() {
        return snowFeeValue;
    }

    public void setSnowFeeValue(Double snowFeeValue) {
        this.snowFeeValue = snowFeeValue;
    }

    public Double getRainFeeValue() {
        return rainFeeValue;
    }

    public void setRainFeeValue(Double rainFeeValue) {
        this.rainFeeValue = rainFeeValue;
    }
}

