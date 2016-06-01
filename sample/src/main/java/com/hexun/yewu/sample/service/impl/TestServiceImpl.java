package com.hexun.yewu.sample.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hexun.yewu.sample.entity.Test;
import com.hexun.yewu.sample.mapper.TestMapper;
import com.hexun.yewu.sample.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Resource
	private TestMapper testMapper;
	
	@CacheEvict(key="#record.getCode",value="redisCache", allEntries = true) //D
	@Override
	public int deleteByPrimaryKey(String id) {
		return testMapper.deleteByPrimaryKey(id);
	}
	
	@CachePut(key="#record.userCode",value="redisCache") //放入缓存，缓存库redisCache,缓存key是ID的值
	@Override
	public Test insert(Test record) {
		testMapper.insert(record);
		return record;
	}
	
	@CacheEvict(key="#record.userCode",value="redisCache") //放入缓存，缓存库redisCache,缓存key是ID的值
	@Override
	public int insertSelective(Test record) {
		return testMapper.insertSelective(record);
	}

	@Override
	public Test selectByPrimaryKey(String id) {
		return testMapper.selectByPrimaryKey(id);
	}
	
	@CacheEvict(key="#record.getCode",value="redisCache")//更新缓存  
	@Override
	public int updateByPrimaryKeySelective(Test record) {
		return testMapper.updateByPrimaryKeySelective(record);
	}

	@CacheEvict(key="#record.getCode",value="redisCache")//更新缓存  
	@Override
	public int updateByPrimaryKey(Test record) {
		return testMapper.updateByPrimaryKey(record);
	}
	
	@Cacheable(key="#userCode",value="redisCache")//查询并放入缓存,持久化到redis中
	@Override
	public Test findOneByCode(String userCode) {
		System.out.println("查询数据库");
		return testMapper.findOneByCode(userCode);
	}
	
	@CacheEvict(value="redisCache",allEntries=true)//清空缓存  
    public void reload() {  
    } 
	
}
