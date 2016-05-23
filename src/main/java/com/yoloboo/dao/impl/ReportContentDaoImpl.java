package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ReportBean;
import com.yoloboo.dao.ReportContentDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
@Repository
public class ReportContentDaoImpl extends BaseDao implements ReportContentDao
{
    @Transactional
    @Override
    public void AddReportContent(ReportBean rb) {
        sqlSession.insert("ReportContentDao.addReportContent",rb);
    }

    @Transactional
    @Override
    public void DeleteReportContent(Long rId) {
        sqlSession.delete("ReportContentDao.deleteReportContent",rId);

    }
}
