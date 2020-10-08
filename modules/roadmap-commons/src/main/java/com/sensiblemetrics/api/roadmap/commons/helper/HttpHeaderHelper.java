package com.sensiblemetrics.api.roadmap.commons.helper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Returns a new HttpHeaders from the given one but omits the hop-by-hop headers and
 * specified headers.
 */
public class HttpHeaderHelper {
    /**
     * Default hop headers
     */
    private static final String[] HOP_BY_HOP_HEADERS = {
        "Host",
        "Connection", "Keep-Alive",
        "Proxy-Authenticate", "Proxy-Authorization",
        "TE", "Trailer", "Transfer-Encoding", "Upgrade",
        "X-Application-Context"
    };

    private final Set<String> ignoredHeaders;

    public HttpHeaderHelper() {
        this(Collections.emptySet());
    }

    public HttpHeaderHelper(final Set<String> ignoredHeaders) {
        this.ignoredHeaders = Stream.concat(ignoredHeaders.stream(), Arrays.stream(HOP_BY_HOP_HEADERS))
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }

    private boolean includeHeader(final String header) {
        return !this.ignoredHeaders.contains(header.toLowerCase());
    }

    /**
     * Returns {@link HttpGet} request
     *
     * @param url     initial input {@link String} endpoint url
     * @param headers initial input {@link Map} collection of headers
     * @return http get request
     */
    public static HttpGet createHttpGetRequest(final String url,
                                               final Map<String, String> headers) {
        final HttpGet httpRequest = new HttpGet(url);
        headers.forEach(httpRequest::setHeader);
        return httpRequest;
    }

    /**
     * Returns {@link HttpPost} request
     *
     * @param url     initial input {@link String} endpoint url
     * @param body    initial input {@link HttpEntity} payload
     * @param headers initial input {@link Map} collection of headers
     * @return http post request
     */
    public static HttpPost createHttpPostRequest(final String url,
                                                 final HttpEntity body,
                                                 final Map<String, String> headers) {
        final HttpPost httpRequest = new HttpPost(url);
        httpRequest.setEntity(body);
        headers.forEach(httpRequest::setHeader);
        return httpRequest;
    }

    /**
     * Returns {@link HttpGet} request
     *
     * @param url     initial input {@link String} endpoint url
     * @param body    initial input {@link HttpEntity} payload
     * @param headers initial input {@link Map} collection of headers
     * @return http get request
     */
    public static HttpPut createHttpPutRequest(final String url,
                                               final HttpEntity body,
                                               final Map<String, String> headers) {
        final HttpPut httpRequest = new HttpPut(url);
        httpRequest.setEntity(body);
        headers.forEach(httpRequest::setHeader);
        return httpRequest;
    }

    /**
     * Returns {@link HttpDelete} request
     *
     * @param url     initial input {@link String} endpoint url
     * @param headers initial input {@link Map} collection of headers
     * @return http delete request
     */
    public static HttpDelete createHttpDeleteRequest(final String url,
                                                     final Map<String, String> headers) {
        final HttpDelete httpRequest = new HttpDelete(url);
        headers.forEach(httpRequest::setHeader);
        return httpRequest;
    }
}
