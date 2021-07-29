package com.mindtree.aem.assets.utility.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;
@ObjectClassDefinition(name="S3Configuration" , description="S3 Configuration")
public @interface S3Configuration {

	
	@AttributeDefinition(name="Bucket name", description="Enter your bucket name",type=AttributeType.STRING)
	String getBucketName() default "null";
	
	@AttributeDefinition(name="AccessKey", description="Enter your access key id",type=AttributeType.STRING)
	String getAccessKey() default "";
	
	@AttributeDefinition(name="SecretKey", description="Enter your secret key id",type=AttributeType.STRING)
	String getSecretKey() default "";
	
	@AttributeDefinition(name="ClientRegion", description="Select your region",type=AttributeType.STRING)
	String getRegion() default "AP_South_1";
	

	@AttributeDefinition(name="User name", description="Enter your User name",type=AttributeType.STRING)
	String getUserName() default "admin";
	

	@AttributeDefinition(name="Password", description="Enter your Password",type=AttributeType.STRING)
	String getPassword() default "admin";
	

	@AttributeDefinition(name="DAM path", description="Enter the DAM path",type=AttributeType.STRING)
	String getDAMPath() default "content/dam/";
}