package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import com.example.braveCoward.model.QDo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // ✅ 스프링 빈 등록
public class DoRepositoryCustomImpl implements DoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DoRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Do> searchByDescriptionContains(Long projectId, String keyword, Pageable pageable) {
        QDo d = QDo.do$; // ✅ 생성된 QDo 사용

        BooleanExpression predicate = d.project.id.eq(projectId)
                .and(d.description.contains(keyword)); // ✅ QueryDSL의 contains() 사용

        List<Do> results = queryFactory.selectFrom(d)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(d.id.desc()) // ✅ 최신순 정렬
                .fetch();

        long total = queryFactory.select(d.count())
                .from(d)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Do> searchByDescriptionStartsWith(Long projectId, String keyword, Pageable pageable) {
        QDo d = QDo.do$;

        BooleanExpression predicate = d.project.id.eq(projectId)
                .and(d.description.startsWith(keyword));

        List<Do> results = queryFactory.selectFrom(d)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(d.id.desc())
                .fetch();

        long total = queryFactory.select(d.count())
                .from(d)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
