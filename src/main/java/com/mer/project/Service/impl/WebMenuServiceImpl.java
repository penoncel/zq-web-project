package com.mer.project.Service.impl;

import com.mer.project.Dao.WebMenuDao;
import com.mer.project.Pojo.WebMenu;
import com.mer.project.Pojo.WebPage;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhoaqi 15701556037
 */
@Service
@Transactional
public class WebMenuServiceImpl implements WebMenuService {

    @Autowired
    private WebMenuDao menuDao;

    @Override
    public String getMax_Lowerlevel(String superior) {
        return menuDao.getMax_Lowerlevel(superior);
    }

    @Override
    public String getMax_Superior() {
        return menuDao.getMax_Superior();
    }

    @Override
    public List<Map<String,Object>> getUserMenu(WebUser webUser) {
        return menuDao.getUserMenu(webUser);
    }

    @Override
    public int insert(WebMenu webMenu) {
        return menuDao.insert(webMenu);
    }

    @Override
    public int update(WebMenu webMenu) {
        return menuDao.update(webMenu);
    }

    @Override
    public int delete(int id) {
        return menuDao.delete(id);
    }

    @Override
    public List<Map<String, Object>> getList() {
        return menuDao.getList();
    }

    @Override
    public List<Map<String, Object>> getTree() {
        return menuDao.getTree();
    }

    @Override
    public WebMenu getOneInt(int id) {
        return menuDao.getOneInt(id);
    }

    @Override
    public int getNumber(int parents) {
        return menuDao.getNumber(parents);
    }

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        return null;
    }

}
