package io.perfume.api.note.adapter.out.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import io.perfume.api.configuration.TestQueryDSLConfiguration;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteCategory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({NoteQueryPersistenceAdapter.class, NoteMapper.class, TestQueryDSLConfiguration.class})
@DataJpaTest
@EnableJpaAuditing
class NoteQueryPersistenceAdapterTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private NoteQueryPersistenceAdapter noteQueryRepository;

  @Autowired
  private NoteMapper noteMapper;

  @Test
  @DisplayName("전체 Note 조회")
  void testFind() {
    // given
    Note note = Note.create("name", NoteCategory.BASE, 1L);
    entityManager.persist(noteMapper.toEntity(note));
    entityManager.clear();

    // when
    List<Note> notes = noteQueryRepository.find();

    // then
    assertThat(notes).hasSize(1);
  }

  @Test
  @DisplayName("특정 Id Note 조회")
  void testFindById() {
    // given
    Note note = Note.create("name", NoteCategory.BASE, 1L);
    entityManager.persist(noteMapper.toEntity(note));
    entityManager.clear();

    // when
    Optional<Note> findNote = noteQueryRepository.findById(1L);

    // then
    assertThat(findNote).isPresent();
    assertThat(findNote.get().getName()).isEqualTo("name");
  }
}
