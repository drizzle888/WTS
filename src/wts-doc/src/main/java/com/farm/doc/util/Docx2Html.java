package com.farm.doc.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.converter.core.IImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Docx2Html {
	private final static Logger log = Logger.getLogger(Docx2Html.class);

	public static void main(String[] args) throws IOException {
		String html = new Docx2Html().doGenerateHTMLFile(new File("D:\\2.docx"), new IImageExtractor() {
			@Override
			public void extract(String imagePath, byte[] imageData) throws IOException {
				// TODO Auto-generated method stub
				System.out.println(imagePath);
			}
		}, new IURIResolver() {
			@Override
			public String resolve(String uri) {
				// TODO Auto-generated method stub
				System.out.println("#####" + uri);
				return "demo";
			}
		});
		System.out.println(formatHtmlForWCP(html));
	}

	public static String formatHtmlForWCP(String html) {
		Document jsoupdoc = Jsoup.parse(html);
		for (Element node : jsoupdoc.getElementsByTag("div")) {
			node.removeAttr("style");
		}
		for (Element node : jsoupdoc.getElementsByTag("a")) {
			node.removeAttr("href");
		}
		for (Element node : jsoupdoc.getElementsByTag("p")) {
			if (node.hasAttr("class")) {
				node.attr("style", "font-weight:bold;");
			}
		}
		for (Element node : jsoupdoc.getElementsByClass("X1")) {
			String newTag = node.toString().replaceAll("<p ", "<h1 ").replaceAll("</p> ", "</h1>");
			node.after(newTag);
			node.remove();
		}
		for (Element node : jsoupdoc.getElementsByClass("X2")) {
			String newTag = node.toString().replaceAll("<p ", "<h2 ").replaceAll("</p> ", "</h2>");
			node.after(newTag);
			node.remove();
		}
		for (Element node : jsoupdoc.getElementsByClass("X3")) {
			String newTag = node.toString().replaceAll("<p ", "<h3 ").replaceAll("</p> ", "</h3>");
			node.after(newTag);
			node.remove();
		}
		{
			//??????word??????
			jsoupdoc.getAllElements().removeAttr("class").removeAttr("style").removeAttr("id");
			jsoupdoc.getElementsByTag("div").tagName("p");
			Elements nodes = jsoupdoc.getElementsByTag("span");
			for (Element node : nodes) {
				node.after(node.text());
				node.remove();
			}
		}
		String outhtml = null;
		try {
			outhtml = jsoupdoc.getElementsByTag("body").toString().replace("<body>", "").replace("</body>", "");
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return outhtml != null ? outhtml : jsoupdoc.toString();
	}

	/**
	 * @param wordFile
	 *            docx??????
	 * @param doimgsave
	 *            ?????????????????????
	 * @param getimgurl
	 *            ????????????url?????????
	 * @return ??????html?????????
	 * @throws IOException
	 */
	public String doGenerateHTMLFile(File wordFile, IImageExtractor doimgsave, IURIResolver getimgurl)
			throws IOException {
		// ???????????????docx??????
		// if (wordFile.getName().indexOf(".docx") < 0) {
		// throw new FileFormatException(wordFile.getName() + " not is docx
		// file!");
		// }
		String str = null;
		XWPFDocument document = new XWPFDocument(new FileInputStream(wordFile));
		try {
			XHTMLOptions options = XHTMLOptions.create();// .indent( 4 );
			// ??????????????????
			options.setExtractor(doimgsave);
			// URI resolver
			options.URIResolver(getimgurl);
			ByteArrayOutputStream out = new ByteArrayOutputStream();// ???OutPutStream???????????????
			XHTMLConverter.getInstance().convert(document, out, options);
			str = out.toString();
		} catch (Exception e) {
			log.error(e+e.getMessage(), e);
			throw new RuntimeException("???????????????????????????????????????????????????????????????????????????????????????docx??????");
		} finally {
			document.close();
		}
		String htmlstr = Jsoup.parse(str).getElementsByTag("body").toString().replaceAll("</body>", "")
				.replaceAll("<body>", "");
		String regstr = "&lt;";
		return htmlstr.replaceAll(regstr, "(");
	}
}
