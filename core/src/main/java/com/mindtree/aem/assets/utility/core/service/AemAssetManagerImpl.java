package com.mindtree.aem.assets.utility.core.service;

import java.util.Iterator;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.commons.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.agl.impl.Utility;

public class AemAssetManagerImpl implements AemAssetManager{
	private static final Logger LOG = LoggerFactory.getLogger(AemAssetManagerImpl.class);

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("AemAssetManagerImpl");
	
	private static final String JCR_PRIMARY_TYPE = "jcr:primaryType";
	
	private static final String DAM_ASSET = "dam:Asset";
	@Override
	public Session getSession(String repositoryUri, String userName, String password) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			Repository repository = JcrUtils.getRepository(repositoryUri);
			Credentials credentials = new SimpleCredentials(userName, password.toCharArray());
			session = repository.login(credentials);
		} catch (RepositoryException e) {
			LOGGER.info("AemAssetManagerImpl.getSession : "+e.getMessage());
			LOG.info("AemAssetManagerImpl.getSession : {}",e.getMessage());
		}
		return session;
	}

	@Override
	public void downloadAssets(Node damNode, String downloadPath) {
		// TODO Auto-generated method stub
		
		try {
			UtilityImpl utility = new UtilityImpl();
			boolean getSubNodes = true;
			if(damNode.hasProperty(JCR_PRIMARY_TYPE) && damNode.getProperty(JCR_PRIMARY_TYPE).getValue().getString().contains("Folder")) {
				String directoryDestPath = downloadPath + damNode.getPath().split("/content/dam")[1];
				utility.createDirectory(directoryDestPath);
			}
			else if(damNode.hasProperty(JCR_PRIMARY_TYPE) && damNode.getProperty(JCR_PRIMARY_TYPE).getValue().getString().equals(DAM_ASSET)) {
				String fileDestPath = downloadPath + damNode.getPath().split("/content/dam")[1];
				utility.createFile(fileDestPath);
				getSubNodes = false;
			}
			if(damNode.hasNodes() && getSubNodes) {
				Iterator<Node> childNodes = damNode.getNodes();
				while (childNodes.hasNext()) {
					Node childNode = childNodes.next();
					if(childNode.hasProperty(JCR_PRIMARY_TYPE) && (childNode.getProperty(JCR_PRIMARY_TYPE).getValue().getString().contains("Folder") || childNode.getProperty(JCR_PRIMARY_TYPE).getValue().getString().equals(DAM_ASSET))) {
						downloadAssets(childNode, downloadPath);
					}
				}
			}
		} catch (IllegalStateException | RepositoryException e) {
			LOGGER.info("AemAssetManagerImpl.downloadAssets : "+e.getMessage());
			LOG.info("AemAssetManagerImpl.downloadAssets : {}",e.getMessage());
		}
	}
	}

