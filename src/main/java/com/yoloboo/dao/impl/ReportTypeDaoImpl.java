package com.yoloboo.dao.impl;

import com.yoloboo.dao.ReportTypeDao;
import com.yoloboo.models.ReportTypeModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/27.
 */
@Repository
public class ReportTypeDaoImpl extends BaseDao implements ReportTypeDao {
    @Override
    public List<ReportTypeModel> getReportTypeList() {
        return (List<ReportTypeModel>)sqlSession.selectList("ReportTypeDao.getReportTypeList");
    }
}
