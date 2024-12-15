package com.it.dao.impl;

import com.it.dao.api.StudentDao;
import com.it.pojo.Student;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    Connection conn = DBUtil.getConnection();

    //数据总数
    public int selectTotalCountByCondition(Student student) {
        String sql1 = "select * from tb_student";
        String sql2 = "select * from tb_student where Snum like ? or Susername like ?";

        PreparedStatement pstmt = null;
        List<Student> list = new ArrayList<>();
        try {
            String snum = student.getSnum();
            String susername = student.getSusername();
            if(snum.length() == 0 && susername.length() == 0){
                pstmt = conn.prepareStatement(sql1);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, student.getSnum());
                pstmt.setString(2, student.getSusername());
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Student(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7),re.getInt(8),
                        re.getInt(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list.size();
    }

    //分页及条件查询
    public List<Student> selectByPageAndCondition(int begin, int size, Student student) {
        String sql1 = "select * from tb_student limit ?,?";
        String sql2 = "select * from tb_student where Snum like ? or Susername like ? limit ?,?";

        PreparedStatement pstmt = null;
        List<Student> list = new ArrayList<>();
        try {
            String snum = student.getSnum();
            String susername = student.getSusername();
            if(snum.length() == 0 && susername.length() == 0){
                pstmt = conn.prepareStatement(sql1);
                pstmt.setInt(1,begin);
                pstmt.setInt(2,size);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, student.getSnum());
                pstmt.setString(2, student.getSusername());
                pstmt.setInt(3, begin);
                pstmt.setInt(4, size);
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Student(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7),re.getInt(8),
                        re.getInt(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //根据id查询
    public Student selectById(Integer id) {
        String sql = "select * from tb_student where id = ?";
        Student student = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                student = new Student(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7),re.getInt(8),
                        re.getInt(9));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    //保存数据
    public Integer saveStudent(Student student) {
        String sql = "insert into tb_student(id,Snum,Susername,Spassword,Ssex,Sbirth,Sphone,Soption)values(null,?,?,?,?,?,?,?)";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getSnum());
            pstmt.setString(2, student.getSusername());
            pstmt.setString(3, student.getSpassword());
            pstmt.setString(4, student.getSsex());
            pstmt.setString(5, student.getSbirth());
            pstmt.setString(6, student.getSphone());
            pstmt.setInt(7, student.getSoption());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //删除数据
    public Integer deleteStudent(Integer id) {
        String sql = "delete from tb_student where id = ?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //更新数据
    public Integer updateStudent(Student student) {
        String sql = "update tb_student set Snum=?,Susername=?," +
                "Spassword=?,Ssex=?,Sbirth=?,Sphone=?,Soption=? where id=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getSnum());
            pstmt.setString(2, student.getSusername());
            pstmt.setString(3, student.getSpassword());
            pstmt.setString(4, student.getSsex());
            pstmt.setString(5, student.getSbirth());
            pstmt.setString(6, student.getSphone());
            pstmt.setInt(7, student.getSoption());
            pstmt.setInt(8, student.getId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //更新选择数据
    public Integer updateSelectStudent(Student student) {
        String sql = "update tb_student set Snum=?,Susername=?," +
                "Spassword=?,Ssex=?,Sbirth=?,Sphone=?,Soption=?,Status=? where id=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getSnum());
            pstmt.setString(2, student.getSusername());
            pstmt.setString(3, student.getSpassword());
            pstmt.setString(4, student.getSsex());
            pstmt.setString(5, student.getSbirth());
            pstmt.setString(6, student.getSphone());
            pstmt.setInt(7, student.getSoption());
            pstmt.setInt(8, student.getStatus());
            pstmt.setInt(9, student.getId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<Student> selectAll() {
        String sql = "select * from tb_student";
        PreparedStatement pstmt = null;
        List<Student> list = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Student(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7),re.getInt(8),
                re.getInt(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
