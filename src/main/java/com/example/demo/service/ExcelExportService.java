package com.example.demo.service;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.sql.results.LoadingLogger_.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.mybatis.model.TableDetail;
import com.example.demo.mybatis.model.TableEntity;
import com.example.demo.mybatis.service.TableService;
import com.example.demo.utils.Lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Component
public class ExcelExportService {

    private static final String FILENAME = "MySql_System_db_desig.xlsx";
    private static final String SHEET_TABLE_LIST = "Table List";

    @Autowired
    TableService _tableService;

    public void exportDataToExcel() throws IOException {
        removeFile(FILENAME);
        Workbook workbook = new XSSFWorkbook();
        this.createTableList(workbook);


        this.write(workbook);
    }

    private String cutTablename (String TABLE_NAME) {
        if(TABLE_NAME.length() <= 31) return TABLE_NAME;
        return TABLE_NAME.substring(0, 31); 
    }

    private String[] TABLE_HEADS = {
        "No", "Table type", "English Naming", "Instances",
        "owner", "View", "Size", "Table Description", "Href Table"
    };

    /**
     * @param workbook
     */
    private void createTableList (Workbook workbook) {
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);

        CellStyle styleNumber = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleNumber.setAlignment(HorizontalAlignment.RIGHT);
        styleNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setBorderTop(BorderStyle.THIN);
        styleNumber.setBorderLeft(BorderStyle.THIN);
        styleNumber.setBorderRight(BorderStyle.THIN);

        CellStyle styleCC = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleCC.setAlignment(HorizontalAlignment.CENTER);
        styleCC.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCC.setBorderBottom(BorderStyle.THIN);
        styleCC.setBorderTop(BorderStyle.THIN);
        styleCC.setBorderLeft(BorderStyle.THIN);
        styleCC.setBorderRight(BorderStyle.THIN);

        CellStyle styleLCB = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleLCB.setAlignment(HorizontalAlignment.LEFT);
        styleLCB.setVerticalAlignment(VerticalAlignment.CENTER);
        styleLCB.setBorderBottom(BorderStyle.THIN);
        styleLCB.setBorderTop(BorderStyle.THIN);
        styleLCB.setBorderLeft(BorderStyle.THIN);
        styleLCB.setBorderRight(BorderStyle.THIN);
        styleLCB.setFont(fontBold);

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet(SHEET_TABLE_LIST);

        Row headRow = sheet.createRow(0);
        CellRangeAddress mergedHead = new CellRangeAddress(0, 1, 0, 7);
        sheet.addMergedRegion(mergedHead);
        Cell mergedCell1 = headRow.createCell(0);
        mergedCell1.setCellValue("Sytem Table List");

        Row headRow2 = sheet.createRow(2);
        headRow2.setHeightInPoints(30);
        int cols = TABLE_HEADS.length;
        for (int i = 0; i < cols; i++) {
            Cell cell = headRow2.createCell(i);
            cell.setCellValue(TABLE_HEADS[i]);
            cell.setCellStyle(styleLCB);
        }
        
        List<TableEntity> tables = this._tableService.getTables();
        Comparator<TableEntity> nameComparator = Comparator.comparing(TableEntity::getTABLE_NAME);
        Collections.sort(tables, nameComparator);     

        int r = 3;
        int stt = 1;
        for (TableEntity table : tables) {
            if(!table.getTABLE_NAME().startsWith("hpm") && !this.table_on_db(table)) continue;
            Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("'" + this.cutTablename(table.getTABLE_NAME()) + "'!A1"); // Định vị đến cell A1 trên Sheet2
            // Tạo sheet detail trước
            try { 
                List<String> tableInList = Arrays.asList(TABLE_ON_DB);
                table.setTable_in(tableInList);      
                this.createTableDetail(workbook, table, style, createHelper, r);
                Row row = sheet.createRow(r++);
                row.setHeightInPoints(30);
                for (int i = 0; i < cols; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style);
                    String value = "";                   
                    switch (i) {
                        case 0: value = "" + stt; sheet.setColumnWidth(i, 1200); cell.setCellStyle(styleCC); break; 
                        case 1: value = table.getENGINE(); sheet.setColumnWidth(i, 4000); break;
                        case 2: value = table.getTABLE_NAME(); cell.setHyperlink(hyperlink); sheet.setColumnWidth(i, 8000); break;
                        case 3: value = "FlexClinic"; sheet.setColumnWidth(i, 4000);break;
                        case 4: value = "Flex"; sheet.setColumnWidth(i, 3000);break;
                        case 5: value = "No"; sheet.setColumnWidth(i, 2500);break;
                        case 6: value = table.getTABLE_ROWS() ; sheet.setColumnWidth(i, 4000); cell.setCellStyle(styleNumber);break;
                        case 7: value = table.getTABLE_COMMENT() ; sheet.setColumnWidth(i, 12000);break;
                        case 8:
                            value = table.getHREF_NAME(); sheet.setColumnWidth(i, 12000);
                            if(!Lib.isBlank(value)) {
                                Hyperlink hyperlinkHref = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
                                hyperlinkHref.setAddress("'" + this.cutTablename(value) + "'!A1");
                                cell.setHyperlink(hyperlinkHref);
                            }
                            break;
                    
                        default:
                            break;
                    }
                    cell.setCellValue(value);                
                }
                stt++;
            } catch (Exception e) { 
                continue;
            }
        }
        
    }

    private String[] DETAIL_HEAD = {
        "No", "Column Name", "Type", "Size", "Description", "NOT NULL", "Default", "INDEX"
    };
    private String[] DETAIL_HEAD_INDEX = {
        "P", "N1", "N2", "N3", "N4", "N5", "N6", "N7"
    };
    private void createTableDetail (Workbook workbook, TableEntity table, CellStyle style, CreationHelper createHelper, int rowTABLE) {
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        // Tạo một CellStyle
        CellStyle styleLC = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleLC.setAlignment(HorizontalAlignment.LEFT);
        styleLC.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle styleCCB = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleCCB.setAlignment(HorizontalAlignment.CENTER);
        styleCCB.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCCB.setFont(fontBold);
        styleCCB.setBorderBottom(BorderStyle.THIN);
        styleCCB.setBorderTop(BorderStyle.THIN);
        styleCCB.setBorderLeft(BorderStyle.THIN);
        styleCCB.setBorderRight(BorderStyle.THIN);

        CellStyle styleCCL = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleCCL.setAlignment(HorizontalAlignment.LEFT);
        styleCCL.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCCL.setFont(fontBold);
        styleCCL.setBorderBottom(BorderStyle.THIN);
        styleCCL.setBorderTop(BorderStyle.THIN);
        styleCCL.setBorderLeft(BorderStyle.THIN);
        styleCCL.setBorderRight(BorderStyle.THIN);

        CellStyle styleCC = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleCC.setAlignment(HorizontalAlignment.CENTER);
        styleCC.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCC.setBorderBottom(BorderStyle.THIN);
        styleCC.setBorderTop(BorderStyle.THIN);
        styleCC.setBorderLeft(BorderStyle.THIN);
        styleCC.setBorderRight(BorderStyle.THIN);

        CellStyle styleNumber = workbook.createCellStyle();
        // Canh nội dung của ô ở trái
        styleNumber.setAlignment(HorizontalAlignment.RIGHT);
        styleNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNumber.setBorderBottom(BorderStyle.THIN);
        styleNumber.setBorderTop(BorderStyle.THIN);
        styleNumber.setBorderLeft(BorderStyle.THIN);
        styleNumber.setBorderRight(BorderStyle.THIN);


        int height = 20;
        List<TableDetail> columns = this._tableService.getTableDetail(table);
        Sheet sheet = workbook.createSheet(this.cutTablename(table.getTABLE_NAME()));

        Row row0 = sheet.createRow(0);
        createBlankCell(row0, style);
        Cell cell00 = row0.createCell(0);
        Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        hyperlink.setAddress("'" + SHEET_TABLE_LIST + "'!A" + (rowTABLE + 1));
        cell00.setCellValue("←");
        cell00.setHyperlink(hyperlink);
        cell00.setCellStyle(styleCC);

        Cell cell01 = row0.createCell(1);
        cell01.setCellValue("Table Name");
        cell01.setCellStyle(styleCCL);
        Cell cell02 = row0.createCell(4);
        cell02.setCellValue("View");
        cell02.setCellStyle(styleCCL);
        Cell cell03 = row0.createCell(5);
        cell03.setCellValue("Size");
        cell03.setCellStyle(styleCCL);
        Cell cell04 = row0.createCell(10);
        cell04.setCellValue("Instance");
        cell04.setCellStyle(styleCCL);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 14));

        Row row1 = sheet.createRow(1);
        createBlankCell(row1, style);
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue(table.getTABLE_NAME());
        cell11.setCellStyle(style);
        Cell cell2 = row1.createCell(4);
        cell2.setCellValue("No");
        cell2.setCellStyle(style);
        Cell cell3 = row1.createCell(5);
        cell3.setCellValue(table.getTABLE_ROWS());
        cell3.setCellStyle(styleNumber);
        Cell cell4 = row1.createCell(10); 
        cell4.setCellStyle(style);
        cell4.setCellValue("FlexClinic");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 9));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 14));

        Row row2 = sheet.createRow(2);
        createBlankCell(row2, style);
        for (int i = 0; i < DETAIL_HEAD.length; i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(DETAIL_HEAD[i]);
            cell.setCellStyle(style);
            cell.setCellStyle(styleCCB); 
        }
        
        Row row3 = sheet.createRow(3);
        createBlankCell(row3, style);
        for (int i = 0; i < DETAIL_HEAD_INDEX.length; i++) {
            Cell cell = row3.createCell(i + DETAIL_HEAD.length - 1);
            cell.setCellValue(DETAIL_HEAD_INDEX[i]);
            cell.setCellStyle(style);
            cell.setCellStyle(styleCCB);
        }

        Cell cellH = row3.createCell(DETAIL_HEAD_INDEX.length + DETAIL_HEAD.length - 1);
        cellH.setCellValue("ALSO APEAR IN");
        cellH.setCellStyle(style);
        cellH.setCellStyle(styleCCB);

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 14));

        int cols = 8;
        int r = 4;
        int stt = 1;

        for (TableDetail column : columns) {
            Row row = sheet.createRow(r++);
            row.setHeightInPoints(height);
            int j = 0;
            for (int i = 0; i < cols; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                String value = "";               
                switch (i) {
                    case 0: value = "" + stt; stt++; sheet.setColumnWidth(i, 1200); cell.setCellStyle(styleCC); break;
                    case 1: value = column.getCOLUMN_NAME(); sheet.setColumnWidth(i, 7000);break;
                    case 2: value = column.getDATA_TYPE(); sheet.setColumnWidth(i, 3500);break;
                    case 3: value = column.getCHARACTER_MAXIMUM_LENGTH(); sheet.setColumnWidth(i, 4000); cell.setCellStyle(styleNumber); break;
                    case 4: value = this.getCommentAuto(column); sheet.setColumnWidth(i, 10000);break;
                    case 5: value = column.getIS_NULLABLE().equals("NO") ? "\u2022" : ""; sheet.setColumnWidth(i, 2500); cell.setCellStyle(styleCC); break;
                    case 6: value = column.getCOLUMN_DEFAULT() ; sheet.setColumnWidth(i, 4000);break;
                    case 7: value = column.getCOLUMN_KEY().equals("PRI") ? "\u2022" : "" ; cell.setCellStyle(styleCC); break;
                
                    default:
                        break;
                }
                cell.setCellValue(value);
                j = i + 1;
            }

            // set index
            int indexs = 7;
            for (int i = 1; i <= indexs; i++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(styleCC);
                List<Integer> INDEXs = column.getSEQ_IN_INDEX();
                String value = "";
                try {
                    if(INDEXs != null) {
                        for (int k = 0; k < INDEXs.size(); k++) {
                            if(INDEXs.get(k).intValue() == i) {
                                value = "\u2022";
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                cell.setCellValue(value);
                j++;
            }

            Cell cellAPEAR_ON = row.createCell(j);
            cellAPEAR_ON.setCellStyle(style);
            cellAPEAR_ON.setCellValue(this.APEAR_ON(column) ? "" : column.getAPPEAR_ON());
            sheet.setColumnWidth(j, 10000);
        }

        row0.setHeightInPoints(height);
        row1.setHeightInPoints(height);
        row2.setHeightInPoints(height);
        row3.setHeightInPoints(height);
    }

    private final static String[] field_defaults = {"id","headquarter_id","create_by","create_date","update_by","update_date"};
    private boolean APEAR_ON (TableDetail column) { 
        for (int i = 0; i < field_defaults.length; i++) {
            if(column.getCOLUMN_NAME().equals(field_defaults[i])) return true;
        }
        return false;
    }

    private void createBlankCell (Row row, CellStyle style) {
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        int cells = 15;
        for (int i = 0; i < cells; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
        }
    }

    private void write (Workbook workbook) {
        // Lưu workbook vào tệp Excel
        try (FileOutputStream outputStream = new FileOutputStream(FILENAME)) {
            workbook.write(outputStream);
            System.out.println("Excel file has been created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    private boolean removeFile(String filePath) {
        File file = new File(filePath);
        // Kiểm tra nếu file tồn tại
        return file.exists() && file.delete();
    }

    private String getCommentAuto (TableDetail column) {
        if(column.getCOLUMN_KEY().equals("PRI")) return column.getCOLUMN_COMMENT();
        String field_name = column.getCOLUMN_NAME();
        String commend = this.COMMENT_DEFAULTS.get(field_name);

        if(commend == null || commend.equals("")) return column.getCOLUMN_COMMENT();

        String final_comment = Lib.isBlank(column.getCOLUMN_COMMENT()) ? "" : column.getCOLUMN_COMMENT() + "\n";
               final_comment += commend;

        return final_comment;
    }

    private HashMap<String, String> COMMENT_DEFAULTS = new HashMap<>();
    public ExcelExportService () {
        String lk = "Ref:";
        COMMENT_DEFAULTS.put("id", "Khóa chính tự tăng");
        COMMENT_DEFAULTS.put("headquarter_id", "Mã trụ sở");
        // COMMENT_DEFAULTS.put("create_date", "Ngày giờ bản ghi được tạo");
        // COMMENT_DEFAULTS.put("create_by", "Người tạo bản ghi");
        // COMMENT_DEFAULTS.put("update_date", "Thời điểm thực hiện cập nhật bản ghi");
        // COMMENT_DEFAULTS.put("update_by", "Người thực hiện cập nhật bản ghi");
        COMMENT_DEFAULTS.put("del_flag", "0: Chưa xóa, 1: Đã xóa");
        COMMENT_DEFAULTS.put("del_reason", "Lý do xóa");
        COMMENT_DEFAULTS.put("is_delete", "0: Chưa xóa, 1: Đã xóa");
        COMMENT_DEFAULTS.put("BHYT_flag", "0: Không BHYT, 1: Có BHYT");
        COMMENT_DEFAULTS.put("contract_id", "Mã hợp đồng \n" + lk + " \n hpm_company_contract.contract_id");
        COMMENT_DEFAULTS.put("company_id", "Mã công ty \n" + lk + " \n hpm_company.id");
        COMMENT_DEFAULTS.put("patient_id", "Mã bệnh nhân \n" + lk + " \n hpm_patient.patient_id");
        COMMENT_DEFAULTS.put("service_id", "Mã dịch vụ \n" + lk + " \n hpm_service.service_id");
        COMMENT_DEFAULTS.put("examine_time_id", "Mã lần khám \n" + lk + " \n hpm_examine_time.id");
        COMMENT_DEFAULTS.put("examine_id", "Mã lần khám \n" + lk + " \n hpm_examine_time.id");
        COMMENT_DEFAULTS.put("department_id", "Mã khoa phòng \n" + lk + " \n hpm_department.id");
        COMMENT_DEFAULTS.put("drug_id", "Mã thuốc, vật tư \n" + lk + " \n hpm_drug.id");
        COMMENT_DEFAULTS.put("perfusion_id", "Mã thuốc, dịch truyền \n" + lk + " \n hpm_drug.id");
        COMMENT_DEFAULTS.put("unit_id", "Mã đơn vị thuốc, vật tư \n" + lk + " \n hpm_unit.id");
        COMMENT_DEFAULTS.put("how_use_id", "Mã cách dùng thuốc, vật tư \n" + lk + " \n hpm_how_use.id");
        COMMENT_DEFAULTS.put("use_unit_id", "Mã đơn vị sử dụng \n" + lk + " \n hpm_drug_how_use.id");
        COMMENT_DEFAULTS.put("group_cure_id", "Mã họ trị liệu \n" + lk + " \n hpm_drug_group_cure.id");
        COMMENT_DEFAULTS.put("city_id", "Id tỉnh thành trong db \n" + lk + " \n hpm_city.city_id");
        COMMENT_DEFAULTS.put("district_id", "Id quận huyện trong db \n" + lk + " \n hpm_district.district_id");
        COMMENT_DEFAULTS.put("invoice_id", "Mã phiếu thu \n" + lk + " \n hpm_invoice.invoice_id");
        COMMENT_DEFAULTS.put("inspect_id", "Mã phiếu cận lâm sàng \n" + lk + " \n hpm_inspect.inspect_id");
        COMMENT_DEFAULTS.put("job_id", "Mã nghề nghiệp \n" + lk + " \n hpm_job.id");
        COMMENT_DEFAULTS.put("non_resident_document_id", "Mã hồ sơ khám \n" + lk + " \n hpm_nonresident_document.id");
        COMMENT_DEFAULTS.put("non_resident_id", "Mã đợt khám \n" + lk + " \n hpm_non_resident_id.non_resident_id");
        COMMENT_DEFAULTS.put("resident_id", "Mã hồ sơ nội trú \n" + lk + " \n hpm_resident_document.resident_id");
        COMMENT_DEFAULTS.put("settlement_payment_id", "Mã phiếu quyết toán \n" + lk + " \n hpm_settlement_payment.settlement_payment_id");
        COMMENT_DEFAULTS.put("nominate_id", "Mã phiếu chỉ định \n" + lk + " \n hpm_nominate_list.nominate_id");
        COMMENT_DEFAULTS.put("service_group_id", "Mã nhóm dịch vụ \n" + lk + " \n hpm_service_group_type.id");
        COMMENT_DEFAULTS.put("service_detail_id", "Mã dịch vụ chi tiết \n" + lk + " \n hpm_service_detail.id");
        COMMENT_DEFAULTS.put("room_id", "Mã phòng khám \n" + lk + " \n hpm_examine_room.id");
        COMMENT_DEFAULTS.put("voucher_out_id", "Mã phiếu hóa đơn xuất \n" + lk + " \n hpm_drug_voucher_out.voucher_out_id");
        COMMENT_DEFAULTS.put("voucher_out_detail_id", "Chi tiết hóa đơn xuất \n" + lk + " \n hpm_drug_voucher_out_detail.id");
        COMMENT_DEFAULTS.put("voucher_in_id", "Mã phiếu hóa đơn nhập \n" + lk + " \n hpm_drug_voucher_in.voucher_in_id");
        COMMENT_DEFAULTS.put("voucher_in_detail_id", "Chi tiết hóa đơn nhập \n" + lk + " \n hpm_drug_voucher_in_detail.id");
        COMMENT_DEFAULTS.put("order_id", "Mã phiếu dự trù \n" + lk + " \n hpm_drug_order.order_id");
        COMMENT_DEFAULTS.put("invoice_retail_id", "Mã phiếu xuất thuốc \n" + lk + " \n hpm_drug_invoice_retail.invoice_retail_id");
        COMMENT_DEFAULTS.put("machine_id", "Mã máy \n" + lk + " \n hpm_machine.machine_id");
        COMMENT_DEFAULTS.put("position_id", "Mã vị trí nhân viên \n" + lk + " \n hpm_position.id");
        COMMENT_DEFAULTS.put("primary_surgery_doc_id", "Mã phiếu kỹ thuật thủ thuật \n" + lk + " \n hpm_primary_surgery_document.primary_surgery_doc_id");
        COMMENT_DEFAULTS.put("hospitalize_doc_id", "Mã nhập viện nội trú \n" + lk + " \n hpm_hospitalize_document.id");
        COMMENT_DEFAULTS.put("hospitalized_doc_id", "Mã nhập viện nội trú \n" + lk + " \n hpm_hospitalize_document.id");
        COMMENT_DEFAULTS.put("hos_doc_id", "Mã nhập viện nội trú \n" + lk + " \n hpm_hospitalize_document.id");
        COMMENT_DEFAULTS.put("treaty_id", "Mã y lệnh, xử trí điều trị nội trú \n" + lk + " \n hpm_resident_treaty.treaty_id");
        COMMENT_DEFAULTS.put("case_record_id", "Mã bệnh án nội trú \n" + lk + " \n hpm_resident_case_record_document.case_record_id");
        COMMENT_DEFAULTS.put("provider_id", "Mã nhà cung cấp\n" + lk + " \n hpm_drug_provider.provider_id");
        COMMENT_DEFAULTS.put("blood_id", "Mã máu\n" + lk + " \n hpm_blood.blood_id");
        COMMENT_DEFAULTS.put("invoice_refund_id", "Mã phiếu hoàn phí\n" + lk + " \n hpm_drug_invoice_refund.invoice_refund_id");
        COMMENT_DEFAULTS.put("liquidation_return_id", "Mã phiếu trả thuốc\n" + lk + " \n hpm_drug_liquidation_return.liquidation_return_id");
        COMMENT_DEFAULTS.put("voucher_in_detail_id", "Mã chi tiết phiếu nhập thuốc\n" + lk + " \n hpm_drug_voucher_in_detail.id");
        COMMENT_DEFAULTS.put("root_voucher_in_id", "Mã phiếu nhập ban đầu của thuốc vào Kho chẵn\n" + lk + " \n hpm_drug_voucher_in.voucher_in_id");
        COMMENT_DEFAULTS.put("restitution_id", "Mã phiếu trả thuốc\n" + lk + " \n hpm_drug_restitution.restitution_id");
        COMMENT_DEFAULTS.put("receiving_id", "Mã phiếu tiếp nhận\n" + lk + " \n hpm_patient_recieving.receiving_id");
        COMMENT_DEFAULTS.put("order_form_id", "Mã phiếu đặt hàng\n" + lk + " \n hpm_order_form.order_form_id");
        COMMENT_DEFAULTS.put("primary_surgery_doc_id", "Mã phiếu thủ thuật\n" + lk + " \n hpm_primary_surgery_document.primary_surgery_doc_id");
        COMMENT_DEFAULTS.put("primary_surgery_method_doc_id", "Mã phiếu thủ thuật\n" + lk + " \n hpm_primary_surgery_method_list.id");
        COMMENT_DEFAULTS.put("position_surgery_list_id", "Mã vị trí thủ thuật\n" + lk + " \n hpm_position_surgery_list.id");
        COMMENT_DEFAULTS.put("anaethesia_method_id", "Mã phương pháp vô cảm\n" + lk + " \n hpm_anaethesia_method_list.id");
        COMMENT_DEFAULTS.put("decubitus_surgery_id", "Mã tư thế thủ thuật\n" + lk + " \n hpm_decubitus_surgery_list.id");
        COMMENT_DEFAULTS.put("surgery_method_id", "Mã phương pháp mổ\n" + lk + " \n hpm_primary_surgery_method_list.id");
        COMMENT_DEFAULTS.put("nursing_take_care_id", "Mã chăm sóc\n" + lk + " \n hpm_nursing_take_care.id");
        COMMENT_DEFAULTS.put("surgery_anesthesia_doc_id", "Mã phiếu gây mê\n" + lk + " \n hpm_surgery_anesthesia_document.surgery_anesthesia_doc_id");
        COMMENT_DEFAULTS.put("examine_room_id", "Mã phòng khám\n" + lk + " \n hpm_surgery_anesthesia_document.surgery_anesthesia_doc_id");


        COMMENT_DEFAULTS.put("invoice_department_id", lk + " \n hpm_drug_invoice_department.invoice_department_id");
        COMMENT_DEFAULTS.put("invoice_department_detail_id", lk + " \n hpm_drug_invoice_department_detail.id");

    }

    private final static String[] TABLE_ON_DB = {"invoice_vat", "invoice_vat_detail", "invoice_vat_link"};
    private boolean table_on_db (TableEntity table) {
        for (int i = 0; i < TABLE_ON_DB.length; i++) {
            if(TABLE_ON_DB[i].equals(table.getTABLE_NAME())) return true;
        }
        return false;
    }
}