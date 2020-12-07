package com.mer.common.Utils.Files.TxT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件操作常用功能
 *
 * @author Nano
 * @version 1.0.0
 * abacus-parent
 */

public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获得整个文件字节内容
     *
     * @param file
     * @return
     */
    public static byte[] readAllBytesFromFile(File file) {
        byte[] fileBytes = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);
            int length = (int) randomAccessFile.length();
            fileBytes = new byte[length];
            randomAccessFile.readFully(fileBytes);
        } catch (IOException ioe) {
	        LOGGER.warn("read all bytes from file io exception, fromFile {} :{}", file.getAbsolutePath(), ioe.getMessage());
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
	            LOGGER.warn("read all bytes from file close file channel, fromFile {} :{}", file.getAbsolutePath(), e.getMessage());
            }
        }
        return fileBytes;
    } 

    /**
     * 一行一行读取文件内容 (过滤空行)
     *
     * @param file 获得的文件
     * @param pos  行的起始位置
     * @return 所有行的内容
     */
    public static List<String> readLineFromFile(File file, int pos, String encode) {
        List<String> lines = new ArrayList<String>();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(pos);
            String line;
            while ((line = randomAccessFile.readLine()) != null) {
                if (line.trim().length() > 0)
                    lines.add(new String(line.getBytes("ISO-8859-1"), encode));
            }
        } catch (IOException ioe) {
            throw new RuntimeException("readLineFromFile error:" + ioe.getMessage());
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
	            LOGGER.warn("read all bytes from file close file channel, fromFile {} :{}", file.getAbsolutePath(), e.getMessage());
            }
        }
        return lines;
    }

    /**
     * 打开文件访问通道
     *
     * @param file   文件
     * @param encode 文件编码
     * @return
     */
    public static BufferedReader openBufferReaderFromFile(File file, String encode) {
        BufferedReader reader;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(fileInputStream, encode);
            reader = new BufferedReader(input);
        } catch (IOException e) {
            throw new RuntimeException("openBufferReaderFromFile error:" + e.getMessage());
        }
        return reader;
    }

    /**
     * 获取按行读取文件记录
     *
     * @param reader
     * @param batchNum
     * @return
     */
    public static List<String> readBatchLineWithReader(BufferedReader reader, int batchNum) {
        List<String> lines = new ArrayList<String>(batchNum);
        try {
            if (reader != null) {
                String line;
                int count = 0;
                while (count < batchNum && (line = reader.readLine()) != null) {
                    if (line.trim().length() > 0) {
                        lines.add(line);
                        count = count + 1;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("readBatchLineFromReader error:" + e.getMessage());
        }
        return lines;
    }

    /**
     * 关闭文件访问通道
     *
     * @param reader
     */
    public static void closeBufferReader(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException("closeBufferReader error:" + e.getMessage());
            }
        }
    }

    /**
     * 统计文件行数，过滤空行
     *
     * @param file 文件
     * @param pos  读取起始位置
     * @return
     */
    public static int countLineFromFile(File file, int pos) {
        int count = 0;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(pos);
            String line;
            while ((line = randomAccessFile.readLine()) != null) {
                if (line.trim().length() > 0)
                    count = count + 1;
            }
        } catch (IOException ioe) {
	        LOGGER.warn("count line from file, fromFile {} :{}", file.getAbsolutePath(), ioe.getMessage());
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
	            LOGGER.warn("count line from file close file channel, fromFile {} :{}", file.getAbsolutePath(), e.getMessage());
            }
        }
        return count;
    }

    /**
     * 获得文件第一行内容
     * @param file   获得的文件
     * @return 行的内容
     */
    public static String readFirstLineFromFile(File file) {
        String first = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);
            first = new String(randomAccessFile.readLine().getBytes("ISO-8859-1"), "UTF-8");
        } catch (IOException ioe) {
	        ioe.printStackTrace();
	        LOGGER.warn("read first line  from file, fromFile {} :{}", file.getAbsolutePath(), ioe.getMessage());
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
	            LOGGER.warn("read first line from file close file channel, fromFile {} :{}", file.getAbsolutePath(), e.getMessage());
            }
        }
        return first;
    }

    /**
     * 写文件第一行内容
     *
     * @param file   获得的文件
     * @param encode 字符编码
     * @return 行的内容
     */
    public static boolean writeFirstLineToFile(File file, String line, String encode) {
        boolean wfirst = false;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
            line = line + "\r\n";
            randomAccessFile.write(line.getBytes(encode));
            wfirst = true;
        } catch (IOException ioe) {
	        LOGGER.warn("write first line  from file, fromFile {} :{}", file.getAbsolutePath(), ioe.getMessage());
        } finally {
            try {
                if (randomAccessFile != null) randomAccessFile.close();
            } catch (IOException e) {
	            LOGGER.warn("write first line from file close file channel, fromFile {} :{}", file.getAbsolutePath(), e.getMessage());
            }
        }
        return wfirst;
    }

    /**
     * 打开写文件通道
     *
     * @param file   文件
     * @param encode 文件编码
     * @return
     */
    public static BufferedWriter openBufferWriterToFile(File file, String encode) {
        BufferedWriter writer;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            OutputStreamWriter out = new OutputStreamWriter(fileOutputStream, encode);
            writer = new BufferedWriter(out);
        } catch (IOException e) {
            throw new RuntimeException("openBufferWriterFromFile error:" + e.getMessage());
        }
        return writer;
    }

    /**
     * 按行写入文件记录
     *
     * @param writer
     * @param lines
     * @return
     */
    public static boolean writeBatchLineWithWriter(BufferedWriter writer, List<String> lines) {
        boolean write = false;
        try {
            int count = 0;
            if (writer != null && lines != null && lines.size() > 0) {
                for (String line : lines) {
                    if(line != null && line.trim().length() > 0){
                        writer.write(line+"\r\n");
                        count ++;
                    }
                }
                if(count <= lines.size()){/**空行时可能小于*/
                    writer.flush();
                    write = true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("writeBatchLineWithWriter error:" + e.getMessage());
        }
        return write;
    }


    /**
     * 文件的重命名
     *
     * @param file 原文件
     * @param dest 目标文件
     * @return
     */
    public static boolean rename(File file, File dest) {
        if (file.exists()) {
            return file.renameTo(dest);
        }
        return false;
    }


    public static boolean mkdir(String path){
        File absLocalFilePathDirectory = new File(path);
        if(!absLocalFilePathDirectory.exists()){
            try{
                return absLocalFilePathDirectory.mkdirs();
            }catch (Exception e){
                LOGGER.error("mkdir create file path {} failed,cause by:{}",new Object[]{e.getMessage()});
                try{
                    if(!absLocalFilePathDirectory.exists()){
                        return absLocalFilePathDirectory.mkdirs();
                    }
                }catch (Exception ee){
                    LOGGER.error("mkdir create file path {} failed,cause by:{}",new Object[]{ee.getMessage()});
                }
            }
        }else{
            return true;
        }
        return false;
    }
    
    public static List<String[]> fileTxt_list(File file ){
      	List<String[]> list=new ArrayList<String[]>();
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader bf = new BufferedReader(input);
            // 按行读取字符串
            String str;
            int row =0;
            while ((str = bf.readLine()) != null) {
                row++;
                if(row!=1){
                    list.add(str.split(","));
                }
            }
            bf.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
			return list;
		}
    	
    }


}
