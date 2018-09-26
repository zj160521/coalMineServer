package com.cm.service;

import com.cm.dao.CurvecolorDao;
import com.cm.entity.CurveColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvecolorService {
    @Autowired
    private CurvecolorDao dao;

    public void add(CurveColor color){
        dao.add(color);
    }

    public void update(CurveColor color){
        dao.update(color);
    }

    public CurveColor getAll(){
        return dao.getAll();
    }

    public void delete(int id){
        dao.delete(id);
    }
}
