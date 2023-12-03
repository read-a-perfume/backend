package io.perfume.api.note.adapter.out.persistence.category;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserJpaRepository;
import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserMapper;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;

@PersistenceAdapter
public class CategoryPersistenceAdapter implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  private final CategoryUserJpaRepository categoryUserJpaRepository;

  private final CategoryMapper categoryMapper;

  private final CategoryUserMapper categoryUserMapper;

  public CategoryPersistenceAdapter(
      CategoryJpaRepository categoryJpaRepository,
      CategoryUserJpaRepository categoryUserJpaRepository,
      CategoryMapper categoryMapper,
      CategoryUserMapper categoryUserMapper) {
    this.categoryJpaRepository = categoryJpaRepository;
    this.categoryUserJpaRepository = categoryUserJpaRepository;
    this.categoryMapper = categoryMapper;
    this.categoryUserMapper = categoryUserMapper;
  }

  @Override
  public Category save(Category category) {
    return categoryMapper.toDomain(categoryJpaRepository.save(categoryMapper.toEntity(category)));
  }

  @Override
  public CategoryUser save(CategoryUser categoryUser) {
    return categoryUserMapper.toDomain(
        categoryUserJpaRepository.save(categoryUserMapper.toEntity(categoryUser)));
  }
}
