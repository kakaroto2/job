package com.yoloboo.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by ZHOU005 on 2016/1/16.
 */


public class BaseDao
{
	@Autowired
	protected SqlSessionTemplate sqlSession;
}
