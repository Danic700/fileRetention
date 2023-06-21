package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.Link;
import com.cyolo.fileRetention.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private static final Logger logger = LoggerFactory.getLogger(LinkServiceImpl.class);

    private final LinkRepository linkRepository;


    @Override
    public Optional<Link> createLink(Long fileId, int ttl) {
        Link link = new Link(fileId, UUID.randomUUID().toString(), ttl);
        logger.info("Creating new link in Links table");
        Optional<Link>  newLink = Optional.of(linkRepository.save(link));
        return newLink;
    }

    @Override
    public Optional<Link> getLink(String uuid) {
        logger.info("Retrieving link from Links table");
        return linkRepository.findByLink(uuid);
    }


}
