package com.mindtree.aem.assets.utility.core.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AWSS3 {
	public AmazonS3 getAmazonS3Client(String accessKeyId, String secretAccessKeyId, String clientRegion) {
	BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKeyId);
	return AmazonS3ClientBuilder.standard()
			.withRegion(clientRegion)
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
			.build();
}
}
