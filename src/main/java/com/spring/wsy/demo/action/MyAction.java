package com.spring.wsy.demo.action;


import com.spring.wsy.demo.service.IModifyService;
import com.spring.wsy.demo.service.IQueryService;
import com.spring.wsy.framework.annotation.AutowiredWsy;
import com.spring.wsy.framework.annotation.ControllerWsy;
import com.spring.wsy.framework.annotation.RequestMappingWsy;
import com.spring.wsy.framework.annotation.RequestParamWsy;
import com.spring.wsy.framework.webmvc.servlet.ModelAndViewWsy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-02 20:50
 */
@ControllerWsy
@RequestMappingWsy("/web")
public class MyAction {

	@AutowiredWsy
	IQueryService queryService;
	@AutowiredWsy
	IModifyService modifyService;

	@RequestMappingWsy("/query.json")
	public ModelAndViewWsy query(HttpServletRequest request, HttpServletResponse response,
								 @RequestParamWsy("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	@RequestMappingWsy("/add*.json")
	public ModelAndViewWsy add(HttpServletRequest request,HttpServletResponse response,
			   @RequestParamWsy("name") String name,@RequestParamWsy("addr") String addr){
		String result = null;
		try {
			result = modifyService.add(name,addr);
			return out(response,result);
		} catch (Exception e) {
//			e.printStackTrace();
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("detail",e.getMessage());
//			System.out.println(Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]",""));
			return new ModelAndViewWsy("500",model);
		}

	}
	
	@RequestMappingWsy("/remove.json")
	public ModelAndViewWsy remove(HttpServletRequest request,HttpServletResponse response,
		   @RequestParamWsy("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@RequestMappingWsy("/edit.json")
	public ModelAndViewWsy edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParamWsy("id") Integer id,
			@RequestParamWsy("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private ModelAndViewWsy out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
