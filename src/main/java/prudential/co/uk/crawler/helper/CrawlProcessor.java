package prudential.co.uk.crawler.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import prudential.co.uk.crawler.filter.SameWebSiteOnlyFilter;
import prudential.co.uk.crawler.filter.UrlFilter;

public class CrawlProcessor implements PageProcessor {
	final static Logger logger = Logger.getLogger(CrawlProcessor.class);

	private Document htmlDocument;
	protected List<String> urlsToCrawl;
	protected Set<String> crawledUrls;
	private UrlFilter filter;
	private String baseUrl;

	public CrawlProcessor(String baseUrl) {

		this.urlsToCrawl = new ArrayList<String>();
		this.crawledUrls = new HashSet<String>();
		this.filter = new SameWebSiteOnlyFilter();

		this.baseUrl = baseUrl;
	}

	public void addUrl(String url) {
		logger.info("adding "+url+" for crawling");
		this.urlsToCrawl.add(url);
	}

	public boolean process() throws IOException {
		String normalizedUrl = " ";
		long startTime = System.currentTimeMillis();
		this.addUrl(this.baseUrl);
		do {
			URL url = new URL(this.urlsToCrawl.get(0));
			URLConnection urlConnection = null;
			try {
				urlConnection = url.openConnection();

				try (InputStream input = urlConnection.getInputStream()) {
					String urlVisited = this.urlsToCrawl.remove(0);
					System.out.println("Visited URL " + urlVisited);
					logger.info("Updating crwaled URL set data structure ");
					this.crawledUrls.add(urlVisited);
					logger.info("Parsing HTML document recevied using JSOUP library");
					this.htmlDocument = Jsoup.parse(input, "UTF-8", "");
					logger.info("\n**Visiting** Received web page at " + url);
					Elements linksOnPage = htmlDocument.select("a");
				    logger.info("While crawling for url \n"+urlVisited +"\n found  links \n"+ linksOnPage.text());
					logger.info("Found (" + linksOnPage.size() + ") links");
					for (Element link : linksOnPage) {
						String nextUrl = link.attr("href").trim();
						//verifying  the empty link
						logger.info("verifying the link is empty to avoid malformed Url exception");
						if (!UrlFilter.isNullOrEmpty(nextUrl)) {
							//append the URL starting with "/" to domain prefix
							normalizedUrl = UrlFilter.normalize(baseUrl, nextUrl);
							logger.info("Adding the domain as prefix to URL   starting by /");
							// exclude the url to visit which doesnt belong from domain
							logger.info("Excluding the URL  which doesnt belong from domain by applying sameOrigin filter");

							if (filter.include(this.baseUrl, normalizedUrl)
									&& (!this.crawledUrls.contains(normalizedUrl)))
								this.urlsToCrawl.add(normalizedUrl);
							else
								continue;
						}
					}

				} catch (IOException e) {
					throw new RuntimeException("Error connecting to URL", e);
				}
			} catch (IOException e) {
				throw new RuntimeException("Error connecting to URL", e);
			}
			
		} while (this.urlsToCrawl.size()  >0);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
    //summary of the time taken to visit URL 	
		logger.info("URL's crawled: " + this.crawledUrls.size() + " in " + totalTime + " ms (avg: "
				+ totalTime / this.crawledUrls.size() + ")" + crawledUrls);
		return true;
	}

}
