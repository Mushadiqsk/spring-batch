package com.spring.batch.csv.SpringBatchCSV.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")

public class Order {

	private String anzsic06;
	private String area;
	private long year;
	private long geo_count;
	private long ec_count;
	

	public Order(String anzsic06, String area, long year, long geo_count, long ec_count) {
		super();
		this.anzsic06 = anzsic06;
		this.area = area;
		this.year = year;
		this.geo_count = geo_count;
		this.ec_count = ec_count;
	}
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	@XmlAttribute(name = "anzsic06"
)
	public String getAnzsic06() {
		return anzsic06;
	}
	public void setAnzsic06(String anzsic06) {
		this.anzsic06 = anzsic06;
	}
	@XmlAttribute(name = "area")

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@XmlAttribute(name = "year")

	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	@XmlAttribute(name = "geo_count")

	public long getGeo_count() {
		return geo_count;
	}
	public void setGeo_count(long geo_count) {
		this.geo_count = geo_count;
	}
	
	@XmlAttribute(name = "ec_count")
	public long getEc_count() {
		return ec_count;
	}
	public void setEc_count(long ec_count) {
		this.ec_count = ec_count;
	}
	
  
	
	@Override 
	   public String toString() { 
	      return "anzsic06=" + anzsic06 + ", area=" + area  + ",year=" + year + ",geo_count=" + geo_count + ",ec_count=" + ec_count + ""; 
	   } 
}


