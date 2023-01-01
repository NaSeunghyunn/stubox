package com.nastudy.stubox.service;

import com.nastudy.stubox.dto.PostTagDto;
import com.nastudy.stubox.repository.PostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostTagService {
    private final PostTagRepository postTagRepository;

    @Transactional(readOnly = true)
    public List<PostTagDto> findPostTags(Long postId){
        return postTagRepository.findPostTags(postId);
    }
}
