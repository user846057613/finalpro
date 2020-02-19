package com.agile.service;

import com.agile.pojo.Paper;

import java.util.List;

public interface PaperService {
    int addPaper(Paper paper);
    int deletePaperById(long id);
    int updatePaper(Paper paper);
    Paper queryById(long id);

    List<Paper> queryAllPaper();
}
