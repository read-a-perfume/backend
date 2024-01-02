package io.perfume.api.note.adapter.out.persistence.category;

import io.perfume.api.base.PersistenceAdapter;
import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserJpaEntity;
import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserJpaRepository;
import io.perfume.api.note.adapter.out.persistence.categoryUser.CategoryUserMapper;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  private final CategoryUserJpaRepository categoryUserJpaRepository;

  private final CategoryMapper categoryMapper;

  private final CategoryUserMapper categoryUserMapper;

  @Override
  public Category save(Category category) {
    return categoryMapper.toDomain(categoryJpaRepository.save(categoryMapper.toEntity(category)));
  }

  @Override
  public void deleteUserTypes(Long userId) {
    categoryUserJpaRepository.deleteAllByUserId(userId);
  }

  @Override
  public void saveUserTypes(Long userId, List<CategoryUser> categoryUsers) {
    categoryUserJpaRepository.deleteAllByUserId(userId);
    if (categoryUsers.isEmpty()) {
      return;
    }
    List<CategoryUserJpaEntity> categoryUserJpaEntities =
        categoryUsers.stream().map(categoryUserMapper::toEntity).toList();
    categoryUserJpaRepository.saveAll(categoryUserJpaEntities);
  }
}
