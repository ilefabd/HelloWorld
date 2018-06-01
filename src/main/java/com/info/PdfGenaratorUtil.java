package com.info;

import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;



@Component
public class PdfGenaratorUtil {
	@Autowired
	private TemplateEngine templateEngine;
	public void createPdf(String templateName, Map map, Long long1) throws DocumentException, IOException {
		Assert.notNull(templateName, "The templateName can not be null");
		Context ctx = new Context();
		if (map != null) {
		     Iterator itMap = map.entrySet().iterator();
		       while (itMap.hasNext()) {
			  Map.Entry pair = (Map.Entry) itMap.next();
		          ctx.setVariable(pair.getKey().toString(), pair.getValue());
		          
			}
		}
		
		String processedHtml = templateEngine.process(templateName, ctx);
		  FileOutputStream os = null;
		// String fileName = UUID.randomUUID().toString();
		    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
		    Date d =new Date();
		    String t =String.valueOf(long1);
		    String f="factureN";
		    String da ="Date";
		    String fileName=f+t+da+mdyFormat.format(d)+".pdf";

	        try {
	       
	        	final File outputFile = new File("D:/PFE/",fileName);
	            System.out.println(outputFile.toString());

	        	  os = new FileOutputStream(outputFile);

	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(processedHtml);
	         
	            renderer.layout();

	            renderer.createPDF(os, true);

	            renderer.finishPDF();
	           
	            System.out.println("PDF created successfully");
	        	}
	        finally {
	            if (os != null) {
	                try {
	            
	                    os.close();

	                } catch (IOException e) { /*ignore*/ }
	            }
	        }
	}
}
