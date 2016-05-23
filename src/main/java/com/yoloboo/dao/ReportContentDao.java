package com.yoloboo.dao;

import com.yoloboo.controller.bean.ReportBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
public interface ReportContentDao
{
    @Transactional
    void AddReportContent(ReportBean rb);
    @Transactional
    void DeleteReportContent(Long rId);
}
