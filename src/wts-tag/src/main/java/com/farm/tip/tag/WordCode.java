package com.farm.tip.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.web.tag.BasePathTaget;

public class WordCode extends TagSupport {
	private int code;
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BasePathTaget.class);
	private static final Map<Integer, String> codeMap = new HashMap<>();

	{
		codeMap.put(1, "A");
		codeMap.put(2, "B");
		codeMap.put(3, "C");
		codeMap.put(4, "D");
		codeMap.put(5, "E");
		codeMap.put(6, "F");
		codeMap.put(7, "G");
		codeMap.put(8, "H");
		codeMap.put(9, "I");
		codeMap.put(10, "J");
		codeMap.put(11, "K");
		codeMap.put(12, "L");
		codeMap.put(13, "M");
		codeMap.put(14, "N");
		codeMap.put(15, "O");
		codeMap.put(16, "P");
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {

		JspWriter jspw = this.pageContext.getOut();
		try {
			jspw.print(codeMap.get(code));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
