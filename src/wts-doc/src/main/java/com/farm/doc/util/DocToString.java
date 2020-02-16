package com.farm.doc.util;

import java.io.File;
import java.util.List;

public class DocToString {

	public static String getText(List<File> files) {
		StringBuffer str = new StringBuffer();
		str.append(getTextByDoc(files));
		return str.toString();
	}

	public static String getTextByDoc(Object obj) {
		return null;
	}

}
