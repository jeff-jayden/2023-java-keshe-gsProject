package com.it.service.api;

import com.it.pojo.PageBean;
import com.it.pojo.TopicRes;

import java.util.List;

public interface TopicResService {
    //查
    PageBean<TopicRes> selectByPageAndCondition(int currentPage, int pageSize, TopicRes topicRes);

    TopicRes selectById(Integer id);

    //增
    boolean saveTopicRes(TopicRes topic);
    //删
    boolean deleteTopicRes(Integer id);
    //改
    boolean updateTopicRes(TopicRes topic);
    boolean deleteByDoubleName(String tusername, String susername);
    List<TopicRes> selectAll();

    TopicRes selectBySname(String username);
}
