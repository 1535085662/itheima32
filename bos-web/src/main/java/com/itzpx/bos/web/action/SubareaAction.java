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
	 * ��ӷ���
	 */
	public String add() {
		
		subareaService.save(model);
		return LIST;
	}
	/**
	 * ��ҳ��ѯ
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//��̬��ӹ�������
		String addresskey = model.getAddresskey();
		if (StringUtils.isNoneBlank(addresskey)) {
			//��̬��ӹ������������ݹؼ��ֽ���ģ����ѯ
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			//ʹ�ñ������ѯ��ʡ���У�����������ֵ����bc_region���У�������Ҫ����
			//����һ"region"����Subarea.java�ж����Region���ԣ��������������
			//select * from bc_subarea s join bc_region r on s.region.id = r.id where r.province = '�ӱ�ʡ'��
			dc.createAlias("region", "r");
			if (StringUtils.isNoneBlank(province)) {
				//��̬��ӹ������������ݹؼ��ֽ���ģ����ѯ
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if (StringUtils.isNoneBlank(city)) {
				//��̬��ӹ������������ݹؼ��ֽ���ģ����ѯ
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if (StringUtils.isNoneBlank(district)) {
				//��̬��ӹ������������ݹؼ��ֽ���ģ����ѯ
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","decidedzone","subareas"});
		return NONE;
	}
	/**
	 * ��������
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		//��ѯ���з���
		List<Subarea> finAll = subareaService.finAll();
		//�ڴ��д���һ��Excel���
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("��������");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("�������");
		headRow.createCell(1).setCellValue("��ʼ���");
		headRow.createCell(2).setCellValue("�������");
		headRow.createCell(3).setCellValue("λ����Ϣ");
		headRow.createCell(4).setCellValue("ʡ����");
		for (Subarea subarea : finAll) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//����������ļ����أ�һ����������ͷ��
		String filename = "��������.xls";
		//�����ļ�����ȡ���������
		String conentTyoe = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		//�������������
		ServletActionContext.getResponse().setContentType(conentTyoe);
		//��ȡ���������
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//���ù�������ļ������а�װ����Ҫ���������������
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		//�����ļ�����
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		
		workbook.write(out);
		return NONE;
	}
	/**
	 * ��������ֲ�highcharts����
	 * @return
	 */
	public String findSubareasGroupByProvince() {
		List<Object> list = subareaService.findSubareasGroupByProvince();
		this.java2Json(list, new String[] {});
		return NONE;
	}
	/**
	 * ��ѯ˵��δ�����������ķ���������json
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssociation();
		this.java2Json(list, new String[] {"decidedzone","region"});
		return NONE;
	}
	
	private String decidedzoneId;
	/**
	 * 	���ݶ���ID�鿴��������
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
