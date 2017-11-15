package org.test;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * {@link ZuulFilter} Responsible for rewriting the Location header to be the
 * Zuul URL
 *
 * @author Biju Kunjummen
 */
public class LocationRewriteFilter extends ZuulFilter {

	private final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Autowired
	private ZuulProperties zuulProperties;

	@Autowired
	private RouteLocator routeLocator;

	private static final String CUSTOM_HEADER = "CUSTOM_HEADER";

	public LocationRewriteFilter() {
	}

	public LocationRewriteFilter(ZuulProperties zuulProperties, RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
		this.zuulProperties = zuulProperties;
	}

	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return SEND_RESPONSE_FILTER_ORDER - 100;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Pair<String, String> lh = customHeader(ctx);
		return lh != null ? true : false;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Route route = routeLocator.getMatchingRoute(urlPathHelper.getPathWithinApplication(ctx.getRequest()));

		if (route != null) {
			Pair<String, String> lh = customHeader(ctx);
			if (lh != null) {
				String location = lh.second();
				URI originalRequestUri = UriComponentsBuilder
						.fromHttpRequest(new ServletServerHttpRequest(ctx.getRequest())).build().toUri();

				UriComponentsBuilder redirectedUriBuilder = UriComponentsBuilder.fromUriString(location);

				UriComponents redirectedUriComps = redirectedUriBuilder.build();

				String newPath = getRestoredPath(this.zuulProperties, route, redirectedUriComps);

				String modifiedLocation = redirectedUriBuilder.scheme(originalRequestUri.getScheme())
						.host(originalRequestUri.getHost()).port(originalRequestUri.getPort()).replacePath(newPath)
						.build().toUriString();

				lh.setSecond(modifiedLocation);
			}
		}
		return null;
	}

	private String getRestoredPath(ZuulProperties zuulProperties, Route route, UriComponents redirectedUriComps) {
		StringBuilder path = new StringBuilder();
		String redirectedPathWithoutGlobal = downstreamHasGlobalPrefix(zuulProperties)
				? redirectedUriComps.getPath().substring(("/" + zuulProperties.getPrefix()).length())
				: redirectedUriComps.getPath();

		if (downstreamHasGlobalPrefix(zuulProperties)) {
			path.append("/" + zuulProperties.getPrefix());
		} else {
			path.append(zuulHasGlobalPrefix(zuulProperties) ? "/" + zuulProperties.getPrefix() : "");
		}

		path.append(downstreamHasRoutePrefix(route) ? "" : "/" + route.getPrefix()).append(redirectedPathWithoutGlobal);

		return path.toString();
	}

	private boolean downstreamHasGlobalPrefix(ZuulProperties zuulProperties) {
		return (!zuulProperties.isStripPrefix() && StringUtils.hasText(zuulProperties.getPrefix()));
	}

	private boolean zuulHasGlobalPrefix(ZuulProperties zuulProperties) {
		return StringUtils.hasText(zuulProperties.getPrefix());
	}

	private boolean downstreamHasRoutePrefix(Route route) {
		return (StringUtils.hasText(route.getPrefix()));
	}

	private Pair<String, String> customHeader(RequestContext ctx) {
		System.out.println("custom header" + ctx.getResponse().getHeader(CUSTOM_HEADER));
		if (ctx.getZuulResponseHeaders() != null) {
			for (Pair<String, String> pair : ctx.getZuulResponseHeaders()) {
				System.out.println("header " + pair.first());
				if (pair.first().equals(CUSTOM_HEADER)) {
					return pair;
				}
			}
		}
		return null;
	}
}
