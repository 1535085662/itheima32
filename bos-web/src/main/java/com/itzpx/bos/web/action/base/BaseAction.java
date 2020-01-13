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
 * 	���ֲ�ͨ��ʵ��
 * @author ����ү
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	//��ҳ��ѯ�����ȡ
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
	 * 	��ָ����Java����ת��Ϊjson,����Ӧ���ͻ���ҳ��
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
	/////��ҳ��ѯ��ȡ����
	
	
	
	
	public static final String HOME = "home";
	public static final String LIST = "list";
	//ģ�Ͷ���
	protected T model;
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	//��̬��ȡʵ�����ͣ�ͨ�����䴴��model����User user = new User();
	public BaseAction() {
		//1.��ȡ��������
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//2.��ȡBaseAction�������ķ�������
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//3.Type ����תΪ Class<T>ʵ����
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		///////////////////////��ҳ��ѯDetachedCriteria��ֵDetachedCriteria.forClass(***.class);
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		//////////////////////
		//4.ͨ�����䴴������
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
