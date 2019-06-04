package com.financialapp.repository;

import com.financialapp.model.MaeTotalData;

import java.util.List;

public interface MaeCrawler {
    List<MaeTotalData> findMae();
}
