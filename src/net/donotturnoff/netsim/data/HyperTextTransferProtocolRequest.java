package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.protocol.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HyperTextTransferProtocolRequest extends Request {

    private final List<String> VALID_VERSIONS = List.of("0.9", "1.0", "1.1", "2.0", "3.0");

    public HyperTextTransferProtocolRequest(HyperTextTransferProtocolMethod method, String path, String version, Map<String, String> httpHeaders, String payload) throws IllegalArgumentException {
        setProtocol(Protocol.HTTP);
        setMethod(method);
        setPath(path);
        setVersion(version);
        setHTTPHeaders(httpHeaders);
        setEmptyLine();
        setPayload(payload);
        validate();
    }

    private void setMethod(HyperTextTransferProtocolMethod method) {
        setHeader("Method", method.name());
    }

    private void setPath(String path) {
        setHeader("Path", path);
    }

    private void setVersion(String version) throws IllegalArgumentException {
        if (VALID_VERSIONS.contains(version)) {
            setHeader("Version", version);
        } else {
            throw new IllegalArgumentException("Invalid HTTP version");
        }
    }

    private void setHTTPHeaders(Map<String, String> httpHeaders) {
        headers.putAll(httpHeaders);
    }

    private void setEmptyLine() {
        setHeader("", "");
    }

    private void validate() throws IllegalArgumentException {
        if (getVersion().equals("1.1") && headers.get("Host") == null) {
            throw new IllegalArgumentException("HTTP/1.1 requires the Host header to be set");
        }
        // TODO: implement more checks
    }

    private HyperTextTransferProtocolMethod getMethod() {
        return HyperTextTransferProtocolMethod.valueOf(getHeader("Method"));
    }

    private String getPath() {
        return getHeader("Path");
    }

    private String getVersion() {
        return getHeader("Version");
    }

    public HashMap<String, String> getHTTPHeaders() {
        HashMap<String, String> httpHeaders = new HashMap<>(headers);
        httpHeaders.remove("Method");
        httpHeaders.remove("Path");
        httpHeaders.remove("Version");
        httpHeaders.remove("");
        return httpHeaders;
    }

    @Override
    public int size() {
        int size = 0;
        size += getMethod().name().length();
        size += getPath().length();
        size += 4 + getVersion().length();
        for (String k: headers.keySet()) {
            String v = getHeader(k);
            size += k.length() + 2 + v.length() + 2;
        }
        size += payload.length();
        return size;
    }

    @Override
    public int[] toOctets() {
        return new int[0];
    }
}