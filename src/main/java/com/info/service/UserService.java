package com.info.service;

import com.info.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void saveFinacier(User user);

}