package com.farm.doc.domain.doc;

public class DocImgs {
	// 预览图(小图)
	private String imgMinUrl;
	// 预览图(大图)
	private String imgMaxUrl;
	// 预览图(中图)
	private String imgMedUrl;
	// 创建者照片
	private String photoUrl;

	public String getImgMinUrl() {
		return imgMinUrl;
	}

	public void setImgMinUrl(String imgMinUrl) {
		this.imgMinUrl = imgMinUrl;
	}

	public String getImgMaxUrl() {
		return imgMaxUrl;
	}

	public void setImgMaxUrl(String imgMaxUrl) {
		this.imgMaxUrl = imgMaxUrl;
	}

	public String getImgMedUrl() {
		return imgMedUrl;
	}

	public void setImgMedUrl(String imgMedUrl) {
		this.imgMedUrl = imgMedUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
