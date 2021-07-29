package com.mindtree.aem.assets.utility.core.service;

import java.io.BufferedReader;
import java.io.IOException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAMFolderStructure {
	private Node currentNode;
	private static final String CONTENT_DAM_PATH = "/content/dam/test";
	private static final String SLING_ORDERED_FOLDER = "sling:OrderedFolder";
	private static final String FRWRD_SLASH = "/";
	private static final String JCRCONTENT = "jcr:content";
	private static final String NODE_UNSTRUCTURED = "nt:unstructured";
	private static final String JCR_CONTENT = "/jcr:content";
	private static final String JCR_TITILE = "jcr:title";
	private static final Logger LOG = LoggerFactory.getLogger(DAMFolderStructure.class);

	public static void main(String[] args) throws Exception {
		LOG.info("Entry into FolderCreation");
		Session session = null;
		BasicConfigurator.configure();
		try {

			// Create a connection to the CQ repository running on local host
			if (args.length > 1) {
				displayUsage();
				return;
			}

			String ip = "10.150.16.47";
			String port = "4502";
			String userName = "admin";
			String password = "admin";
			String filePath = "D:\\test-dam-structure\\test_DAM_Folder_Structure_TestRun2.csv";

			// Create a Session
			session = SetupUtil.getSession(ip, port, userName, password);
			LOG.info("Session : ", session);
			new DAMFolderStructure().readCSV(session, filePath);

			// Save the session changes and LOG out
			session.save();

		} catch (Exception e) {
			LOG.error("Exception occured while reading the file", e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		LOG.info("Exit from FolderCreation");
	}

	public void readCSV(final Session session, final String csvPath) {
		LOG.info("Entry into readCsv");
		BufferedReader myInput = null;

		String thisLine;
		try {
			myInput = SetupUtil.getBufferedReaderFromFile(csvPath);
		} catch (Exception e) {
			LOG.info("exception-->" + e);
		}

		try {
			while (myInput != null && (thisLine = myInput.readLine()) != null) {
				Node baseNode = (Node) session.getItem(CONTENT_DAM_PATH);
				String[] csvParams = thisLine.trim().split(",");
				for (int k = 0; k < csvParams.length; k++) {
					if (csvParams[k].length() > 0 && csvParams[k] != null) {
						createNode(k, session, csvParams[k], baseNode);
					}
				}

			}
		} catch (IOException ioe) {
			LOG.error("IOEexception in readCSV" + ioe.getMessage() + ioe);
		} catch (Exception e) {
			LOG.error("Eexception in readCSV" + e.getMessage() + e);
		} finally {
			if (myInput != null) {
				try {
					myInput.close();
				} catch (IOException e) {

					LOG.error("IOException in closing bufferedReader" + e.getMessage() + e);
				}
			}
		}
		LOG.info("Entry into readCsv");
	}

	private String createNode(final int k, Session session, final String csvParams, final Node baseNode) {
		LOG.info("Entry into createNode()");

		Node jcrContentNode = null;

		String propertyName = csvParams.trim();
		propertyName = StringUtils.replace(propertyName, "#", ",");
		String propertyMod = propertyName;

		propertyName = StringUtils.replace(propertyName, " - ", " ");
		propertyName = StringUtils.replace(propertyName, "*", "");
		propertyName = StringUtils.replace(propertyName, " ", "-");
		propertyName = StringUtils.replace(propertyName, "-&-", "-");
		propertyName = propertyName.replaceAll("[<>'\"/;%]", "_");
		propertyName = propertyName.replaceAll(",", "");
		if (propertyName.startsWith("[")) {
			propertyName = StringUtils.replace(propertyName, "[", "");
		}
		if (propertyName.endsWith("]")) {
			propertyName = StringUtils.replace(propertyName, "]", "");
		}
		propertyName = propertyName.toLowerCase();

		try {

			if (k == 0) {
				LOG.info("Base folder Search");

				if (!(baseNode.hasNode(propertyName))) {
					baseNode.addNode(propertyName, SLING_ORDERED_FOLDER);

					session.save();
					currentNode = (Node) session.getItem(baseNode.getPath() + FRWRD_SLASH + propertyName);
					currentNode.addNode(JCRCONTENT, NODE_UNSTRUCTURED);

					jcrContentNode = (Node) session.getItem(currentNode.getPath() + JCR_CONTENT);
					jcrContentNode.setProperty(JCR_TITILE, propertyMod);

					session.save();
					LOG.info("Base folder Created " + propertyName);
				} else {
					LOG.info("Base folder Already Exists " + propertyName);
					currentNode = (Node) session.getItem(baseNode.getPath() + FRWRD_SLASH + propertyName);
				}
				return propertyName;
			} else {
				if (!(currentNode.hasNode(propertyName))) {

					LOG.info("Sub folder Search");
					currentNode.addNode(propertyName, SLING_ORDERED_FOLDER);

					currentNode = (Node) session.getItem(currentNode.getPath() + FRWRD_SLASH + propertyName);
					currentNode.addNode(JCRCONTENT, NODE_UNSTRUCTURED);
					jcrContentNode = (Node) session.getItem(currentNode.getPath() + JCR_CONTENT);
					jcrContentNode.setProperty(JCR_TITILE, propertyMod);
					LOG.info("Folder Created " + propertyName);

					session.save();
				} else {
					LOG.info("Folder Already Exists " + propertyName);
					currentNode = (Node) session.getItem(currentNode.getPath() + FRWRD_SLASH + propertyName);
				}

			}

		} catch (PathNotFoundException e) {
			LOG.error("PathNotFoundException" + e);
		} catch (RepositoryException re) {
			LOG.error("RepositoryException" + re);
		}
		LOG.info("Exit from createNode()");
		return propertyName;
	}

	public static void displayUsage() {
		LOG.info("Command Usage - ");

		LOG.info(DAMFolderStructure.class.getName() + " [ip address/hostname] [port] [username] [password] [filename]");

		LOG.info("ip address/hostname\t IP address or host name of the AEM instance");
		LOG.info("port\t\t\t Port of the AEM instance");
		LOG.info("username\t\t Username to connect the AEM instance");
		LOG.info("password\t\t Password to connect the AEM instance");
		LOG.info("filename\t\t CSV File with folder structure defined");
	}

}
