package com.mer.project.Service.impl;


import com.mer.project.Dao.SysFtpDao;
import com.mer.project.Pojo.SysFtp;
import com.mer.project.Pojo.WebPage;
import com.mer.project.Service.SysFtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class SysFtpServiceImpl implements SysFtpService {

    @Autowired
    private SysFtpDao sysFtpDao;

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        return null;
    }

    @Override
    public SysFtp getOne(Integer id) {
        return sysFtpDao.getOne(id);
    }


    @Override
    public void insert_nuomansitrad(Object parameter) {
        sysFtpDao.insert_nuomansitrad(parameter);
    }
}
