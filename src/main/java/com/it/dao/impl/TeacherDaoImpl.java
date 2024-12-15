package com.it.dao.impl;

import com.it.dao.api.TeacherDao;
import com.it.pojo.Teacher;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    Connection conn = DBUtil.getConnection();
    //数据总数
    public int selectTotalCountByCondition(Teacher teacher) {
        String sql1 = "select * from tb_teacher";
        String sql2 = "select * from tb_teacher where Tnum like ? or Tusername like ?";

        PreparedStatement pstmt = null;
        List<Teacher> list = new ArrayList<>();
        try {
            String tnum = teacher.getTnum();
            String tusername = teacher.getTusername();
            if(tnum.length() == 0 && tusername.length() == 0){
                pstmt = conn.prepareStatement(sql1);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, teacher.getTnum());
                pstmt.setString(2, teacher.getTusername());
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Teacher(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list.size();
    }

    //分页及条件查询
    public List<Teacher> selectByPageAndCondition(int begin, int size, Teacher teacher) {
        String sql1 = "select * from tb_teacher limit ?,?";
        String sql2 = "select * from tb_teacher where Tnum like ? or Tusername like ? limit ?,?";

        PreparedStatement pstmt = null;
        List<Teacher> list = new ArrayList<>();
        try {
            String tnum = teacher.getTnum();
            String tusername = teacher.getTusername();
            if(tnum.length() == 0 && tusername.length() == 0){
                pstmt = conn.prepareStatement(sql1);
                pstmt.setInt(1,begin);
                pstmt.setInt(2,size);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, teacher.getTnum());
                pstmt.setString(2, teacher.getTusername());
                pstmt.setInt(3, begin);
                pstmt.setInt(4, size);
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Teacher(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //根据id查询
    public Teacher selectById(Integer id) {
        String sql = "select * from tb_teacher where id = ?";
        Teacher t = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                t = new Teacher(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    //保存数据
    public Integer saveTeacher(Teacher t) {
        String sql = "insert into tb_teacher(id,Tnum,Tusername,Tpassword,Tsex,Tpro,Toption)values(null,?,?,?,?,?,?)";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, t.getTnum());
            pstmt.setString(2, t.getTusername());
            pstmt.setString(3, t.getTpassword());
            pstmt.setString(4, t.getTsex());
            pstmt.setString(5, t.getTpro());
            pstmt.setString(6, t.getToption());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //删除数据
    public Integer deleteTeacher(Integer id) {
        String sql = "delete from tb_teacher where id = ?";
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
    public Integer updateTeacher(Teacher teacher) {
        String sql = "update tb_teacher set Tnum=?,Tusername=?," +
                "Tpassword=?,Tsex=?,Tpro=?,Toption=? where id=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher.getTnum());
            pstmt.setString(2, teacher.getTusername());
            pstmt.setString(3, teacher.getTpassword());
            pstmt.setString(4, teacher.getTsex());
            pstmt.setString(5, teacher.getTpro());
            pstmt.setString(6, teacher.getToption());
            pstmt.setInt(7, teacher.getId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //查询所有
    public List<Teacher> queryAllTeacher() {
        List<Teacher> list = new ArrayList<>();
        String sql = "select * from tb_teacher";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                Teacher t = new Teacher(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getString(7));
                list.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
