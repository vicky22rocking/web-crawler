package prudential.co.uk.crawler.filter;

public class SameWebSiteOnlyFilter implements UrlFilter {

	@Override
	public boolean include(String domainUrl, String url) {
		return url.startsWith(domainUrl);

	}

}
