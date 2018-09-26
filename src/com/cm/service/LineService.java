package com.cm.service;

import com.cm.dao.LineDao;
import com.cm.entity.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {

    @Autowired
    private LineDao lineDao;

    public void add(Line line){
        lineDao.add(line);
    }

    public void update(Line line){
        lineDao.update(line);
    }

    public List<Line> getAll(){
        return lineDao.getAll();
    }

    public void delete(int id){
        lineDao.delete(id);
    }

    public List<Line> getByType(int type){
        return lineDao.getByType(type);
    }
}
