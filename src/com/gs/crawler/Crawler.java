
package com.gs.crawler;


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
	 * First it will crawl the webpages ,and down them to local.Then it will index them.
	 * @param property some property of the crawler
	 */
	public void crawl(Property property){
		
		this.deepth = property.deepth;
		this.topN = property.topN;
		Queue q = new Queue(property.Bloomfile);
		URL starturl = new URL();
		starturl.level = 1;
		starturl.url = property.url;
		q.enQueue(starturl);
		URL u;
		while(!q.empty()){
			u = q.deQueue();
			if(u.level<deepth){
				List<URL> list = MyLinkExtractor.extractor(u,topN);
				Iterator<URL> iterator = list.iterator();
				while(iterator.hasNext()){
					q.enQueue(iterator.next());
				}
				DownLoader.down(u,property.docfile);
			}else{
				DownLoader.down(u,property.docfile);
			}
		}
		if (!property.needsIndex) {
			Indexer indexer = new Indexer();
			indexer.index(property.Indexfile, property.docfile);
		}
	}
}
