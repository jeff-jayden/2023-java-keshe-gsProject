package com.it.dao.impl;

import com.it.dao.api.TopicResDao;
import com.it.pojo.TopicRes;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicResDaoImpl implements TopicResDao {
    Connection conn = DBUtil.getConnection();

    //数据总数
    public int selectTotalCountByCondition(TopicRes topicRes) {
        String sql1 = "select * from tb_result";
        String sql2 = "select * from tb_result where Toname like ? or Tname like ? or Sname like ?";

        PreparedStatement pstmt = null;
        List<TopicRes> list = new ArrayList<>();
        try {
            String toname = topicRes.getToname();
            String tname = topicRes.getTname();
            String sname = topicRes.getSname();
            if(toname.length() == 0 && tname.length() == 0 && sname.length() == 0){
                pstmt = conn.prepareStatement(sql1);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, topicRes.getToname());
                pstmt.setString(2, topicRes.getTname());
                pstmt.setString(3, topicRes.getSname());
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new TopicRes(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list.size();
    }

    //分页及条件查询
    public List<TopicRes> selectByPageAndCondition(int begin, int size, TopicRes topicRes) {
        String sql1 = "select * from tb_result limit ?,?";
        String sql2 = "select * from tb_result where Toname like ? or Tname like ? or Sname like ? limit ?,?";

        PreparedStatement pstmt = null;
        List<TopicRes> list = new ArrayList<>();
        try {
            String toname = topicRes.getToname();
            String tname = topicRes.getTname();
            String sname = topicRes.getSname();
            if(toname.length() == 0 && tname.length() == 0 && sname.length() == 0){
                pstmt = conn.prepareStatement(sql1);
                pstmt.setInt(1,begin);
                pstmt.setInt(2,size);
            }else{
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, topicRes.getToname());
                pstmt.setString(2, topicRes.getTname());
                pstmt.setString(3, topicRes.getSname());
                pstmt.setInt(4, begin);
                pstmt.setInt(5, size);
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new TopicRes(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //根据id查询
    public TopicRes selectById(Integer id) {
        String sql = "select * from tb_result where id = ?";
        TopicRes topicRes = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                topicRes = new TopicRes(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicRes;
    }

    //保存数据
    public Integer saveTopicRes(TopicRes topicRes) {
        String sql = "insert into tb_result(id,Toname,Tname,Sname)values(null,?,?,?)";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topicRes.getToname());
            pstmt.setString(2, topicRes.getTname());
            pstmt.setString(3, topicRes.getSname());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //删除数据
    public Integer deleteTopicRes(Integer id) {
        String sql = "delete from tb_result where id = ?";
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
    public Integer updateTopicRes(TopicRes topicRes) {
        String sql = "update tb_result set Toname=?,Tname=?," +
                "Sname=? where id=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topicRes.getToname());
            pstmt.setString(2, topicRes.getTname());
            pstmt.setString(3, topicRes.getSname());
            pstmt.setInt(4, topicRes.getId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public Integer deleteByDoubleName(String tusername, String susername) {
        String sql = "delete from tb_result where Tname=? and Sname=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,tusername);
            pstmt.setString(2,susername);
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<TopicRes> selectAll() {
        String sql = "select * from tb_result";
        List<TopicRes> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                list.add(new TopicRes(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public TopicRes selectBySname(String username) {
        String sql = "select * from tb_result where Sname = ?";
        TopicRes topicRes = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet re = pstmt.executeQuery();
            while (re.next()){
                topicRes = new TopicRes(re.getInt(1),re.getString(2),
                        re.getString(3),re.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicRes;
    }
}
