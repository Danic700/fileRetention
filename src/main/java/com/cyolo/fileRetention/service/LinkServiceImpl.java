package com.cyolo.fileRetention.service;

import com.cyolo.fileRetention.model.Links;
import com.cyolo.fileRetention.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;


    @Override
    public String createLink(Long fileId, int ttl) {

        Links links = new Links(fileId, UUID.randomUUID().toString(), ttl);
        linkRepository.save(links);
        return links.getLink();

    }

    @Override
    public Long getLink(String uuid) {
        Links link = linkRepository.findByLink(uuid);
        return link.getFileId();
    }


}
