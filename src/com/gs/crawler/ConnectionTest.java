/**
 * GS
 */
package com.gs.crawler;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class ConnectionTest {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param url
	 *            the url to be tested
	 * @param time
	 *            the limit of timeout
	 * @return
	 */
	public boolean test(String url, int time) {
		ConnectThread thread = null;
		try {
			thread = new ConnectThread(); // the httpclient thread
			thread.setUrl(url); // set the url which want to be tested
			thread.start();
			long start = System.currentTimeMillis();
			int use = 0;
			while (use < time) {
				long now = System.currentTimeMillis();
				use = (int) (now - start);
				if (!thread.isAlive()) {
					break;
				} // if the connect is good then stop the timer
				if (thread.isError()) {
					if (thread.isAlive()) {
						thread.interrupt();
					}
					logger.error("Not 200! Bad Connection");
					thread.releaseConnection(); // release the connection
					return false;
				}
			}
			if (thread.isAlive()) {// if the connect is timeout stop the thread
									// and give the information
				thread.interrupt();
				logger.error("Interrupted! Bad Connection");
				thread.releaseConnection(); // release the connection
				return false;
			}
			thread.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return true;
	}
}
