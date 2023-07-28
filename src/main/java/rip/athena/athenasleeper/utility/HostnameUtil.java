package rip.athena.athenasleeper.utility;

import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.servlet.http.HttpServletRequest;

public final class HostnameUtil {
    private HostnameUtil() {}

    private static final String url;

    static {
        HttpServletRequest servletContext = VaadinServletRequest.getCurrent().getHttpServletRequest();

        final String scheme = servletContext.getScheme();
        final String hostname = servletContext.getServerName();
        final int port = servletContext.getServerPort();

        url = scheme + "://" + hostname + ":" + port;
    }

    public static String resolveUrl(final String p_url) {
        return url + p_url;
    }
}
