package com.cm.service;

import com.cm.dao.FilecontentDao;
import com.cm.entity.Filecontent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilecontentService {

    @Autowired
    private FilecontentDao dao;

    public void add(Filecontent filecontent){
        dao.add(filecontent);
    }

    public List<Filecontent> getAll(){
        return dao.getAll();
    }

    public void delete(int id){
        dao.delete(id);
    }

    public void update(int id){
        dao.update(id);
    }
}
