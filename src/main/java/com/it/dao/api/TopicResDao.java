package com.it.dao.api;

import com.it.pojo.TopicRes;

import java.util.List;

public interface TopicResDao {
    int selectTotalCountByCondition(TopicRes topicRes);
    List<TopicRes> selectByPageAndCondition(int begin, int size, TopicRes topicRes);
    TopicRes selectById(Integer id);
    Integer saveTopicRes(TopicRes t);
    Integer deleteTopicRes(Integer id);
    Integer updateTopicRes(TopicRes topicRes);
    Integer deleteByDoubleName(String tusername, String susername);
    List<TopicRes> selectAll();

    TopicRes selectBySname(String username);
}
