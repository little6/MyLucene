/**
 * 
 */
package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.parserapplications.LinkExtractor;
import org.junit.Test;


import com.gs.crawler.BloomFilter;
import com.gs.crawler.Crawler;
import com.gs.crawler.OS;
import com.gs.crawler.Property;
import com.gs.crawler.URL;
import com.gs.extractor.ContentExtractor;
import com.gs.extractor.MyLinkExtractor;
import com.gs.extractor.TitleExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestGtmlParser {

	@Test
	public void test() {
		String[] s = new String[7];
		s[0] = "http://news.qq.com";
		LinkExtractor.main(s);
	}


	@Test
	public void testString() {
		System.out.println("<a href=\"http://mail.qq.com\" target=\"_blank\">");
		String s = "<a href=\"http://mail.qq.com\" target=\"_blank\">";
		System.out.println(s.substring(0, 13));
	}

	@Test
	public void testTileExt() {
		TitleExtractor t = new TitleExtractor();
		t.extractor("http://news.qq.com/a/20130821/006472.htm");
	}

	@Test
	public void testAll() {
		MyLinkExtractor linkEx = new MyLinkExtractor();
		TitleExtractor titleEx = new TitleExtractor();

	}


	@Test
	public void testFilter() {
		BloomFilter b = new BloomFilter();
		System.out.println(b.exist("http://news.qq.com"));
		// b.exsit("http://news.qq.com");
	}
	
	@Test
	public void tsetNewCrawl(){
		Property p = new Property("http://news.qq.com", 3, 30, OS.Linux, "/home/gaoshen/test",true);
		Crawler c = new Crawler();
		c.crawl(p);
	}
	
	@Test
	public void testCrawlInWindows(){
		Property p = new Property("http://news.qq.com", 3, 30, OS.Windows, "D://Test",true);
		Crawler c = new Crawler();
		c.crawl(p);
	}

}
