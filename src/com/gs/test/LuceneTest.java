package com.gs.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.gs.Lucene.Indexer;
import com.gs.Lucene.Searcher;

public class LuceneTest {

	@Test
	public void test() {
		Indexer indexer = new Indexer();
		indexer.index();
	}

	@Test
	public void testSeracher(){
		Searcher searcher = new Searcher();
		searcher.search();
	}
	public void search(){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("������A:"); 
			String a=br.readLine();
			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
