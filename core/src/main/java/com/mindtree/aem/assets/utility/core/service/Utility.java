package com.mindtree.aem.assets.utility.core.service;

import java.util.Properties;

public interface Utility {

	public Properties getConfigProperties(String configFileName);
	
	public boolean createDirectory(String directoryDestPath);
	
	public boolean createFile(String fileDestPath);
}
