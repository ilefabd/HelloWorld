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
public class Invoice  {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long invoiceNumber;
	
	@Column(name = "status")
    private  String status ;
	
	@Column(name = "total_amount")
	private double  total_amount ;
	
	@Column(name="issue_date")
	private Date issue_date ;
	@Column(name="detail")
	private String detail;
	@Column(name="Organisation")
	private String Organisation ;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_Invoice")
	  private User user;
	
	
	
	
	
	

	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOrganisation() {
		return Organisation;
	}

	public void setOrganisation(String organisation) {
		Organisation = organisation;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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

	public Long getInvoiceNumber() {
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



	public Invoice() {
		super();
	}

	

	
	

	public Invoice(String status, double total_amount, Date issue_date, String detail,
			String organisation, User user) {
		super();
		this.status = status;
		this.total_amount = total_amount;
		this.issue_date = issue_date;
		this.detail = detail;
		Organisation = organisation;
		this.user = user;
	}

	public Invoice(Long invoiceNumber, String organisation, Date issue_date ,String status, double total_amount) {
		super();
		this.invoiceNumber = invoiceNumber;

		this.Organisation = organisation;
		this.issue_date = issue_date;
		this.status = status;
		this.total_amount = total_amount;
		
	}
	public Invoice(String status, double total_amount) {
		super();
		this.status = status;
		this.total_amount = total_amount;
	}

	@Override
	public String toString() {
		return "Invoice [invoice_number=" + invoiceNumber + ", status=" + status + ", total_amount=" + total_amount
				+ ", issue_date=" + issue_date + "]";
	}
	
	
	
	
}
