/*
 * Copyright (C) 2015 Ferid Cafer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ferid.app.classroom.utility;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by ferid.cafer on 7/13/2015.
 */
public class ExcelStyleManager {

    private static volatile CellStyle cellStyleHeader;
    private static volatile CellStyle cellStyleContent;

    public ExcelStyleManager() {
        cellStyleHeader = null;
        cellStyleContent = null;
    }

    /**
     * Get header cell style
     * @param wb
     * @return
     */
    private static CellStyle getHeaderCellStyleInstance(Workbook wb) {
        if (cellStyleHeader == null) {
            synchronized (ExcelStyleManager.class) {
                if (cellStyleHeader == null) {
                    cellStyleHeader = wb.createCellStyle();
                }
            }
        }

        return cellStyleHeader;
    }

    /**
     * Get content cell style
     * @param wb
     * @return
     */
    private static CellStyle getContentCellStyleInstance(Workbook wb) {
        if (cellStyleContent == null) {
            synchronized (ExcelStyleManager.class) {
                if (cellStyleContent == null) {
                    cellStyleContent = wb.createCellStyle();
                }
            }
        }

        return cellStyleContent;
    }

    /**
     * Header cell style (dates)
     * @param wb Workbook
     * @return CellStyle
     */
    public CellStyle getHeaderCellStyle(Workbook wb) {
        CellStyle cellStyle = getHeaderCellStyleInstance(wb);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setWrapText(true);

        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return cellStyle;
    }

    /**
     * Content cell style (presence)
     * @param wb Workbook
     * @return CellStyle
     */
    public CellStyle getContentCellStyle(Workbook wb) {
        CellStyle cellStyle = getContentCellStyleInstance(wb);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 8);
        cellStyle.setFont(font);

        cellStyle.setWrapText(true);

        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return cellStyle;
    }
}