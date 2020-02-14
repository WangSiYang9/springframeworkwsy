package com.spring.wsy.demo.service.impl;

import com.spring.wsy.demo.service.IModifyService;
import com.spring.wsy.framework.annotation.ServiceWsy;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-02 20:50
 */
@ServiceWsy
public class ModifyService implements IModifyService {

	/**
	 * 增加
	 */
	public String add(String name,String addr) throws Exception {
		throw new Exception("这是故意抛的异常！！");
		//return "modifyService add,name=" + name + ",addr=" + addr;
	}

	/**
	 * 修改
	 */
	public String edit(Integer id,String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	/**
	 * 删除
	 */
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}
	
}
