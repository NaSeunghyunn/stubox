package com.nastudy.stubox.service;

import com.nastudy.stubox.controller.form.TagSaveForm;
import com.nastudy.stubox.domain.entity.Tag;
import com.nastudy.stubox.dto.TagDto;
import com.nastudy.stubox.repository.TagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {
    private final TagJpaRepository tagJpaRepository;

    @Transactional(readOnly = true)
    public List<TagDto> findTags(String name) {
        List<Tag> tags = tagJpaRepository.findByNameStartsWith(name);
        List<TagDto> result = tags.stream().map(TagDto::of).collect(Collectors.toList());
        TagDto inputTag = takeOutTag(result, name);
        result.add(0, inputTag);
        return result;
    }

    public Long save(TagSaveForm form){
        Tag tag2 = tagJpaRepository.findByName(form.getName());
        if(tag2 != null){
            return tag2.getId();
        }
        Tag tag = Tag.builder().name(form.getName()).build();
        tagJpaRepository.save(tag);
        return tag.getId();
    }

    private TagDto takeOutTag(List<TagDto> tags, String targetName) {
        for (TagDto tag : tags) {
            if (tag.getName().equals(targetName)) {
                tags.remove(tag);
                return tag;
            }
        }
        return new TagDto(null, targetName);
    }
}
