/**
 * GS
 */
package com.gs.downloader;

import org.apache.log4j.Logger;

/**
 * this config is build for DownThread
 * 
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownConf {
	private Logger logger = Logger.getLogger(this.getClass());
	public Downloader downloader;

	/**
	 * @param url
	 * @param path
	 * @param count
	 */
	public DownConf(String url,int count, Downloader downloader) {
		this.url = url;
		this.count = count;
		this.downloader = downloader;
	}

	public String url;
	public int count;

}
