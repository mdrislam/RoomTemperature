
package com.eoms.roomtemperature.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    public long getTemp() {
        return Math.round(temp);
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public long getPressure() {
        return Math.round(pressure);
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public long getTempMin() {
        return Math.round(tempMin);
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public long getTempMax() {

        return Math.round(tempMax);
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

}
