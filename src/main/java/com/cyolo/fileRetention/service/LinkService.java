package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.Link;

import java.util.Optional;

public interface LinkService {

    Optional<Link> createLink(Long fileId, int ttl);

    Optional<Link> getLink(String uuid);
}
