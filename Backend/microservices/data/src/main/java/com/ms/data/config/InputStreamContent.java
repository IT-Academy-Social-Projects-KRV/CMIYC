package com.ms.data.config;

import com.google.api.client.http.AbstractInputStreamContent;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamContent extends AbstractInputStreamContent {

    private final InputStream targetStream;
    private final long size;

    public InputStreamContent(String type, InputStream targetStream, long size) {
        super(type);
        this.targetStream = targetStream;
        this.size = size;
    }

    @Override
    public InputStream getInputStream() throws IOException {

        return targetStream;
    }

    @Override
    public long getLength() throws IOException {

        return size;
    }

    @Override
    public boolean retrySupported() {

        return false;
    }

}
