package com.info.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long invoiceNumber;
	
	@Column(name = "status")
    private  String status ;
	
	@Column(name = "total_amount")
	private double  total_amount ;
	
	@Column(name="issue_date")
	private Date issue_date ;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_Invoice")
	  private User user;
	
	
	
	
	
	public long getInvoice_number() {
		return invoiceNumber;
	}

	public void setInvoice_number(long invoice_number) {
		this.invoiceNumber = invoice_number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Invoice() {
		super();
	}

	public Invoice( String status, double total_amount, Date issue_date) {
		super();
		this.status = status;
		this.total_amount = total_amount;
		this.issue_date = issue_date;
	}

	@Override
	public String toString() {
		return "Invoice [invoice_number=" + invoiceNumber + ", status=" + status + ", total_amount=" + total_amount
				+ ", issue_date=" + issue_date + "]";
	}
	
	
	
	
}
