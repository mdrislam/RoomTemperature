
package com.eoms.roomtemperature.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public long getDeg() {
        return Math.round(deg);
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

}
