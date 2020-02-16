package com.farm.doc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.rtf.RtfWriter2;

public class Html2Word {
	private static final Logger log = Logger.getLogger(Html2Word.class);
	public static void main(String[] args) {
		// 创建文件
		File f = new File("D:/buffer/demo.pdf");
		toWord("asdf", f);
	}

	public static File toWord(String html, File file) {
		// 创建itext文档对象
		Document document = new Document(PageSize.A4);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			// 创建RtfWriter对象
			RtfWriter2.getInstance(document, fos);
			document.open();
			// 添加html代码
			addHtmlContent(document, html, 500);
		} catch (Exception e) {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			log.error(e+e.getMessage(), e);
			System.err.println(e.getMessage());
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return null;
	}

	public static void addHtmlContent(Object element, String html, float imgWidth)
			throws IOException, DocumentException {
		StyleSheet ss = new StyleSheet();
		List<?> htmlList = HTMLWorker.parseToList(new StringReader(html), ss);
		for (int i = 0; i < htmlList.size(); i++) {
			Element e = (Element) htmlList.get(i);
			for (Object object : e.getChunks()) {
				if (object instanceof Chunk) {
					Chunk ch = (Chunk) object;
					if (ch.getImage() != null) {
						float width = imgWidth;
						float height = ch.getImage().getHeight() / ch.getImage().getWidth() * width;
						ch.getImage().scaleAbsolute(width, height);
					}
				}
			}
			if (element instanceof Cell) {
				((Cell) element).add(e);
			}
			if (element instanceof Paragraph) {
				((Paragraph) element).add(e);
			}
			if (element instanceof Document) {
				((Document) element).add(e);
			}
		}
	}

}
