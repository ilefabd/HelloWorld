package com.info.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.info.model.DemandeEnCours;
import com.info.model.Response;
import com.info.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void saveFinacier(User user);
	public void savedemande(DemandeEnCours demande ,HttpServletRequest request);
	public void saveTechnical(User user);
	void repondredemande(Response response, DemandeEnCours demande);


}