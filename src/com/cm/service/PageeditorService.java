package com.cm.service;

import com.cm.dao.PagaeeditorDao;
import com.cm.entity.Pageeditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageeditorService {

    @Autowired
    private PagaeeditorDao dao;

    public void add(Pageeditor editor){
        dao.add(editor);
    }

    public List<Pageeditor> getAll(){
        return dao.getAll();
    }

    public void update(Pageeditor editor){
        dao.update(editor);
    }

    public Pageeditor getByTypeAndName(String type){
        return dao.getByTypeAndName(type);

    }

    public void delete(int id){
        dao.delelte(id);
    }
}
