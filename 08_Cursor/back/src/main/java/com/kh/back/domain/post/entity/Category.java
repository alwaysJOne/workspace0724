package com.kh.back.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    TODAY_LEARNED("📝 오늘 배운 것"),
    STUCK("❓ 막힌 것"),
    SHORT_TIP("💡 짧은 팁"),
    SUMMARY_NOTE("📌 정리 노트");

    private final String description;
}

