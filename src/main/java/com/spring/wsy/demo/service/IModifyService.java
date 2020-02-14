package com.spring.wsy.demo.service;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-02 20:50
 */
public interface IModifyService {

	/**
	 * 增加
	 */
	public String add(String name, String addr) throws Exception;
	
	/**
	 * 修改
	 */
	public String edit(Integer id, String name);
	
	/**
	 * 删除
	 */
	public String remove(Integer id);
	
}
