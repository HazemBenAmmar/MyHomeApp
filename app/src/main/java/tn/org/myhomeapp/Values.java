package tn.org.myhomeapp;

import org.jetbrains.annotations.NotNull;

public class Values {

    public float hum;
    public float temp;

    @NotNull
    @Override
    public String toString() {
        return "Values{" +
                "hum=" + hum +
                ", temp=" + temp +
                '}';
    }
}
