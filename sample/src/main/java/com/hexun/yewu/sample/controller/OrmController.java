package com.hexun.yewu.sample.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.hexun.framework.core.controller.DefaultBaseController;
import com.hexun.framework.core.redis.RedisUtils;
import com.hexun.framework.core.utils.IDUtils;
import com.hexun.framework.core.utils.RespEnum;
import com.hexun.yewu.sample.entity.Test;
import com.hexun.yewu.sample.service.TestService;

/**
 * 后台管理部分
 * @author zhoudong
 *
 */
@Controller
public class OrmController extends DefaultBaseController {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private TestService testService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value="orm",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView orm() {
		return getModelAndView("orm");
	}
	
	/**
	 * 入库
	 * @return
	 */
	@RequestMapping(value="orm_save",method={RequestMethod.POST})
	public @ResponseBody Map<String, Object> saveTest(Test test){
		test.setId(IDUtils.getId());
		log.info("保存数据如下：{}",JSON.toJSONString(test));
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			testService.insert(test);
			map.put("respCode", RespEnum.RESP_SUCCESS.getCode());
			map.put("respMsg", RespEnum.RESP_SUCCESS.getCnMsg());
		} catch (Exception e) {
			map.put("respCode", RespEnum.RESP_FAIL.getCode());
			map.put("respMsg", RespEnum.RESP_FAIL.getCnMsg());
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 根据Code查询一个
	 * @return
	 */
	@RequestMapping(value="find_one_by_user_code/{userCode}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, Object> findOneByCode(@PathVariable String userCode){
		
		log.info("查询条件，userCode:{}",userCode);
		Object test = testService.findOneByCode(userCode);
		log.info("查询结束，查询结果：{}",JSON.toJSONString(test));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("respCode", RespEnum.RESP_SUCCESS.getCode());
		map.put("respMsg", RespEnum.RESP_SUCCESS.getCnMsg());
		map.put("result", test);
		return map;
	}
	
	/**
	 * 根据Code查询一个
	 * @return
	 */
	@RequestMapping(value="find_one_by_user_code_jsonp/{userCode}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JSONPObject findOneByCodeJsonp(@PathVariable String userCode,String callback){
		
		log.info("查询条件，userCode:{}",userCode);
		Object test = testService.findOneByCode(userCode);
		log.info("查询结束，查询结果：{}",JSON.toJSONString(test));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("respCode", RespEnum.RESP_SUCCESS.getCode());
		map.put("respMsg", RespEnum.RESP_SUCCESS.getCnMsg());
		map.put("result", test);
		return new JSONPObject(callback, map);
	}
	
	/**
	 * 清空redis
	 * @return
	 */
	@RequestMapping(value="flushDb",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, Object> flushDB(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("respMsg", RedisUtils.flushDB());
		return map;
	}
	
	/**
	 * 清空缓存
	 * @return
	 */
	@RequestMapping(value="reloadCache",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<String, Object> reloadCache(){
		Map<String, Object> map = new HashMap<String, Object>();
		testService.reload();
		map.put("respMsg", "SUCCESS");
		return map;
	}
}
