package com.it.service.impl;

import com.it.dao.api.TopicDao;
import com.it.dao.impl.TopicDaoImpl;
import com.it.pojo.PageBean;
import com.it.pojo.Teacher;
import com.it.pojo.Topic;
import com.it.service.api.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicDao topicDao = new TopicDaoImpl();

    @Override
    public PageBean<Topic> selectByPageAndCondition(int currentPage, int pageSize, Topic topic) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        String toname = topic.getToname();
        String ttype = topic.getTtype();
        if (toname != null && toname.length() > 0){
            topic.setToname("%" + toname + "%");
        }
        if (ttype != null && ttype.length() > 0){
            topic.setTtype("%" + ttype + "%");
        }

        List<Topic> topics = topicDao.selectByPageAndCondition(begin, size, topic);
        int total = topicDao.selectTotalCountByCondition(topic);

        PageBean<Topic> pageBean = new PageBean<>();
        pageBean.setRows(topics);
        pageBean.setTotalCount(total);

        return pageBean;
    }

    @Override
    public Topic selectById(Integer id) {
        return topicDao.selectById(id);
    }

    @Override
    public boolean saveTopic(Topic topic) {
        return topicDao.saveTopic(topic) > 0;
    }

    @Override
    public boolean deleteTopic(Integer id) {
        return topicDao.deleteTopic(id) > 0;
    }

    @Override
    public boolean updateTopic(Topic topic) {
        return topicDao.updateTopic(topic) > 0;
    }

    @Override
    public List<Topic> queryAllTopic() {
        return topicDao.queryAllTopic();
    }
}
