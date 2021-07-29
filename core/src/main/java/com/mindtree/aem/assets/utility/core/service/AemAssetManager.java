package com.mindtree.aem.assets.utility.core.service;

import javax.jcr.Node;
import javax.jcr.Session;

public interface AemAssetManager {
	public Session getSession(String repositoryUri, String userName, String password);

	public void downloadAssets(Node damNode, String downloadPath);
}
