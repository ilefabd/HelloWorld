package com.info.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="statistic")
public class Statistic {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long Stat_id;
	
	@Column(name="statistic_data")
	private String data ;
	
	
	@Column(name="statistic_date")
	private Date statisticDate ;


	public long getStat_id() {
		return Stat_id;
	}


	public void setStat_id(long stat_id) {
		Stat_id = stat_id;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public Date getStatisticDate() {
		return statisticDate;
	}


	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Statistic() {
		super();
	}


	public Statistic( String data, Date statisticDate) {
		super();
		this.data = data;
		this.statisticDate = statisticDate;
	}


	@Override
	public String toString() {
		return "Statistic [Stat_id=" + Stat_id + ", data=" + data + ", statisticDate=" + statisticDate + "]";
	}
	
	
	
	
	
}
