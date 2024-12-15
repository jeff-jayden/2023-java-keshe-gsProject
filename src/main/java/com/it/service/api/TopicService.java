package com.it.service.api;

import com.it.pojo.PageBean;
import com.it.pojo.Topic;

import java.util.List;

public interface TopicService {
    PageBean<Topic> selectByPageAndCondition(int currentPage, int pageSize, Topic topic);

    Topic selectById(Integer id);

    //增
    boolean saveTopic(Topic topic);
    //删
    boolean deleteTopic(Integer id);
    //改
    boolean updateTopic(Topic topic);
    //查
    List<Topic> queryAllTopic();
}
