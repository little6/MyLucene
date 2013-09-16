/**
 * GS
 */
package com.gs.jdom;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.gs.crawler.OS;
import com.gs.crawler.Property;

/**
 * @author GaoShen
 * @packageName com.gs.jdom
 */
public class CrawlerConfReader {
	private Logger logger = Logger.getLogger(this.getClass());
	private Property property;
	/**
	 * @return the property
	 */
	public Property getProperty() {
		return property;
	}
	public CrawlerConfReader(String xmlpath) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File(xmlpath));
			Element rootEl = doc.getRootElement();
			int topN = Integer.valueOf(rootEl.getChildText("topN"));
			int deepth = Integer.valueOf(rootEl.getChildText("deepth"));
			boolean needsIndex = rootEl.getChildText("needsIndex").equals("false")?false:true;
			String path = rootEl.getChildText("path");
			OS os = rootEl.getChildText("os").equals("Windwos")?OS.Windows:OS.Linux;
			String seeds = rootEl.getChildText("seeds");
			property = new Property(deepth,topN,os,path,needsIndex);
			logger.info(property);
			
		} catch (JDOMException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
