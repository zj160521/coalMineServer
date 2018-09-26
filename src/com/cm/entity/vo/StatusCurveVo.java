package com.cm.entity.vo;

public class StatusCurveVo {
	private String startTime;
	private Double cutValue;
	private Integer feedValue;
	private int debug;
	
//	//有参构造
//	public StatusCurveVo(Builder builder) {
//		startTime = builder.startTime;
//		cutValue = builder.cutValue;
//		feedValue = builder.feedValue;
//		debug = builder.debug;
//	}
//	public StatusCurveVo() {
//		
//	}
	
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Double getCutValue() {
		return cutValue;
	}
	public void setCutValue(Double cutValue) {
		this.cutValue = cutValue;
	}
	public Integer getFeedValue() {
		return feedValue;
	}
	public void setFeedValue(Integer feedValue) {
		this.feedValue = feedValue;
	}
	
//	public static class Builder{//静态内部类，也可以使用外部类，
//        //必要的参数
//        private final String startTime;
//        private final int debug;
//        //可选的参数
//    	private Double cutValue;
//    	private Integer feedValue;
//
//        public Builder(String startTime,int debug){
//            this.startTime = startTime;
//            this.debug = debug;
//        }
//        public Builder cutValue(Double cutValue){
//            this.cutValue=cutValue;
//            return this;//每次返回当前对象，以便后面继续调用更多初始化方法
//        }
//        public Builder feedValue(Integer feedValue){
//            this.feedValue=feedValue;
//            return this;//每次返回当前对象，以便后面继续调用更多初始化方法
//        }
//
//        public StatusCurveVo build(){
//            return new StatusCurveVo(this);
//        }
//    }


}
