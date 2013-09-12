package com.gs.crawler;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.gs.Lucene.Indexer;
import com.gs.extractor.MyLinkExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Crawler {
	private int deepth = 3;
	private int topN = 2;

	/**
	 * default deepth is 3 and the topN is 2<br>
	 * First it will crawl the webpages ,and down them to local.Then it will
	 * index them.
	 * 
	 * @param property
	 *            some property of the crawler
	 */
	public int crawl(Property property) {

		this.deepth = property.deepth;
		this.topN = property.topN;
		Downloader downloader = new Downloader(property.docfile,property.mergefile);
		ConnectionTest tester = new ConnectionTest(); //It's a tester of url 
		FetchQueue q = new FetchQueue();
		URL starturl = new URL();
		for (String currentURL : property.seeds) { //currentURL is the initial url which is given by the user
			if (!q.isQueueEmpty()) {
				q.empty();
			}
			starturl.level = 1;
			starturl.url = currentURL;
			q.push(starturl);
			URL u;
			while (!q.isQueueEmpty()) { //the cycle of crawl
				u = q.pop();
				if (!tester.test(u.url, 5000)) { //In order to avoid bad links
					continue;
				}
				if (u.level < deepth) {
					List<URL> list = MyLinkExtractor.extractor(u, topN);
					Iterator<URL> iterator = list.iterator();
					while (iterator.hasNext()) {
						if(u.url.length()>220) continue; //In order to avoid data too long exception
						q.push(iterator.next());
					}
					downloader.down(u);
				} else {
					downloader.down(u);
				}
			}
		}
		if (property.needsIndex) { //start index
			Indexer indexer = new Indexer();
			indexer.index(property.Indexfile, property.docfile);
		}
		File docfile = new File(property.docfile);
		for(File f : docfile.listFiles()){ //delete the file of docs
			f.delete();
		}
		docfile.delete();//delete the docfile directory 
		return downloader.count; //total pages of down
	}
}
