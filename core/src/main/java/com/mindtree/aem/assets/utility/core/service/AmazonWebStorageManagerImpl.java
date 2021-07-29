package com.mindtree.aem.assets.utility.core.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;


public class AmazonWebStorageManagerImpl implements AmazonWebStorageManager {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonWebStorageManagerImpl.class);

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("AemAssetManagerImpl");

	@Override
	public AmazonS3 getAmazonS3Client(String accessKeyId, String secretAccessKeyId, Regions clientRegion) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKeyId);
		return AmazonS3ClientBuilder.standard()
				.withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();
	}

	@Override
	public boolean createFolder(String bucketName, String fileName) {
		return false;
	}

	@Override
	public boolean uploadDirectoryToS3(AmazonS3 s3Client, String bucketName, String fileName, String filePath, boolean includeSubDirectories) {
		try {
			TransferManager transferManager = TransferManagerBuilder.standard()
					.withS3Client(s3Client)
					.build();

			MultipleFileUpload multipleFileUpload = transferManager.uploadDirectory(bucketName, fileName, new File(filePath), includeSubDirectories);
			multipleFileUpload.waitForException();
		} catch (InterruptedException e) {
			LOGGER.info("AmazonWebStorageManager.uploadDirectoryToS3 : "+e.getMessage());
			LOG.info("AmazonWebStorageManager.uploadDirectoryToS3 : {}",e.getMessage());
			// Restore interrupted state...
		    Thread.currentThread().interrupt();
		}
		return false;
	}

	
	
}
