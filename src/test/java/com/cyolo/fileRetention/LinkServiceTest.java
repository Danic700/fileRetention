package com.cyolo.fileRetention;

import com.cyolo.fileRetention.model.Link;
import com.cyolo.fileRetention.repository.LinkRepository;
import com.cyolo.fileRetention.service.LinkService;
import com.cyolo.fileRetention.service.LinkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class LinkServiceTest {

    private LinkService linkService;

    @Mock
    private LinkRepository linkRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        linkService = new LinkServiceImpl(linkRepository);
    }

    @Test
    void testCreateLink() {
        Long fileId = 1L;
        String link = "example-link";
        int ttl = 1;

        Link expectedLink = new Link();
        expectedLink.setId(1L);
        expectedLink.setFileId(fileId);
        expectedLink.setLink(link);
        expectedLink.setTtl(ttl);

        when(linkRepository.save(any(Link.class))).thenReturn(expectedLink);

        Optional<Link> result = linkService.createLink(fileId, ttl);

        assertEquals(Optional.of(expectedLink), result);
    }

    @Test
    void testGetLink() {
        String uuid = "0746097e-708b-43f6-b0a3-4aa9a6d7c575";

        Link expectedLink = new Link();
        expectedLink.setId(1L);
        expectedLink.setFileId(1L);
        expectedLink.setLink(uuid);
        expectedLink.setTtl(1);

        when(linkRepository.findByLink(uuid)).thenReturn(Optional.of(expectedLink));

        Optional<Link> result = linkService.getLink(uuid);

        assertEquals(Optional.of(expectedLink), result);
    }

}