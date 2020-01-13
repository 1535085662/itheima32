package com.itzpx.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Region;
import com.itzpx.bos.service.IRegionService;
import com.itzpx.bos.utils.PageBean;
import com.itzpx.bos.utils.PinYin4jUtils;
import com.itzpx.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 区域管理
 * @author 曾大爷
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//属性驱动，接受上传文件
	private File regionFile;
	@Autowired
	private IRegionService regionService;
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	/**
	 * 区域导入
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException {
		List<Region> regionList = new ArrayList<Region>();
		System.out.println(regionFile);
		//使用POI解析Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//根据名称获得指定的Sheet对象
		HSSFSheet hssfSheet = workbook.getSheet("Sheet1");
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			//
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			//拼音
			province = province.substring(0,province.length()-1);
			city = city.substring(0,city.length()-1);
			district = district.substring(0,district.length()-1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);//城市简码
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");//城市编码
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			
			regionList.add(region);
		}
		regionService.saveBatch(regionList);
		return NONE;
	}
//	private int page;
//	private int rows;

	/**
	 * 	注释掉的代码都是提取到BaseAction中
	 * 	分页查询方法
	 * 	分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
//		PageBean pageBean = new PageBean();
//		pageBean.setCurrentPage(page);
//		pageBean.setPageSize(rows);
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
//		pageBean.setDetachedCriteria(detachedCriteria);
		regionService.pageQuery(pageBean);
		this.java2Json(pageBean,new String [] {"currentPage","detachedCriteria","pageSize","subareas"});//调用java2Json做数据转化并响应到页面
		//pageBean赋值完毕，转为json格式
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String [] {"currentPage","detachedCriteria","pageSize"});
//		String json = JSONObject.fromObject(pageBean,jsonConfig).toString();
//		ServletActionContext.getResponse().setContentType("text/json;charset=utf8");
//		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	/**
	 * 
	 * @return
	 */
	public String listajax() {
		List<Region> list = null;
		if (StringUtils.isNoneBlank(q)) {
			list = regionService.finListByq(q);
		}else{
			list = regionService.finAll();
		}
		this.java2Json(list, new String[] {
				"subareas","province","city","district","postcode","shortcode","citycode"});
		return NONE;
	}
	
}
