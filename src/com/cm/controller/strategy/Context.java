package com.cm.controller.strategy;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public Object dofilter(Object o){
        return this.strategy.dofilter(o);
    }
    
    public void addAreaCon(Object ps){
    	this.strategy.addAreaCon(ps);
    }
    
    public void delAreaCon(Object o){
    	this.strategy.delAreaCon(o);
    }
}
