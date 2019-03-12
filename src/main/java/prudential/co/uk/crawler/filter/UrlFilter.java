package prudential.co.uk.crawler.filter;

public interface UrlFilter {

	public static boolean isNullOrEmpty(String url) {
		if (null == url || url.isEmpty())
			return true;
		else
			return false;
	}

	public static String normalize(String domainUrl,String nextUrl) {
		if(nextUrl.startsWith("/"))
			return domainUrl+nextUrl;
		else
			return nextUrl;
	}
	public boolean include(String domainUrl, String url);

}
