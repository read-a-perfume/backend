package io.perfume.api.note.adapter.out.persistence;

import io.perfume.api.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class NoteUserJpaEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long id;

  @Column(nullable = false)
  private Long noteId;

  @Column(nullable = false)
  private Long userId;
}
