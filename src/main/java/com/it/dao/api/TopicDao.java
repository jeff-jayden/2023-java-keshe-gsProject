package com.it.dao.api;

import com.it.pojo.Topic;

import java.util.List;

public interface TopicDao {
    int selectTotalCountByCondition(Topic topic);
    List<Topic> selectByPageAndCondition(int begin, int size, Topic topic);
    Topic selectById(Integer id);
    Integer saveTopic(Topic t);
    Integer deleteTopic(Integer id);
    Integer updateTopic(Topic topic);
    List<Topic> queryAllTopic();
}
