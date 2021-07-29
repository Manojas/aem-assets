package com.mindtree.aem.assets.utility.core.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

public interface AmazonWebStorageManager {

	public AmazonS3 getAmazonS3Client(String accessKeyId, String secretAccessKeyId, Regions clientRegion);

	public boolean createFolder(String bucketName, String fileName);

	public boolean uploadDirectoryToS3(AmazonS3 s3Client, String bucketName, String fileName, String uploadFilePath, boolean includeSubDirectories);


}
