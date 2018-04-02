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
public class Ticket implements Serializable {
	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Ticket;
	
	@Column(name="subject")
	private String Subjet ;
	
	
	@Column(name="email")
	private String From ;
	
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

	public long getTicket() {
		return Ticket;
	}

	public void setTicket(long ticket) {
		Ticket = ticket;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Ticket() {
		super();
	}

	public Ticket(String subjet, String from, Date opened) {
		super();
		Subjet = subjet;
		From = from;
		Opened = opened;
	}

	@Override
	public String toString() {
		return "Ticket [Ticket=" + Ticket + ", Subjet=" + Subjet + ", From=" + From + ", Opened=" + Opened + "]";
	}
	
	
	
	
	
	
	
	
	
}
