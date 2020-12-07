package com.mer.common.Utils.Files.Excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

	/** 读取的总行数 */
	private int totalRows = 0;
	/** 读取的总列数 */
	private int totalCells = 0;
	/** 起始读取的行数 */
	private int fstrow = 0;

	/**
	 * [方法] readList
	 * [描述] 解析Excel,直接获得数组集合
	 * [参数] file:文件流 filename:版本.xls或者.xlsx
	 * [返回] List<String[]>
	 */
	public List<String[]> readList(File file, String fileName) throws Exception {
		List<String[]> list=new ArrayList<String[]>();
		List<ArrayList<String>> dataList=read(file,fileName);
		if(dataList==null){
			return list;
		}
		for (ArrayList<String> innerLst : dataList) {
			StringBuffer rowData = new StringBuffer();
			for (String dataStr : innerLst) {
				rowData.append(",").append(dataStr);
			}
			if (rowData.length() > 0) {
				String[] arrays=rowData.deleteCharAt(0).toString().split(",");
				list.add(arrays);
			}
		}
		return list;
	}

	/**
	 * [方法] read
	 * [描述] 解析Excel,根据Excel文件的版本获得相应的实例
	 * [返回] List<ArrayList<String>>
	 */
	public List<ArrayList<String>> read(File file, String fileName) throws Exception {
		boolean isExcel2003 = true;
		List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		// 检查文件名是否为空或者是否是Excel格式的文件
		if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
			return dataList;
		}
		// 对文件的合法性进行验
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		// 检查文件是否存在
		if (file == null || !file.exists()) {
			return dataList;
		}
		Workbook wb = isExcel2003 ? new HSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook(new FileInputStream(file));
		dataList = read(wb);
		return dataList;
	}

	/**
	 * [方法] read <br>
	 * [描述] 解析Excel,读取每一行的数据，封装成集合
	 * [返回] List<ArrayList<String>>
	 */
	private List<ArrayList<String>> read(Workbook wb) {
		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		this.totalRows = sheet.getPhysicalNumberOfRows();
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		// 循环Excel的行
		for (int r=fstrow ; r < this.totalRows; r++) {

			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();

			// 循环Excel的列
			for (short c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
//				System.out.println("cell::--------------------->"+cell);
				if (cell == null) {
					rowLst.add(cellValue);
					continue;
				}

				// 处理数字型的,自动去零
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					// 在excel里,日期也是数字,在此要进行判断
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = get4yMdHms(cell.getDateCellValue());
					} else {
						cellValue = cell.getNumericCellValue()+"";
						// 科学计数法转换为数字
						cellValue = convertStr(cellValue).toString();
					}
				} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {// 处理字符串型
					cellValue = cell.getStringCellValue();
				} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {// 处理布尔型
					cellValue = cell.getBooleanCellValue() + "";
				} else {// 其它数据类型
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}


	/**
	 *
	 * [方法] convertStr <br>
	 * [描述] (科学计数法转换为数字) <br>
	 * [参数] (科学计数法) <br>
	 * [返回] String <br>
	 */
	public static BigDecimal convertStr(String str) {
		BigDecimal db = new BigDecimal(str);
		return db.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	/** 根据日期得到YYYY-MM-DD HH:MM:SS.SSS格式字符串 */
	public static String get4yMdHms(Date date) {
		SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormate.format(date);
	}

	/**
	 *
	 * [方法] outputExcelFile
	 * [描述] TODO(导出Excel表格)
	 * [参数] TODO(对参数的描述)
	 * [返回] HSSFWorkbook
	 */
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook outputExcelFile2003(List<Object[]> list, String path, String[] names, int[] width) throws Exception {
		FileInputStream fs  = new FileInputStream(path); //模板
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fs));//创建Excel文档
		Map<String, HSSFCellStyle> mapstyle = createStyles(wb);//标题样式和字体样式
		HSSFCell[] firstcell = new HSSFCell[names.length];
		HSSFSheet hs = wb.getSheetAt(0);//获得一个sheet

		HSSFRow firstrow = hs.createRow((short)0);//第二行的标题
		for(int i=0;i<names.length;i++){
			firstcell[i] = firstrow.createCell((short)i);
			firstcell[i].setCellStyle(mapstyle.get("cell_header_title"));
			firstcell[i].setCellValue(new HSSFRichTextString(names[i]));
			hs.setColumnWidth(i, width[i]*256);
		}

		hs.createFreezePane(0, 1);//冻结行
		for(int i=0;i<list.size();i++){
			firstrow = hs.createRow((short)i+1);//下标为1的开始
			Object[] obj = list.get(i);
			for(int j=0;j<names.length;j++){	//循环excel里的列数
				firstcell[j] = firstrow.createCell((short)j);
				firstcell[j].setCellStyle(mapstyle.get("cell_data_default"));//字体样式
				firstcell[j].setCellType(HSSFCell.CELL_TYPE_STRING);
				if(obj[j]==null||obj[j].equals("")){
					firstcell[j].setCellValue(" ");
				}else{
					firstcell[j].setCellValue(obj[j].toString());
				}
			}
		}
		System.out.println("结束");
		return wb;
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] createStyles <br>
	 * [描述] TODO(设置Excel的标题和样式) <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] Map<String,HSSFCellStyle> <br>
	 * [时间] 2014-8-18 下午05:39:25 <br>
	 *********************************************************.<br>
	 */
	public static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {
		Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();
		//----------------------标题样式---------------------------
		HSSFCellStyle cell_header_title = wb.createCellStyle();
		HSSFFont font2 = wb.createFont();
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 13);//设置字体大小
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体
		cell_header_title.setFont(font2);//选择需要用到的字体格式
		cell_header_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		cell_header_title.setWrapText(false);// 自动换行
		cell_header_title.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cell_header_title.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cell_header_title.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cell_header_title.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		styles.put("cell_header_title", cell_header_title);

		//-----------------------字体样式---------------------------
		HSSFCellStyle cell_data_default = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);//设置字体大小
		cell_data_default.setFont(font);//选择需要用到的字体格式
		cell_data_default.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		cell_data_default.setWrapText(true);// 自动换行
		cell_data_default.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cell_data_default.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cell_data_default.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cell_data_default.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		styles.put("cell_data_default", cell_data_default);
		return styles;
	}

	/*******************************get and set***********************************/
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalCells() {
		return totalCells;
	}
	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}
	public int getFstrow() {
		return fstrow;
	}
	public void setFstrow(int fstrow) {
		this.fstrow = fstrow;
	}

	/**
	 * 用poi获取Excel数据
	 * @param fname
	 * @param titleId
	 * @return
	 */
	public static ArrayList<Map<String, Object>> Excelpoi(MultipartFile fname , String[] title) {
		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		boolean isExcel2003 = true;
		String fileName= fname.getOriginalFilename();
		// 检查文件名是否为空或者是否是Excel格式的文件
		if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
			return arr;
		}
		// 对文件的合法性进行验
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		// 检查文件是否存在
		if (fname == null || fname.getSize()==0) {
			return arr;
		}
		Workbook workbook=null;
		try {
			InputStream fIn = fname.getInputStream();
			if(isExcel2003){
				POIFSFileSystem fs = new POIFSFileSystem(fIn);
				workbook=	 new HSSFWorkbook(fs);
			}else{
				workbook=	 new XSSFWorkbook(new BufferedInputStream(fIn));
			}
			//  workbook = new HSSFWorkbook(fs);
			//readWorkBook = Workbook.getWorkbook(fIn);
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();// 行
			int cellNum;
			Row row;
			for (int i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i);
				cellNum = row.getLastCellNum();// 列
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int j = 0; j < cellNum; j++) {//对一行的每个列进行解析
					Cell cell = row.getCell(j);
					String cellValue = "";
					if (cell == null) {
						map.put(title[j],cellValue);
						continue;
					}
					if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) { // 处理数字型的,自动去零
						if (HSSFDateUtil.isCellDateFormatted(cell)) {// 在excel里,日期也是数字,在此要进行判断
							cellValue = get4yMdHms(cell.getDateCellValue());
						} else {
							cellValue=new DecimalFormat("#").format(cell.getNumericCellValue());
						}
					} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {// 处理字符串型
						cellValue = cell.getStringCellValue();
					} else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {// 处理布尔型
						cellValue = cell.getBooleanCellValue() + "";
					} else {// 其它数据类型
						cellValue = cell.toString() + "";
					}
					map.put(title[j],cellValue);
				}
				arr.add(map);
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook = null;
			}
		}
		return arr;
	}



	public static final String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
	public static final int DEFAULT_COLUMN_WIDTH = 17;// 默认列宽


	/**
	 * 下载文件-到浏览器
	 * */
	public static void XlSX_outExcel(
			LinkedHashMap<String, String> titleList,
			List<Map<String, Object>> list,
			int[] width,
			HttpServletResponse response,
			String fileName)throws IOException {
		//数据格式转换
		JSONArray dataArray = JSONArray.parseArray(JSON.toJSONString(list));
		SXSSFWorkbook workbook = outputExcelFile2007(titleList,dataArray,width);
		XLSX_downLoadExcelToWebsite(workbook,response,fileName);
	}

	public static void XlSX_outExcelLoca(
			LinkedHashMap<String, String> titleList,
			List<Map<String, Object>> list,
			int[] width,
			String paht)throws IOException {
		//数据格式转换
		JSONArray dataArray = JSONArray.parseArray(JSON.toJSONString(list));
		SXSSFWorkbook workbook = outputExcelFile2007(titleList,dataArray,width);
		XLSX_downLoadExcelToLocalPath(workbook,paht);
	}


	/**
	 * 导出Excel(.xlsx)格式
	 * @param titleList 表格头信息集合
	 * @param dataArray 数据数组
	 * @param os 文件输出流
	 */
	public static SXSSFWorkbook outputExcelFile2007(LinkedHashMap<String, String> titleList, JSONArray dataArray, int[] width) {
		String datePattern = DEFAULT_DATE_PATTERN;
		int minBytes = DEFAULT_COLUMN_WIDTH;
		/**
		 * 声明一个工作薄
		 */
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 大于1000行时会把之前的行写入硬盘
		workbook.setCompressTempFiles(true);
		try {
//			OutputStream os = new FileOutputStream(path);

			// head样式
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			headerStyle.setFillForegroundColor(HSSFColor.SEA_GREEN.index);// 设置颜色// https://www.cnblogs.com/haha12/p/4353602.html
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 前景色纯色填充
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			Font headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerStyle.setFont(headerFont);

			// 单元格样式
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			Font cellFont = workbook.createFont();
			cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			cellStyle.setFont(cellFont);

			LinkedHashMap<String, String> headMap = titleList;

			/**
			 * 生成一个(带名称)表格
			 */
			SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
			sheet.createFreezePane(0, 1, 0, 1);// (单独)冻结前三行

			/**
			 * 生成head相关信息+设置每列宽度
			 */
			int[] colWidthArr = new int[headMap.size()];// 列宽数组
			String[] headKeyArr = new String[headMap.size()];// headKey数组
			String[] headValArr = new String[headMap.size()];// headVal数组
			int i = 0;
			for (Map.Entry<String, String> entry : headMap.entrySet()) {
				headKeyArr[i] = entry.getKey();
				headValArr[i] = entry.getValue();
				int bytes = headKeyArr[i].getBytes().length;
				colWidthArr[i] = bytes < minBytes ? minBytes : bytes;
				sheet.setColumnWidth(i, colWidthArr[i] * width[i]);// 设置列宽
				i++;
			}


			/**
			 * 遍历数据集合，产生Excel行数据
			 */
			int rowIndex = 0;
			for (Object obj : dataArray) {
				// 生成title+head信息
				if (rowIndex == 0) {
					SXSSFRow headerRow = (SXSSFRow) sheet.createRow(0);// head行
					for (int j = 0; j < headValArr.length; j++) {
						headerRow.createCell(j).setCellValue(headValArr[j]);
						headerRow.getCell(j).setCellStyle(headerStyle);
					}
					rowIndex = 1;
				}

				JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
				// 生成数据
				SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);// 创建行
				for (int k = 0; k < headKeyArr.length; k++) {
					SXSSFCell cell = (SXSSFCell) dataRow.createCell(k);// 创建单元格
					Object o = jo.get(headKeyArr[k]);
					String cellValue = "";
					if (o == null) {
						cellValue = "";
					} else if (o instanceof Date) {
						cellValue = new SimpleDateFormat(datePattern).format(o);
					} else if (o instanceof Float || o instanceof Double) {
						cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
					} else {
						cellValue = o.toString();
					}

					if (cellValue.equals("true")) {
						cellValue = "男";
					} else if (cellValue.equals("false")) {
						cellValue = "女";
					}

					cell.setCellValue(cellValue);
					cell.setCellStyle(cellStyle);
				}
				rowIndex++;
			}


//			workbook.write(os);
//			os.flush();// 刷新此输出流并强制将所有缓冲的输出字节写出
//			os.close();// 关闭流
//			workbook.dispose();// 释放workbook所占用的所有windows资源

//			System.out.println("文件路径：" + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}


	/**
	 * 下载EXCEL到本地指定的文件夹
	 *
	 * @param wb         EXCEL对象SXSSFWorkbook
	 * @param exportPath 导出路径
	 */
	public static void XLSX_downLoadExcelToLocalPath(SXSSFWorkbook wb, String exportPath) {
		FileOutputStream fops = null;
		try {
			fops = new FileOutputStream(exportPath);
			wb.write(fops);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != wb) {
				try {
					wb.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != fops) {
				try {
					fops.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 下载EXCEL到浏览器
	 *
	 * @param wb       EXCEL对象XSSFWorkbook
	 * @param response
	 * @param fileName 文件名称
	 * @throws IOException
	 */
	public static void XLSX_downLoadExcelToWebsite(SXSSFWorkbook wb, HttpServletResponse response, String fileName) throws IOException {

		response.setHeader("Content-disposition", "attachment; filename="
				+ new String((fileName + ".xlsx").getBytes("utf-8"), "ISO8859-1"));//设置下载的文件名

		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != wb) {
				try {
					wb.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}


	