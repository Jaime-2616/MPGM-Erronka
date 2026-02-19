package com.example.viajes.domain;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class XoteloResponse {
    @SerializedName("result")
    private Result result;

    public Result getResult() { return result; }

    public static class Result {
        @SerializedName("rates")
        private List<Rate> rates;

        public List<Rate> getRates() { return rates; }
    }

    public static class Rate {
        @SerializedName("name")
        private String name;
        @SerializedName("rate")
        private String rate;

        public String getName() { return name; }
        public String getRate() { return rate; }
    }
}