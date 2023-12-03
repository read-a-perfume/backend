package io.perfume.api.note.adapter.out.persistence.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.adapter.out.persistence.categoryUser.QCategoryUserJpaEntity;
import io.perfume.api.note.application.port.out.CategoryQueryRepository;
import io.perfume.api.note.domain.Category;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@PersistenceAdapter
public class CategoryQueryPersistenceAdapter implements CategoryQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private final CategoryMapper categoryMapper;

  public CategoryQueryPersistenceAdapter(
      JPAQueryFactory jpaQueryFactory, CategoryMapper categoryMapper) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public List<Category> find() {
    return jpaQueryFactory
        .selectFrom(QCategoryJpaEntity.categoryJpaEntity)
        .where(QCategoryJpaEntity.categoryJpaEntity.deletedAt.isNull())
        .fetch()
        .stream()
        .map(categoryMapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Category> findById(Long id) {
    var category =
        jpaQueryFactory
            .selectFrom(QCategoryJpaEntity.categoryJpaEntity)
            .where(
                QCategoryJpaEntity.categoryJpaEntity
                    .id
                    .eq(id)
                    .and(QCategoryJpaEntity.categoryJpaEntity.deletedAt.isNull()))
            .fetchOne();

    if (Objects.isNull(category)) {
      return Optional.empty();
    }

    return Optional.of(categoryMapper.toDomain(category));
  }

  @Override
  public List<Category> findCategoryUserByUserId(Long id) {
    return jpaQueryFactory
        .select(QCategoryJpaEntity.categoryJpaEntity)
        .from(QCategoryUserJpaEntity.categoryUserJpaEntity)
        .innerJoin(QCategoryJpaEntity.categoryJpaEntity)
        .on(
            QCategoryJpaEntity.categoryJpaEntity
                .id
                .eq(QCategoryUserJpaEntity.categoryUserJpaEntity.category.id)
                .and(QCategoryJpaEntity.categoryJpaEntity.deletedAt.isNull()))
        .where(
            QCategoryUserJpaEntity.categoryUserJpaEntity
                .userId
                .eq(id)
                .and(QCategoryUserJpaEntity.categoryUserJpaEntity.deletedAt.isNull()))
        .fetch()
        .stream()
        .map(categoryMapper::toDomain)
        .toList();
  }
}
