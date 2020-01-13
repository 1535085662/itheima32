package com.itzpx.bos.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.itzpx.bos.utils.PinYin4jUtils;

public class PinYin4JTest {
	@Test
	public void test1() {
		String p = "广东省";
		String c = "河源市";
		String d = "源城区";
		p = p.substring(0,p.length()-1);
		c = c.substring(0,c.length()-1);
		d = d.substring(0,d.length()-1);
		String ss = p+c+d;
		String[] headByString = PinYin4jUtils.getHeadByString(ss);
		String join = StringUtils.join(headByString);
		System.out.println(join);
	}
}
