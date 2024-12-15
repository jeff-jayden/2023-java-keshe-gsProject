package com.it.dao.impl;

import com.it.dao.api.TopicDao;
import com.it.pojo.Teacher;
import com.it.pojo.Topic;
import com.it.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl implements TopicDao {
    Connection conn = DBUtil.getConnection();
    //数据总数
    public int selectTotalCountByCondition(Topic topic) {
        String sql1 = "select * from tb_topic";
        String sql2 = "select * from tb_topic where Toname like ? or Ttype like ?";

        PreparedStatement pstmt = null;
        List<Topic> list = new ArrayList<>();
        try {
            String toname = topic.getToname();
            String ttype = topic.getTtype();
            if (toname.length() == 0 && ttype.length() == 0) {
                pstmt = conn.prepareStatement(sql1);
            } else {
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, topic.getToname());
                pstmt.setString(2, topic.getTtype());
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Topic(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getInt(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list.size();
    }

    //分页及条件查询
    public List<Topic> selectByPageAndCondition(int begin, int size, Topic topic) {
        String sql1 = "select * from tb_topic limit ?,?";
        String sql2 = "select * from tb_topic where Toname like ? or Ttype like ? limit ?,?";

        PreparedStatement pstmt = null;
        List<Topic> list = new ArrayList<>();
        try {
            String toname = topic.getToname();
            String ttype = topic.getTtype();
            if (toname.length() == 0 && ttype.length() == 0) {
                pstmt = conn.prepareStatement(sql1);
                pstmt.setInt(1, begin);
                pstmt.setInt(2, size);
            } else {
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, topic.getToname());
                pstmt.setString(2, topic.getTtype());
                pstmt.setInt(3, begin);
                pstmt.setInt(4, size);
            }
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                list.add(new Topic(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getInt(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //根据id查询
    public Topic selectById(Integer id) {
        String sql = "select * from tb_topic where id = ?";
        Topic topic = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                topic = new Topic(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5), re.getString(6),
                        re.getInt(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topic;
    }

    //保存数据
    public Integer saveTopic(Topic topic) {
        String sql = "insert into tb_topic(id,Toname,Ttype,Tbackground,Tdescription)values(null,?,?,?,?)";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topic.getToname());
            pstmt.setString(2, topic.getTtype());
            pstmt.setString(3, topic.getTbackground());
            pstmt.setString(4, topic.getTdescription());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //删除数据
    public Integer deleteTopic(Integer id) {
        String sql = "delete from tb_topic where id = ?";
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
    public Integer updateTopic(Topic topic) {
        String sql = "update tb_topic set Toname=?,Ttype=?," +
                "Tbackground=?,Tdescription=?, studentID=? ,status=? where id=?";
        int res = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topic.getToname());
            pstmt.setString(2, topic.getTtype());
            pstmt.setString(3, topic.getTbackground());
            pstmt.setString(4, topic.getTdescription());
            pstmt.setString(5, topic.getStudentID());
            pstmt.setInt(6, topic.getStatus());
            pstmt.setInt(7, topic.getId());
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //查询所有
    public List<Topic> queryAllTopic() {
        List<Topic> list = new ArrayList<>();
        String sql = "select * from tb_topic";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                Topic topic = new Topic(re.getInt(1), re.getString(2),
                        re.getString(3), re.getString(4),
                        re.getString(5),re.getString(6),
                        re.getInt(7));
                list.add(topic);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
