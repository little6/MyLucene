/**
 * GS
 */
package com.gs.downloader;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.DAO;

/**
 * create downloader and schedu the queue
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloaderFactory {
	private Logger logger = Logger.getLogger(this.getClass());
	private DownloaderQueue proceedingqueue = new DownloaderQueue();
	private DownloaderQueue freequeue = new DownloaderQueue();
	private String docpath;
	private String mergefile;
	private int countofinitial = 0; //a counter of initial downloader

	/**
	 * If there are some free downloaders,return a free one.othervise initial a new one and add it to the proceeding queue
	 * @return
	 */
	public Downloader getDownloader() {
		Downloader current;
		if (freequeue.isQueueEmpty()) { //there is a free one
			logger.info("===========A new downloader is initialed!=============");
			current = new Downloader(docpath, mergefile,this);
			countofinitial++;
			proceedingqueue.push(current); //move it to proceeding queue
			logger.info("Total initial : "+countofinitial+"\nFree Downloader : "+freequeue.size()+"\nProceeding Downloader : "+proceedingqueue.size());
		} else {
			logger.info("-----------Use Old Downloader!-------------");
			try {
				current = freequeue.pop(); //use  a free one
			} catch (NoSuchElementException e) {
				current = new Downloader(docpath, mergefile,this);
				countofinitial++;
				proceedingqueue.push(current); //move it to procedding queue
			}
			proceedingqueue.push(current);
			freequeue.remove(current); //remove it from the freequeue
			logger.info("Total initial : "+countofinitial+"\nFree Downloader : "+freequeue.size()+"\nProceeding Downloader : "+proceedingqueue.size());
		}
		return current;
	}

	/**
	 * @param docpath
	 * @param mergefile
	 */
	public DownloaderFactory(String docpath, String mergefile) {
		this.docpath = docpath;
		this.mergefile = mergefile;
	}
	/**
	 * @return
	 */
	public boolean isProceedingQueueEmpty(){
		return proceedingqueue.isQueueEmpty();
	}

	/**
	 * If a downloader is finished its job remove it from proceeding queue add it to the free queue
	 * @param downloader
	 */
	public void releaseDownloader(Downloader downloader) {
		
		logger.info("~~~~~~~~~~~Release~~~~~~~~~~");
		proceedingqueue.remove(downloader);
		freequeue.push(downloader);
	}

	/**
	 * @return
	 */
	public int getFreeDownloaderNum() {
		return freequeue.size();
	}

	/**
	 * @return
	 */
	public int getProceedingNum() {
		return proceedingqueue.size();
	}
	
	

}
