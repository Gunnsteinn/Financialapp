package com.financialapp.model;

import java.util.List;

public class MaeTotalData {

    String evolucionForexChartPaint;
    List<Mae> exchange;

    public MaeTotalData(String evolucionForexChartPaint, List<Mae> exchange) {
        this.evolucionForexChartPaint = evolucionForexChartPaint;
        this.exchange = exchange;
    }

    public String getEvolucionForexChartPaint() {
        return evolucionForexChartPaint;
    }

    public void setEvolucionForexChartPaint(String evolucionForexChartPaint) {
        this.evolucionForexChartPaint = evolucionForexChartPaint;
    }

    public List<Mae> getExchange() {
        return exchange;
    }

    public void setExchange(List<Mae> exchange) {
        this.exchange = exchange;
    }



}
