package com.mindtree.aem.assets.utility.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.mindtree.aem.assets.utility.core.service.ConnectToS3;
import com.mindtree.aem.assets.utility.core.service.ConnectToS3Impl;
import com.mindtree.aem.assets.utility.core.service.CreateFolders;
@Component(service=Servlet.class,property= {"sling.servlet.paths="+"/bin/folders"})
//@SlingServletPaths(value = { "/bin/createFolders" })

public class FolderServlet extends SlingSafeMethodsServlet {
	@Reference
	private transient CreateFolders createFolders;
	ConnectToS3 connectToS3=new ConnectToS3Impl();
	protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException,IOException
	{
		PrintWriter out=response.getWriter();
		response.setContentType("text/plain");
//		createFolders.createFolders();
//		createFolders.uploadFolders();
		connectToS3.connectToS3();
		out.print("created");
		
		
	}

}
