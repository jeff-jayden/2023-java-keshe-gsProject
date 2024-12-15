package com.it;

import com.it.pojo.Student;
import com.it.pojo.Teacher;
import com.it.pojo.User;
import com.it.service.api.AdminService;
import com.it.service.api.LoginService;
import com.it.service.api.StudentService;
import com.it.service.api.TeacherService;
import com.it.service.impl.AdminServiceImpl;
import com.it.service.impl.LoginServiceImpl;
import com.it.service.impl.StudentServiceImpl;
import com.it.service.impl.TeacherServiceImpl;
import com.it.util.DBUtil;
import com.it.util.MD5Util;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {

    Connection con = DBUtil.getConnection();

    LoginService loginService = new LoginServiceImpl();

    AdminService adminService = new AdminServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    TeacherService teacherService = new TeacherServiceImpl();


    public static final String PATH = "src/main/resources";

    @Test
    public void test01(){
        String s = "123";
        System.out.println(s.length());
    }

    @Test
    public void exportExcel(){
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建行和单元格，并填充数据
        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("姓名");
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("年龄");

        Row dataRow = sheet.createRow(1);
        Cell dataCell1 = dataRow.createCell(0);
        dataCell1.setCellValue("张三");
        Cell dataCell2 = dataRow.createCell(1);
        dataCell2.setCellValue(25);

        // 保存工作簿到文件
        try (FileOutputStream outputStream = new FileOutputStream(PATH + "output.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel导出成功！");
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

    @Test
    public void test11(){
        String encode = MD5Util.encode("202CB962AC59075B964B07152D234B70");
        System.out.println(encode);
    }

    @Test
    public void test10(){
        Teacher teacher = loginService.teacherLogin("张三", "123");
        System.out.println(teacher);
    }

    @Test
    public void test1(){
        Student s = new Student();
        s.setSnum("213231231");
        s.setSusername("zhangsan");
        s.setSpassword("122131");
        s.setSsex("男");
        s.setSbirth("20231322");
        s.setSphone("1342343432");
        s.setSoption(1);
        studentService.saveStudent(s);
    }

    @Test
    public void test2(){
        Teacher t = new Teacher();
        t.setTnum("21321312");
        t.setTusername("zhangdan");
        t.setTpassword("23123");
        t.setTsex("女");
        t.setTpro("教授");
        t.setToption("1");
        teacherService.saveTeacher(t);
    }

    @Test
    public void test3(){
        studentService.deleteStudent(3);
    }

    @Test
    public void test4(){
        teacherService.deleteTeacher(1);
    }

    @Test
    public void test5(){
        Student s = new Student();
        s.setId(4);
        s.setSnum("213231265");
        s.setSusername("wangwu");
        s.setSpassword("122131");
        s.setSsex("女");
        s.setSbirth("20231322");
        s.setSphone("1342343432");
        s.setSoption(1);
        studentService.updateStudent(s);
    }
    @Test
    public void test6(){
        Teacher t = new Teacher();
        t.setId(1);
        t.setTnum("21321312");
        t.setTusername("zhangdan");
        t.setTpassword("23123");
        t.setTsex("男");
        t.setTpro("教授");
        t.setToption("1");
        teacherService.updateTeacher(t);
    }

    @Test
    public void test7(){
        List<Student> students = studentService.queryAllStudent();
        System.out.println(students);
    }

    @Test
    public void test8(){
        List<Teacher> teachers = teacherService.queryAllTeacher();
        System.out.println(teachers);
    }

    @Test
    public void test9(){
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1,2,3,3);
        System.out.println(list);
    }



}
