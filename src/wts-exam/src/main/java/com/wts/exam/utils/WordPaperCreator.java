package com.wts.exam.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.parameter.FarmParameterService;
import com.farm.util.spring.BeanFactory;
import com.farm.util.web.FarmHtmlUtils;
import com.wts.exam.domain.Material;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.ex.AnswerUnit;
import com.wts.exam.domain.ex.ChapterUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.SubjectUnit;

public class WordPaperCreator {

	// 正文字体
	private static int STANDARD_FONT_SIZE = 12;
	// 正文缩进
	private static int STANDARD_INDENTATION = 300;
	// 字体
	private static String STANDARD_FONT_FAMILY = "微软雅黑";
	// 附加描述小字體
	private static int SMALL_FONT_SIZE = 10;
	// 答卷标题
	private static int TITLE_FONT_SIZE = 18;
	// 標題一
	private static int H1_FONT_SIZE = 16;
	private static int H1_INDENTATION = 300;
	// 標題二
	private static int H2_FONT_SIZE = 14;
	private static int H2_INDENTATION = 600;
	// 標題三
	private static int H3_FONT_SIZE = 12;
	private static int H3_INDENTATION = 900;
	// 答卷答案
	private static List<String[]> answers = new ArrayList<>();

	/**
	 * 构造答卷word
	 * 
	 * @param document
	 */
	synchronized public static void initWordPaper(XWPFDocument document, PaperUnit paper) {
		answers = new ArrayList<>();
		// 创建页眉
		createHeader(document, FarmParameterService.getInstance().getParameter("config.sys.title"));
		// 创建页脚
		createFooter(document, "wwww.wcpdoc.com", "wts");
		// 创建答卷頭標題
		initPaperTitle(document, paper);
		// 创建答卷信息
		initpaperInfo(document, paper);
		// 创建答卷说明paper.info.papernote
		// 超文本内容
		if (StringUtils.isNotBlank(paper.getInfo().getPapernote())) {
			initpaperNote(document, paper.getInfo().getPapernote());
		}
		// 迭代章節h1
		for (ChapterUnit chapter : paper.getChapters()) {
			initChapterMain(document, chapter, H1_FONT_SIZE, H1_INDENTATION);
			// 迭代章節h2
			for (ChapterUnit chapter2 : chapter.getChapters()) {
				initChapterMain(document, chapter2, H2_FONT_SIZE, H2_INDENTATION);
				// 迭代章節h3
				for (ChapterUnit chapter3 : chapter2.getChapters()) {
					initChapterMain(document, chapter3, H3_FONT_SIZE, H3_INDENTATION);
				}
			}
		}
		// 写入答案
		initpaperAnswer(document, answers);
	}

	/**
	 * 創建答卷答案
	 * 
	 * @param document
	 * @param paper
	 */
	public static void initpaperAnswer(XWPFDocument document, List<String[]> answers) {
		// answers[0] 1:大题号，2小题号，3答案
		// 创建段落
		XWPFParagraph paragraphTitle = document.createParagraph();
		paragraphTitle.setAlignment(ParagraphAlignment.CENTER);
		{
			XWPFRun run0 = paragraphTitle.createRun();
			run0.addBreak();
			run0.addBreak();
			run0.addBreak();
			XWPFRun run = paragraphTitle.createRun();
			run.setText("--------------参考答案--------------");
			run.setFontSize(TITLE_FONT_SIZE);
			run.setFontFamily(STANDARD_FONT_FAMILY);
			run.setBold(true);
			run.addBreak();
		}
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		for (String[] answer : answers) {
			// 1:大题号，2小题号，3答案
			if (answer[0].equals("1")) {
				XWPFRun run = paragraph.createRun();
				run.addBreak();
				run.setText(answer[1]);
				run.setFontSize(SMALL_FONT_SIZE);
				run.setFontFamily(STANDARD_FONT_FAMILY);
				run.addBreak();
			}
			if (answer[0].equals("2")) {
				XWPFRun run = paragraph.createRun();
				run.setText("  " + answer[1]);
				run.setFontSize(SMALL_FONT_SIZE);
				run.setFontFamily(STANDARD_FONT_FAMILY);
			}
			if (answer[0].equals("3")) {
				XWPFRun run = paragraph.createRun();
				run.setText(answer[1]);
				run.setFontSize(SMALL_FONT_SIZE);
				run.setFontFamily(STANDARD_FONT_FAMILY);
			}
		}
	}

	// 生成章节得主体部分
	private static void initChapterMain(XWPFDocument document, ChapterUnit chapter, int chapterTitleFontSize,
			int chapterIndentation) {
		// 迭代章节
		initChapterTitle(document, chapter.getChapter().getName(),
				" 共" + chapter.getSubjectNum() + "道小题，" + chapter.getAllpoint() + "分", chapterTitleFontSize,
				chapterIndentation);
		// 章节备注
		// 超文本内容
		if (StringUtils.isNotBlank(chapter.getChapter().getTextnote())) {
			initChapterNote(document, chapter.getChapter().getTextnote());
		}
		// 章节得引用材料
		initChapterMaterials(document, chapter.getMaterials());
		// 章节题目
		int num = 0;
		for (SubjectUnit subject : chapter.getSubjects()) {
			// 创建题目
			initSubject(document, subject, ++num);
		}
	}

	/**
	 * 輸出題目
	 * 
	 * @param document
	 * @param subject
	 */
	private static void initSubject(XWPFDocument document, SubjectUnit subject, int num) {
		answers.add(new String[] { "2", num + ". " });
		// 題頭空白
		XWPFParagraph paragraph0 = document.createParagraph();
		XWPFRun run0 = paragraph0.createRun();
		run0.setText("");
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setIndentationFirstLine(STANDARD_INDENTATION);
		// 题号
		XWPFRun run11 = paragraph.createRun();
		run11.setText(num + ". ");
		run11.setFontSize(STANDARD_FONT_SIZE);
		run11.setFontFamily(STANDARD_FONT_FAMILY);
		// 题干
		XWPFRun run12 = paragraph.createRun();
		run12.setText(formatSubjectTip(subject.getVersion().getTipstr()));
		run12.setFontSize(STANDARD_FONT_SIZE);
		run12.setFontFamily(STANDARD_FONT_FAMILY);
		// 题得分
		XWPFRun run13 = paragraph.createRun();
		run13.setText(subject.getPoint() + "分");
		run13.setFontSize(SMALL_FONT_SIZE);
		run13.setColor("666666");
		run13.setFontFamily(STANDARD_FONT_FAMILY);
		// 超文本内容
		if (StringUtils.isNotBlank(subject.getVersion().getTipnote())) {
			XWPFParagraph paragraph2 = document.createParagraph();
			initHtmlContent(paragraph2, subject.getVersion().getTipnote(), STANDARD_FONT_SIZE);
		}
		int answernum = 0;
		// 输出選項
		for (AnswerUnit answer : subject.getAnswers()) {
			int showAnswerNum = (++answernum);
			if (subject.getVersion().getTiptype().equals("2") || subject.getVersion().getTiptype().equals("3")) {
				XWPFParagraph paragraphAnswer = document.createParagraph();
				paragraphAnswer.setIndentationFirstLine(STANDARD_INDENTATION);
				XWPFRun runAnswer1 = paragraphAnswer.createRun();
				runAnswer1.setText(getWordCode(showAnswerNum) + ". ");
				runAnswer1.setFontSize(STANDARD_FONT_SIZE);
				runAnswer1.setFontFamily(STANDARD_FONT_FAMILY);
				XWPFRun runAnswer2 = paragraphAnswer.createRun();
				runAnswer2.setText(answer.getAnswer().getAnswer());
				runAnswer2.setFontSize(STANDARD_FONT_SIZE);
				runAnswer2.setFontFamily(STANDARD_FONT_FAMILY);
				// 超文本内容
				if (StringUtils.isNotBlank(answer.getAnswer().getAnswernote())) {
					XWPFParagraph paragraph2 = document.createParagraph();
					paragraph2.setIndentationFirstLine(STANDARD_INDENTATION);
					initHtmlContent(paragraph2, answer.getAnswer().getAnswernote(), STANDARD_FONT_SIZE);
				}
			}
			{
				// 处理答案
				if (answer.getAnswer().getRightanswer().equals("1") && (subject.getVersion().getTiptype().equals("4")
						|| subject.getVersion().getTiptype().equals("2")
						|| subject.getVersion().getTiptype().equals("3"))) {
					// 答案2.单选，3.多选，4判断
					if (subject.getVersion().getTiptype().equals("4")) {
						answers.add(new String[] { "3", (showAnswerNum == 1) ? "对" : "错" });
					}
					if (subject.getVersion().getTiptype().equals("2")
							|| subject.getVersion().getTiptype().equals("3")) {
						answers.add(new String[] { "3", getWordCode(showAnswerNum) });
					}
				}
				if (subject.getVersion().getTiptype().equals("1")) {
					// 1.填空，
					answers.add(new String[] { "3", answer.getAnswer().getAnswer() });
				}
				if (subject.getVersion().getTiptype().equals("5") || subject.getVersion().getTiptype().equals("6")) {
					// 5问答,6附件
					answers.add(new String[] { "3", answer.getAnswer().getAnswer() });
				}
			}
		}
	}

	/**
	 * 创建超文本内容
	 * 
	 * @param paragraph
	 * @param html
	 * @param fontSize
	 */
	private static void initHtmlContent(XWPFParagraph paragraph, String html, int fontSize) {
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		Document document = Jsoup.parse(html);
		Elements imgs = document.getElementsByTag("img");
		imgs.after("666WORDIMG666");
		imgs.before("666WORDIMG666");
		html = document.html();
		String[] htmlparts = html.split("666WORDIMG666");
		for (String part : htmlparts) {
			if (StringUtils.isNotBlank(part)) {
				XWPFRun run2 = paragraph.createRun();
				run2.setFontSize(fontSize);
				run2.setFontFamily(STANDARD_FONT_FAMILY);
				if (part.trim().startsWith("<img")) {
					// 图片
					// 查找图片对应得文件
					// 将图片插入到word中
					FileInputStream is = null;
					try {
						File img = getImgFile(part);
						if (img != null) {
							is = new FileInputStream(img);
							run2.addPicture(is, getImgFileType(img), img.getName(), Units.toEMU(getImgWidth(part, img)),
									Units.toEMU(getImgHeight(part, img))); // 200x200
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (is != null) {
								is.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					// 文本
					// 判断文本中是否需要插入换行
					if (isHeadBreak(part)) {
						run2.addBreak();
						run2.addBreak();
					}
					run2.setText(StringEscapeUtils
							.unescapeHtml(formatSubjectTip(FarmHtmlUtils.HtmlRemoveTag(part.replace("&nbsp;", " ")))));
					if (isFootBreak(part)) {
						run2.addBreak();
						run2.addBreak();
					}
				}
			}
		}
	}

	/**
	 * 获得图片得物理文件
	 * 
	 * @param htmlImg
	 * @return
	 */
	private static File getImgFile(String htmlImg) {
		Document document = Jsoup.parse(htmlImg);
		Elements imgs = document.getElementsByTag("img");
		String url = imgs.get(0).attr("src");
		String fileid = FarmDocFiles.getFileIdFromImgUrl(url);
		if (StringUtils.isNotBlank(fileid)) {
			FarmFileManagerInter fileServer = (FarmFileManagerInter) BeanFactory.getBean("farmFileManagerImpl");
			File file = fileServer.getFile(fileServer.getFile(fileid));
			return file;
		} else {
			return null;
		}
	}

	// 获得文件类型
	private static int getImgFileType(File img) {
		if (img.getName().toUpperCase().indexOf("JPG") > 0 || img.getName().toUpperCase().indexOf("JPEG") > 0) {
			return XWPFDocument.PICTURE_TYPE_JPEG;
		}
		if (img.getName().toUpperCase().indexOf("GIF") > 0) {
			return XWPFDocument.PICTURE_TYPE_GIF;
		}
		if (img.getName().toUpperCase().indexOf("BMP") > 0) {
			return XWPFDocument.PICTURE_TYPE_BMP;
		}
		return XWPFDocument.PICTURE_TYPE_JPEG;
	}

	/**
	 * 获得图片宽度
	 * 
	 * @param htmlImg
	 * @param img
	 * @return
	 */
	private static int getImgWidth(String htmlImg, File img) {
		int widthInt = 0;
		try {
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(img));
			Document document = Jsoup.parse(htmlImg);
			Elements imgs = document.getElementsByTag("img");
			String width = imgs.attr("width");
			String height = imgs.attr("height");
			int fileWidth = sourceImg.getWidth();
			int heightWidth = sourceImg.getHeight();
			widthInt = fileWidth;
			if (StringUtils.isNotBlank(width)) {
				// 有宽度
				widthInt = Integer.valueOf(width);
			}
			if (StringUtils.isBlank(width) && StringUtils.isNotBlank(height)) {
				// 有高度但是没有宽度
				widthInt = Integer.valueOf(height) * (fileWidth / heightWidth);
			}
			if (widthInt > 430) {
				widthInt = 430;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return widthInt;
	}

	/**
	 * 获得图片高度
	 * 
	 * @param htmlImg
	 * @param img
	 * @return
	 */
	private static int getImgHeight(String htmlImg, File img) {
		int heightInt = 0;
		try {
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(img));
			Document document = Jsoup.parse(htmlImg);
			Elements imgs = document.getElementsByTag("img");
			String width = imgs.attr("width");
			String height = imgs.attr("height");
			int fileWidth = sourceImg.getWidth();
			int heightWidth = sourceImg.getHeight();
			heightInt = heightWidth;
			if (StringUtils.isNotBlank(height)) {
				// 有高度
				heightInt = Integer.valueOf(height);
			}
			if (StringUtils.isBlank(height) && StringUtils.isNotBlank(width)) {
				// 有宽度但是没有高度
				heightInt = Integer.valueOf(width) * (heightWidth / fileWidth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return heightInt;
	}

	/**
	 * 是否开始位置换行
	 * 
	 * @param html
	 * @return
	 */
	private static boolean isHeadBreak(String html) {
		if (html.trim().startsWith("<div") || html.trim().startsWith("<p")) {
			return true;
		}
		return false;
	}

	/**
	 * 是否结束位置换行
	 * 
	 * @param html
	 * @return
	 */
	private static boolean isFootBreak(String html) {
		if (html.trim().endsWith("</div>") || html.trim().endsWith("</p>") || html.trim().endsWith("<p>")
				|| html.trim().endsWith("<br/>") || html.trim().endsWith("<p align=\"center\">")
				|| html.trim().endsWith("<p align=\"right\">") || html.trim().endsWith("<p align=\"left\">")
				|| html.trim().endsWith("<br>") || html.trim().endsWith("<dir>")) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化题目题干
	 * 
	 * @param text
	 * @return
	 */
	private static String formatSubjectTip(String text) {
		text = text.replace("（", "(");
		text = text.replace("）", " )");
		text = text.replaceAll("\\([_, ]{0,5}?\\)", "(_____)");
		text = text.replaceAll("[_]{1,5}", "_____");
		return text;
	}

	/**
	 * 转义选项符号
	 * 
	 * @param code
	 * @return
	 */
	private static String getWordCode(int code) {
		Map<Integer, String> codeMap = new HashMap<>();
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
		return codeMap.get(code);
	}

	/**
	 * 创建页眉
	 * 
	 * @param doc
	 * @param orgFullName
	 */
	public static void createHeader(XWPFDocument doc, String orgFullName) {
		try {
			/*
			 * 对页眉段落作处理，使公司logo图片在页眉左边，公司全称在页眉右边
			 */
			CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
			XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
			XWPFHeader header;

			header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

			XWPFParagraph paragraph = header.getParagraphArray(0);
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			paragraph.setBorderBottom(Borders.THICK);

			CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
			tabStop.setVal(STTabJc.RIGHT);
			int twipsPerInch = 1440;
			tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));
			XWPFRun run = paragraph.createRun();
			run.setFontFamily(STANDARD_FONT_FAMILY);
			/*
			 * 根据公司logo在ftp上的路径获取到公司到图片字节流 添加公司logo到页眉，logo在左边
			 */
			{
				String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
				String filePath = "/view/web-simple/atext/png/icon".replaceAll("/",
						File.separator.equals("/") ? "/" : "\\\\");
				File file = new File(webPath + filePath);
				InputStream is = null;
				try {
					is = new FileInputStream(file);
					XWPFPicture picture = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, webPath + filePath,
							Units.toEMU(24), Units.toEMU(24));
					String blipID = "";
					for (XWPFPictureData picturedata : header.getAllPackagePictures()) { // 这段必须有，不然打开的logo图片不显示
						blipID = header.getRelationId(picturedata);
					}
					picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
					run.addTab();
				} finally {
					is.close();
				}
			}
			/*
			 * 添加字体页眉，公司全称 公司全称在右边
			 */
			if (StringUtils.isNotBlank(orgFullName)) {
				run = paragraph.createRun();
				run.setText(orgFullName);
				run.setFontFamily(STANDARD_FONT_FAMILY);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建页脚
	 * 
	 * @param document
	 * @param telephone
	 * @param orgAddress
	 */
	public static void createFooter(XWPFDocument document, String telephone, String orgAddress) {
		try {
			/*
			 * 生成页脚段落 给段落设置宽度为占满一行 为公司地址和公司电话左对齐，页码右对齐创造条件
			 */
			CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
			XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
			XWPFFooter footer = headerFooterPolicy.createFooter(STHdrFtr.DEFAULT);
			XWPFParagraph paragraph = footer.getParagraphArray(0);
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			paragraph.setVerticalAlignment(TextAlignment.CENTER);
			paragraph.setBorderTop(Borders.THICK);
			CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
			tabStop.setVal(STTabJc.RIGHT);
			int twipsPerInch = 1440;
			tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

			/*
			 * 给段落创建元素 设置元素字面为公司地址+公司电话
			 */
			XWPFRun run = paragraph.createRun();
			run.setText((StringUtils.isNotBlank(orgAddress) ? orgAddress : "")
					+ (StringUtils.isNotBlank(telephone) ? "  " + telephone : ""));
			run.setFontFamily(STANDARD_FONT_FAMILY);
			run.addTab();

			/*
			 * 生成页码 页码右对齐
			 */
			run = paragraph.createRun();
			run.setText("第");
			run.setFontFamily(STANDARD_FONT_FAMILY);

			run = paragraph.createRun();
			CTFldChar fldChar = run.getCTR().addNewFldChar();
			fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

			run = paragraph.createRun();
			CTText ctText = run.getCTR().addNewInstrText();
			ctText.setStringValue("PAGE  \\* MERGEFORMAT");
			ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
			run.setFontFamily(STANDARD_FONT_FAMILY);

			fldChar = run.getCTR().addNewFldChar();
			fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

			run = paragraph.createRun();
			run.setText("页 总共");
			run.setFontFamily(STANDARD_FONT_FAMILY);

			run = paragraph.createRun();
			fldChar = run.getCTR().addNewFldChar();
			fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

			run = paragraph.createRun();
			ctText = run.getCTR().addNewInstrText();
			ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
			ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
			run.setFontFamily(STANDARD_FONT_FAMILY);

			fldChar = run.getCTR().addNewFldChar();
			fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

			run = paragraph.createRun();
			run.setText("页");
			run.setFontFamily(STANDARD_FONT_FAMILY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 章节引用材料
	 * 
	 * @param document
	 * @param chapter
	 */
	private static void initChapterMaterials(XWPFDocument document, List<Material> materials) {
		for (Material material : materials) {
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			run.setText("《" + material.getTitle() + "》");
			run.setFontSize(STANDARD_FONT_SIZE);
			run.setFontFamily(STANDARD_FONT_FAMILY);
			run.setBold(true);
			// 超文本内容
			{
				XWPFParagraph paragraph2 = document.createParagraph();
				paragraph2.setIndentationFirstLine(STANDARD_INDENTATION);
				initHtmlContent(paragraph2, material.getText(), SMALL_FONT_SIZE);
			}
		}
	}

	/**
	 * 章节备注
	 * 
	 * @param document
	 * @param chapter
	 */
	private static void initChapterNote(XWPFDocument document, String html) {
		XWPFParagraph paragraph = document.createParagraph();
		initHtmlContent(paragraph, html, SMALL_FONT_SIZE);
	}

	/**
	 * 章节标题
	 * 
	 * @param document
	 * @param chapter
	 */
	private static void initChapterTitle(XWPFDocument document, String title, String info, int chapterTitleFontSize,
			int chapterIndentation) {
		XWPFParagraph paragraph0 = document.createParagraph();
		XWPFRun run0 = paragraph0.createRun();
		run0.addBreak();
		// 创建段落
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setIndentationFirstLine(chapterIndentation);
		XWPFRun run1 = paragraph.createRun();
		answers.add(new String[] { "1", title });
		run1.setText(title);
		run1.setFontSize(chapterTitleFontSize);
		run1.setFontFamily(STANDARD_FONT_FAMILY);
		run1.setBold(true);
		XWPFRun run = paragraph.createRun();
		run.setText(info);
		run.setFontSize(SMALL_FONT_SIZE);
		run.setColor("666666");
		run.setFontFamily(STANDARD_FONT_FAMILY);
	}

	/**
	 * 創建標題
	 * 
	 * @param document
	 * @param paper
	 */
	public static void initPaperTitle(XWPFDocument document, PaperUnit paper) {
		// 创建段落
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setText(paper.getInfo().getName());
		run.setFontSize(TITLE_FONT_SIZE);
		run.setFontFamily(STANDARD_FONT_FAMILY);
		run.setBold(true);
		run.addBreak();
	}

	/**
	 * 答卷信息（题量，总分等）
	 * 
	 * @param document
	 * @param paper
	 */
	public static void initpaperInfo(XWPFDocument document, PaperUnit paper) {
		// 共${paper.rootChapterNum}道大题，${paper.subjectNum}道小题，满分${paper.allPoint}分，建议答题时间${paper.info.advicetime}分钟
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setText("共" + paper.getRootChapterNum() + "道大题，" + paper.getSubjectNum() + "道小题，满分" + paper.getAllPoint()
				+ "分，建议答题时间" + paper.getInfo().getAdvicetime() + "分钟");
		run.setFontSize(SMALL_FONT_SIZE);
		run.setColor("666666");
		run.setFontFamily(STANDARD_FONT_FAMILY);
		run.addBreak();
	}

	/**
	 * 答卷说明
	 * 
	 * @param document
	 * @param paper
	 */
	public static void initpaperNote(XWPFDocument document, String html) {
		// paper.info.papernote
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		initHtmlContent(paragraph, html, SMALL_FONT_SIZE);
		XWPFRun run = paragraph.createRun();
		run.addBreak();
	}

	public static void main(String[] args) {
		// 获取一个空得docx文件
		File papaerFile = new File("D:\\test\\答卷demo.docx");
		papaerFile.delete();
		FileOutputStream out = null;
		XWPFDocument document = null;
		PaperUnit paper = new PaperUnit();
		paper.setInfo(new Paper());
		paper.setRootChapterNum(5);
		paper.setSubjectNum(32);
		paper.setAllPoint(100);
		paper.getInfo().setAdvicetime(60);
		paper.getInfo().setName("行政处罚法试题及答案");
		// paper.info.papernote
		paper.getInfo().setPapernote("<div>温馨提示：请监考老师读题 2 次。请小朋友安安静静地听老师读题，答题时和“认真、细心”交朋友，把字写得漂漂亮亮的。相信自己，你是最棒的！</div>");
		try {
			// new 空白的文档
			document = new XWPFDocument();
			out = new FileOutputStream(papaerFile);
			WordPaperCreator.initWordPaper(document, paper);
			document.write(out);
			// 題目
			// 題型“共2道大题，5道小题，满分5分，建议答题时间100分钟”
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
