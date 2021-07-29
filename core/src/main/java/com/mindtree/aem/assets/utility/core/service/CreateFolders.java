package com.mindtree.aem.assets.utility.core.service;

import java.io.File;
import java.util.Properties;

import javax.jcr.Node;
import javax.jcr.Session;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

public class CreateFolders
{
	Utility utility = new UtilityImpl();
	public void createFolders(){
	
	try {
		
		AemAssetManager aemAssetManager = new AemAssetManagerImpl();
		Properties aemConfigProp = utility.getConfigProperties("aem.config.properties");

		String repositoryUri = aemConfigProp.getProperty("repositoryUri");
		String userName = aemConfigProp.getProperty("userName");
		String password = aemConfigProp.getProperty("password");
		String damPath = aemConfigProp.getProperty("damPath");
		String downloadPath = aemConfigProp.getProperty("downloadPath");

		Session session = aemAssetManager.getSession(repositoryUri, userName, password);
		Node node = session.getRootNode();
		Node damNode = node.getNode(damPath);

		aemAssetManager.downloadAssets(damNode, downloadPath);

	} catch (Exception e) {
		e.printStackTrace();
	}
}
	public void uploadFolders() {
		AmazonWebStorageManager amazonWebStorageManager = new AmazonWebStorageManagerImpl();
		Properties aemConfigProp = utility.getConfigProperties("aws.config.properties");

		String bucketName = aemConfigProp.getProperty("bucketName");
		String accessKeyId = aemConfigProp.getProperty("accessKeyId");
		String secretAccessKeyId = aemConfigProp.getProperty("secretAccessKeyId");
		Regions clientRegion = Regions.AP_SOUTH_1;
		String uploadFilePath = aemConfigProp.getProperty("uploadFilePath");
		String fileName = new File(uploadFilePath).getName();
		boolean includeSubDirectories = true;

		AmazonS3 s3Client = amazonWebStorageManager.getAmazonS3Client(accessKeyId, secretAccessKeyId, clientRegion);
		amazonWebStorageManager.uploadDirectoryToS3(s3Client, bucketName, fileName, uploadFilePath,
				includeSubDirectories);
	}
}

