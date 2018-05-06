package com.info.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.info.ip.Ipv4Range;
import com.info.model.DemandeEnCours;
import com.info.model.Invoice;
import com.info.model.Ipv4range;
import com.info.model.Response;
import com.info.model.User;

public interface UserService {
	public User findUserByEmail(String email);

	public void saveUser(User user);
	public void saveFinacier(User user);
	public void savedemande(DemandeEnCours demande ,HttpServletRequest request);
	public void saveTechnical(User user);
	void repondredemande(Ipv4range ip4range, DemandeEnCours demande, Response response,HttpServletRequest req);
	List<Response> list();
	public Ipv4range findByrange(String iprange);
	public void sendEmail(DemandeEnCours d) throws Exception;
	public void sendEmail2(DemandeEnCours d,Response response);
	public void saveInvoice(Invoice invoice,DemandeEnCours demand) ;

}