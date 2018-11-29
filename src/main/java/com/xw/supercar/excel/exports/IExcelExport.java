package com.xw.supercar.excel.exports;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class IExcelExport<T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取Excel的Header
     *
     * @return
     */
	protected abstract String[] getHeader();

    /**
     * 返回Excel的header大小
     *
     * @return
     */
    protected abstract int getHeaderSize();

    /**
     * 导出的标题
     *
     * @return
     */
    public abstract String getTitle();
    
    /**
     * 导出的标题
     *
     * @return
     */
    public abstract String[] getFields();

    /**
     * 是否包含 特殊的Field的处理
     *
     * @param filedName
     * @return
     */
    public abstract boolean containSpecialField(String filedName);

    /**
     * 获取 特殊的Field的处理后的值
     *
     * @param filedName
     * @return
     */
    public abstract String getSpecialFieldValue(String filedName, Object value);

    /**
     * 获取数据源
     *
     * @return
     */
    protected abstract List<T> getPoiList();

    /**
     * 设置数据源
     */
    protected abstract void setPoiList(List<T> data);
    
    /**
	 * excel导出
	 * 
	 * @param exportFilePath 导出文件路径
	 * @param pattern 导出日期格式
	 *
	 * @author wsz 2017-09-20
	 */
    @SuppressWarnings("deprecation")
	public Boolean export(String exportFilePath, String pattern) {
    	Boolean rs = true;
    	//日期格式为空，直接用默认
    	if(StringUtils.isEmpty(pattern))
    		pattern = "yyyy/MM/dd HH:mm:ss";
    	
        //读取配置
        String[] headers = getHeader();
        Collection<T> dataset = getPoiList();
        if (dataset == null || dataset.isEmpty()) {
            //空数据 直接退出
            return rs;
        }
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(getTitle());
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = t.getClass().getDeclaredFields();
            
            //导出自定义的field属性
            String[] fields = getFields();
            for (int i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = fields[i];
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (containSpecialField(fieldName)) {
                        textValue = getSpecialFieldValue(fieldName,value);
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof Long) {
                        Date date = new Date((Long) value);
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value == null) {
                            textValue = "";
                        } else {
                            textValue = value.toString();
                        }
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLACK.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (Exception e) {
                    logger.error("excel导出-export() exception...", e);
                    rs = false;
                } finally {
                    // 清理资源
                	try {
						workbook.close();
					} catch (IOException e) {
                        logger.error("excel导出-export() io流关闭失败 exception...", e);
					}
                }
            }
        }
        try {
        	OutputStream out = new FileOutputStream(exportFilePath);
            workbook.write(out);
            logger.info("excel 导出成功！");
        } catch (IOException e) {
            logger.error("excel导出-export() exception...", e);
        }
        
        return rs;
    }

}
