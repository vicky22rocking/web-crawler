package prudential.co.uk.crawler;

import prudential.co.uk.crawler.app.WebCrawler;

public class WebCrawlerApplication {

	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Please provide a URL as argument to start  application");
			return;
		}

		String url = args[0];
		new WebCrawler(url).crawl();

	}
}
