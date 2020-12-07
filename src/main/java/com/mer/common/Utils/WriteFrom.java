package com.mer.common.Utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class WriteFrom {
	public static String WriterEx(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
