package com.nastudy.stubox.service;

import com.nastudy.stubox.domain.FileUploader;
import com.nastudy.stubox.domain.S3Folder;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final FileUploader fileUploader;

    public Long modName(Long memberId, String memberName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디 입니다."));

        if (member.getName().equals(memberName)) {
            return memberId;
        }
        existsMemberName(memberName);
        member.changeNickName(memberName);
        return memberId;
    }

    public Long modProfileImage(Long memberId, MultipartFile profileImage) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디 입니다."));

        String profile = fileUploader.uploadProfileS3(profileImage, S3Folder.PROFILE);
        member.updateProfile(profile);
        return memberId;
    }

    private void existsMemberName(String memberName) {
        boolean existsByName = memberRepository.existsByName(memberName);
        if (existsByName) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
    }
}
