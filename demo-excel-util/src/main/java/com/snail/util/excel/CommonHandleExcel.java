package com.snail.util.excel;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 公共的 处理excel 类
 */
public class CommonHandleExcel implements IExcelCallBack{
    /**
     * 列的数量
     */
    private int colSize;
    /**
     * 数据数量
     */
    private int dataSize;
    
    public CommonHandleExcel(int colSize, int dataSize) {
        this.colSize = colSize;
        this.dataSize = dataSize;
    }

    @Override
    public void run(ExportExcel excel) {
        HSSFSheet sheet = excel.getSheet();
        //在最后一行加入 信息
        String timeStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        int rownum = dataSize + 2;
        HSSFRow row = sheet.createRow(rownum);
        for (int i = 0; i < colSize; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString("数据来源：xxxxx，时间：" + timeStr));
        }
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, colSize - 1));

    }
}
