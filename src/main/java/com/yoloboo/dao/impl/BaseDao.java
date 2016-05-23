package com.yoloboo.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by CoderZhao on 2016/1/18.
 */
public class BaseDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;
}
