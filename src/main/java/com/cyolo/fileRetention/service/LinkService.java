package com.cyolo.fileRetention.service;

public interface LinkService {

    String createLink(Long fileId, int ttl);

    Long getLink(String uuid);
}
