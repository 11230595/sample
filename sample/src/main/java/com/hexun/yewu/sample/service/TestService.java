package com.hexun.yewu.sample.service;

import com.hexun.yewu.sample.entity.Test;

public interface TestService {
	int deleteByPrimaryKey(String id);

    Test insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    public void reload();//清楚缓存
    
	Test findOneByCode(String userCode);
	
}
