package com.mindtree.aem.assets.utility.core.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;

public class SetupUtil {
	
	/**
	 * This method returns a session based on the Ip and port provided.
	 * @param ip 			IP of CRX instance
	 * @param port			port of CRX instance
	 * @param userName      username for connecting to CRX instance
	 * @param password      password for connecting to CRX instance 
	 * @return              Session  
	 * @throws RepositoryException
	 */

	public static Session getSession(String ip, String port, String userName,
			String password) throws RepositoryException {

		Repository repository = JcrUtils.getRepository("http://" + ip + ":"
				+ port + "/crx/server");

		// Create a Session
		Session session = repository.login(new SimpleCredentials(userName,
				password.toCharArray()));

		return session;

	}
	
	/**
	 * This method returns buffered reader for a Specific CSV file
	 * @param csvPath  the path of the file to read
	 * @return         BufferedReader  
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */

	public static BufferedReader getBufferedReaderFromFile(String csvPath)
			throws UnsupportedEncodingException, FileNotFoundException {
		File fileDir = new File(csvPath);
		return new BufferedReader(new InputStreamReader(new FileInputStream(
				fileDir), "ISO-8859-1"));
	}
	
}
