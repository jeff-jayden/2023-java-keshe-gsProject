package com.it.service.impl;

import com.it.dao.api.TopicResDao;
import com.it.dao.impl.TopicResDaoImpl;
import com.it.pojo.PageBean;
import com.it.pojo.TopicRes;
import com.it.service.api.TopicResService;

import java.util.List;

public class TopicResServiceImpl implements TopicResService {

    private TopicResDao topicResDao = new TopicResDaoImpl();

    @Override
    public PageBean<TopicRes> selectByPageAndCondition(int currentPage, int pageSize, TopicRes topicRes) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        String toname = topicRes.getToname();
        String tname = topicRes.getTname();
        String sname = topicRes.getSname();
        if (toname != null && toname.length() > 0){
            topicRes.setToname("%" + toname + "%");
        }
        if (tname != null && tname.length() > 0){
            topicRes.setTname("%" + tname + "%");
        }
        if (sname != null && sname.length() > 0){
            topicRes.setTname("%" + sname + "%");
        }

        List<TopicRes> topicResList = topicResDao.selectByPageAndCondition(begin, size, topicRes);
        int total = topicResDao.selectTotalCountByCondition(topicRes);

        PageBean<TopicRes> pageBean = new PageBean<>();
        pageBean.setRows(topicResList);
        pageBean.setTotalCount(total);

        return pageBean;
    }

    @Override
    public TopicRes selectById(Integer id) {
        return topicResDao.selectById(id);
    }

    @Override
    public boolean saveTopicRes(TopicRes topicRes) {
        return topicResDao.saveTopicRes(topicRes) > 0;
    }

    @Override
    public boolean deleteTopicRes(Integer id) {
        return topicResDao.deleteTopicRes(id) > 0;
    }

    @Override
    public boolean updateTopicRes(TopicRes topicRes) {
        return topicResDao.updateTopicRes(topicRes) > 0;
    }

    @Override
    public boolean deleteByDoubleName(String tusername, String susername) {
        return topicResDao.deleteByDoubleName(tusername, susername) > 0;
    }

    @Override
    public List<TopicRes> selectAll() {
        return topicResDao.selectAll();
    }

    @Override
    public TopicRes selectBySname(String username) {
        return topicResDao.selectBySname(username);
    }


}
