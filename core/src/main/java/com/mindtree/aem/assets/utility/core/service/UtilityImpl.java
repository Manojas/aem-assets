package com.mindtree.aem.assets.utility.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityImpl implements Utility {

	private static final Logger LOG = LoggerFactory.getLogger(UtilityImpl.class);

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("AemAssetManagerImpl");

	@Override
	public Properties getConfigProperties(String configFileName) {
		Properties properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(configFileName);
			properties.load(inputStream);
		} catch (IOException e) {
			LOG.info("Utility.getConfigProperties : {}",e.getMessage());
		}
		return properties;
	}

	@Override
	public boolean createDirectory(String directoryDestPath) {
		File directory = new File(directoryDestPath);
		return directory.mkdir();
		
	}

	@Override
	public boolean createFile(String fileDestPath) {
		try {
			File file = new File(fileDestPath);
			file.createNewFile();
//			FileOutputStream fileOutputStream = new FileOutputStream(file);
//			fileOutputStream.write(fileContent.getBytes());
//			fileOutputStream.close();
		} catch (IOException e) {
			LOGGER.info("Utility.createFile : "+fileDestPath+"  :  "+e.getMessage());
			LOG.info("Utility.createFile : {}",e.getMessage());
		}
		return false;
	}

}
