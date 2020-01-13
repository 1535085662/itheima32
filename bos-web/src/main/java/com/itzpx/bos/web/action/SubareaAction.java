package com.itzpx.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itzpx.bos.domain.Region;
import com.itzpx.bos.domain.Subarea;
import com.itzpx.bos.service.ISubareaService;
import com.itzpx.bos.utils.FileUtils;
import com.itzpx.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Resource
	private ISubareaService subareaService;

	/**
	 * 添加分区
	 */
	public String add() {
		
		subareaService.save(model);
		return LIST;
	}
	/**
	 * 分页查询
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加过滤条件
		String addresskey = model.getAddresskey();
		if (StringUtils.isNoneBlank(addresskey)) {
			//动态添加过滤条件，根据关键字进行模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			//使用表关联查询，省，市，区，这三个值都在bc_region表中，所以需要关联
			//参数一"region"代表Subarea.java中定义的Region属性，参数二代表别名
			//select * from bc_subarea s join bc_region r on s.region.id = r.id where r.province = '河北省'；
			dc.createAlias("region", "r");
			if (StringUtils.isNoneBlank(province)) {
				//动态添加过滤条件，根据关键字进行模糊查询
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if (StringUtils.isNoneBlank(city)) {
				//动态添加过滤条件，根据关键字进行模糊查询
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if (StringUtils.isNoneBlank(district)) {
				//动态添加过滤条件，根据关键字进行模糊查询
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","decidedzone","subareas"});
		return NONE;
	}
	/**
	 * 导出功能
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		//查询所有分区
		List<Subarea> finAll = subareaService.finAll();
		//内存中创建一个Excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("分区数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		for (Subarea subarea : finAll) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//输出流进行文件下载（一个流，两个头）
		String filename = "分区数据.xls";
		//根据文件名获取输出流类型
		String conentTyoe = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		//设置输出流类型
		ServletActionContext.getResponse().setContentType(conentTyoe);
		//获取浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//调用工具类对文件名进行包装，主要针对中文乱码问题
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		//进行文件下载
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		
		workbook.write(out);
		return NONE;
	}
	/**
	 * 返回区域分布highcharts数据
	 * @return
	 */
	public String findSubareasGroupByProvince() {
		List<Object> list = subareaService.findSubareasGroupByProvince();
		this.java2Json(list, new String[] {});
		return NONE;
	}
	/**
	 * 查询说有未关联到定区的分区，返回json
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssociation();
		this.java2Json(list, new String[] {"decidedzone","region"});
		return NONE;
	}
	
	private String decidedzoneId;
	/**
	 * 	根据定区ID查看关联分区
	 */
	public String findListByDecidedzoneId() {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[] {"decidedzone","subareas"});
		return NONE;
	}
	public String getDecidedzoneId() {
		return decidedzoneId;
	}
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
}
