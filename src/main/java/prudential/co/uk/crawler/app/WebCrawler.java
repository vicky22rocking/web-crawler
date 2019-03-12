package prudential.co.uk.crawler.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import prudential.co.uk.crawler.helper.CrawlProcessor;
import prudential.co.uk.crawler.helper.PageProcessor;

public class WebCrawler {

	private PageProcessor pageProcessor;
	private String url;

	public WebCrawler(String url) {
		this.url = url;
		this.pageProcessor = new CrawlProcessor(this.url);
	}

	public void crawl() {

		long startTime = System.currentTimeMillis();

		try {
			pageProcessor.process();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
