package com.info.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organisation")
public class Organisation implements Serializable {
 
	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long organisation;
 
	@Column(name = "org_name")
	private String orgname;
 
	@Column(name = "address")
	private String address;
 
	@Column(name = "type")
	private String type;

	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private Long phone;
	
	
	
	public Organisation(String orgname, String address, String type, String email, Long phone) {
		super();
		this.orgname = orgname;
		this.address = address;
		this.type = type;
		this.email = email;
		this.phone = phone;
	}
	
	
	public Organisation(Long organisation, String orgname, String address, String type, String email, Long phone) {
		super();
		this.organisation = organisation;
		this.orgname = orgname;
		this.address = address;
		this.type = type;
		this.email = email;
		this.phone = phone;
	}


	public Organisation() {
		super();
	}

	public Long getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Long organisation) {
		this.organisation = organisation;
	}
	public String getOrg_name() {
		return orgname;
	}
	public void setOrg_name(String org_name) {
		this.orgname = org_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}