package com.mindtree.aem.assets.utility.core.service;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mindtree.aem.assets.utility.core.config.S3Configuration;
@Component(service=ConnectToS3.class,immediate=true)
@Designate(ocd=S3Configuration.class)
public class ConnectToS3Impl implements ConnectToS3{
	//AmazonWebStorageManager amazonWebStorageManager = new AmazonWebStorageManagerImpl();
	private String bucketName;
	private String accessKey;
	private String secretKey;
	private String region;
	private String filePath;
	
	@Activate
	protected void activate(final S3Configuration S3Config)
	{
		bucketName=S3Config.getBucketName();
		accessKey=S3Config.getAccessKey();
		secretKey=S3Config.getSecretKey();
		region =S3Config.getRegion();
	//	filePath=S3Config.getFilePath();
	}
	
	
	@Override
	public String connectToS3() {
		// TODO Auto-generated method stub
//		AWSS3 obj1=new AWSS3();
//		AmazonS3 s3Client = obj1.getAmazonS3Client(accessKey, secretKey, region);
//		amazonWebStorageManager.uploadDirectoryToS3(s3Client, bucketName, "fileName", filePath, true);
		return null;
	}
//	public AmazonS3 getAmazonS3Client(String accessKeyId, String secretAccessKeyId, String clientRegion) {
//		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKeyId);
//		return AmazonS3ClientBuilder.standard()
//				.withRegion(clientRegion)
//				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//				.build();
//	}
}
