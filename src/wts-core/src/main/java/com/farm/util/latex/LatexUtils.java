package com.farm.util.latex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * 生成数学公式图片
 * 
 * @author macpl
 *
 */
public class LatexUtils {

//	public static void main(String[] args) {
//		latex2Png("\\frac{x^{2}}{2 t^{2}-3 t-5}+\\frac{y^{2}}{t^{2}+t-7}=1", new File("D:\\test\\latex.png"));
//	}
//
//	/**
//	 * 生成数学公式
//	 * 
//	 * @param latex
//	 * @param file
//	 */
//	public static void latex2Png(String latex, File file) {
//		try {
//			TeXFormula formula = new TeXFormula(latex);
//			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
//			icon.setInsets(new Insets(5, 5, 5, 5));
//			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
//					BufferedImage.TYPE_BYTE_GRAY);
//			Graphics2D g2 = image.createGraphics();
//			g2.setColor(Color.white);
//			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
//			JLabel jl = new JLabel();
//			jl.setForeground(new Color(0, 0, 0));
//			icon.paintIcon(jl, g2, 0, 0);
//			FileOutputStream fos = new FileOutputStream(file);
//			try {
//				ImageIO.write(image, "png", fos);
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				fos.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// latex 转 imgbase64
	public static byte[] latex2Bytes(String latex) {
		try {
			TeXFormula formula = new TeXFormula(latex);
			// render the formla to an icon of the same size as the formula.
			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
			// insert a border
			icon.setInsets(new Insets(5, 5, 5, 5));
			// now create an actual image of the rendered equation
			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
					BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D g2 = image.createGraphics();
			g2.setColor(Color.white);
			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
			JLabel jl = new JLabel();
			jl.setForeground(new Color(0, 0, 0));
			icon.paintIcon(jl, g2, 0, 0);
			// at this point the image is created, you could also save it with
			// ImageIO
			// saveImage(image, "png", "F:\\b.png");
			// ImageIO.write(image, "png", new File("F:\\c.png"));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = null;
			try {
				ImageIO.write(image, "png", outputStream);
				buffer = outputStream.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				outputStream.close();
			}
			return buffer;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
