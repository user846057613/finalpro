package com.agile.service.impl;

import com.agile.dao.ReferalLinkDao;
import com.agile.pojo.ReferalLink;
import com.agile.pojo.example.ReferalLinkExample;
import com.agile.service.ReferalLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferalLinkServiceImpl implements ReferalLinkService {

    @Autowired
    private ReferalLinkDao referalLinkDao = null;

    @Override
    public List<ReferalLink> listAll() {
        ReferalLinkExample example = new ReferalLinkExample();
        return referalLinkDao.selectByExample(example);
    }

    @Override
    public void update(ReferalLink link) {
        referalLinkDao.updateByPrimaryKey(link);
    }

    @Override
    public void updateLinkText(Integer id, String text) {
        ReferalLink link = get(id);
        link.setText(text);
        referalLinkDao.updateByPrimaryKey(link);
    }

    @Override
    public void updateLinkLink(Integer id, String link) {
        ReferalLink referalLink = get(id);
        referalLink.setLink(link);
        referalLinkDao.updateByPrimaryKey(referalLink);
    }

    @Override
    public ReferalLink get(Integer id) {
        return referalLinkDao.selectByPrimaryKey(id);
    }
}
