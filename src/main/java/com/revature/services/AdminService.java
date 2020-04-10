package com.revature.services;

import java.util.List;

import com.revature.beans.Admin;

/**
 * AdminService is an interface that formats the admin service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface AdminService {
	
	public List<Admin> getAdmins();
	public Admin getAdminById(int id);
	public Admin createAdmin(Admin admin);
	public Admin updateAdmin(Admin admin);
	public String deleteAdminById(int id);
}
