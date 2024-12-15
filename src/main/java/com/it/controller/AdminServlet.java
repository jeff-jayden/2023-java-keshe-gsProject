package com.it.controller;

import com.it.pojo.TopicRes;
import com.it.service.api.TopicResService;
import com.it.service.impl.TopicResServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends BaseServlet {

    private TopicResService topicResService = new TopicResServiceImpl();

    String desktopPath = System.getProperty("user.home") + "/Desktop/";
    String filePath = desktopPath + "output.xlsx";


    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("毕设学生分配结果");

        // 创建行和单元格，并填充数据
        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("分配结果ID");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("课设题目");
        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("老师姓名");
        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("学生姓名");

        List<TopicRes> topicResList = data();
        for (int i = 0; i < topicResList.size(); i++) {
            Row bodyRow = sheet.createRow(i + 1);
            Cell bodyCell1 = bodyRow.createCell(0);
            bodyCell1.setCellValue(topicResList.get(i).getId());
            Cell bodyCell2 = bodyRow.createCell(1);
            bodyCell2.setCellValue(topicResList.get(i).getToname());
            Cell bodyCell3 = bodyRow.createCell(2);
            bodyCell3.setCellValue(topicResList.get(i).getTname());
            Cell bodyCell4 = bodyRow.createCell(3);
            bodyCell4.setCellValue(topicResList.get(i).getSname());
        }


        // 保存工作簿到文件
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            System.out.println("Excel导出成功！");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭工作簿
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<TopicRes> data() {
        List<TopicRes> topicResList = topicResService.selectAll();
        System.out.println(topicResList);
        return topicResList;
    }


    /*public void exportCsv(HttpServletRequest request, HttpServletResponse response) {
        List<TopicRes> topicResList = topicResService.selectAll();
        System.out.println(topicResList);

        try {
            FileWriter fw = new FileWriter("src\\main\\resources\\demo.csv", false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            pw.print("分配结果ID");
            pw.print(",");
            pw.print("课设题目");
            pw.print(",");
            pw.print("老师姓名");
            pw.print(",");
            pw.print("学生姓名");
            pw.println();

            for (int i = 0; i < topicResList.size(); i++) {
                TopicRes topicRes = topicResList.get(i);
                pw.write(topicRes.getId().toString());
                pw.write(",");
                pw.write(topicRes.getToname());
                pw.write(",");
                pw.write(topicRes.getTname());
                pw.write(",");
                pw.write(topicRes.getSname());
                pw.println();
            }

            pw.close();

            System.out.println("成功导出csv");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("success");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/

    /*public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(PATH + "毕设学生分配结果", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), TopicRes.class).sheet("毕设学生分配结果").doWrite(data());
    }*/


}
