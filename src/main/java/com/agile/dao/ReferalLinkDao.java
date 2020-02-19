package com.agile.dao;

import com.agile.pojo.ReferalLink;
import com.agile.pojo.example.ReferalLinkExample;

import java.util.List;

public interface ReferalLinkDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReferalLink record);

    int insertSelective(ReferalLink record);

    List<ReferalLink> selectByExample(ReferalLinkExample example);

    ReferalLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReferalLink record);

    int updateByPrimaryKey(ReferalLink record);
}
