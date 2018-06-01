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
@Table(name="ticket")
public class Ticket  {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(name="subject")
	private String Subjet ;
	@Column(name="title")
	private String title ;
	
	@Column(name="email")
	private String From ;
	@Column(name="response")
	private String Response ;
	@Column(name="opened")
	private Date Opened ;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_ticket")
	private User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public String getSubjet() {
		return Subjet;
	}

	public void setSubjet(String subjet) {
		Subjet = subjet;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public Date getOpened() {
		return Opened;
	}

	public void setOpened(Date opened) {
		Opened = opened;
	}

	
	public Ticket() {
		super();
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public Ticket(String subjet, String title, String from, String response, Date opened, User user) {
		super();
		Subjet = subjet;
		this.title = title;
		From = from;
		Response = response;
		Opened = opened;
		this.user = user;
	}


	public Ticket(String subjet, String title, String from, String response, Date opened) {
		super();
		Subjet = subjet;
		this.title = title;
		From = from;
		Response = response;
		Opened = opened;
	}
	
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", Subjet=" + Subjet + ", From=" + From + ", Response=" + Response + ", Opened="
				+ Opened + ", user=" + user + "]";
	}


	
	
	
	
	
	
}
