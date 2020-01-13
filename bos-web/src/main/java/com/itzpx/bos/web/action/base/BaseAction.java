package com.itzpx.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itzpx.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 	表现层通用实现
 * @author 曾大爷
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	//分页查询代码抽取
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;
//	protected int page;
//	protected int rows;
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	/**
	 * 	将指定的Java对象转化为json,并响应到客户端页面
	 * @param o
	 * @param exclueds
	 */
	public void java2Json(Object o,String[] exclueds) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//
	public void java2Json(List l,String[] exclueds) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONArray.fromObject(l,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/////分页查询抽取结束
	
	
	
	
	public static final String HOME = "home";
	public static final String LIST = "list";
	//模型对象
	protected T model;
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	//动态获取实体类型，通过反射创建model对象，User user = new User();
	public BaseAction() {
		//1.获取父类类型
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//2.获取BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//3.Type 向下转为 Class<T>实现类
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		///////////////////////分页查询DetachedCriteria赋值DetachedCriteria.forClass(***.class);
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		//////////////////////
		//4.通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
