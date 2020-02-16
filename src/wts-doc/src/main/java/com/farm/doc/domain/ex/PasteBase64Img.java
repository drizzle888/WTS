package com.farm.doc.domain.ex;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class PasteBase64Img {
	private String imgName;
	private String imgTypeSuffix;
	private byte[] data;
	private String base64Data;
	private boolean isImgFile;

	public PasteBase64Img(String base64Str) {
		base64Data = base64Str;
		String[] arrImageData = base64Str.split(",");
		String[] arrTypes = arrImageData[0].split(";");
		String[] arrImageType = arrTypes[0].split(":");
		String imageType = arrImageType[1];
		String imageTypeSuffix = imageType.split("/")[1];
		imgTypeSuffix = imageTypeSuffix;
		String IMAGETYPES = "gif,jpg,jpeg,png,bmp";
		imgName = "paste" + new Date().getTime() + "." + imgTypeSuffix;
		if ("base64".equalsIgnoreCase(arrTypes[1]) && IMAGETYPES.indexOf(imageTypeSuffix.toLowerCase()) != -1) {
			isImgFile = true;
			data = Base64.decodeBase64(arrImageData[1]);
		} else {
			isImgFile = false;
		}
	}

	public boolean isImgFile() {
		return isImgFile;
	}

	public void setImgFile(boolean isImgFile) {
		this.isImgFile = isImgFile;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgTypeSuffix() {
		return imgTypeSuffix;
	}

	public void setImgTypeSuffix(String imgTypeSuffix) {
		this.imgTypeSuffix = imgTypeSuffix;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}

}
