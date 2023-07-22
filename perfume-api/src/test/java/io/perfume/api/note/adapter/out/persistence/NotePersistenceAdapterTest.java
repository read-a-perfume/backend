package io.perfume.api.note.adapter.out.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteCategory;
import io.perfume.api.note.domain.NoteUser;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({NotePersistenceAdapter.class, NoteMapper.class, NoteUserMapper.class})
@DataJpaTest
@EnableJpaAuditing
class NotePersistenceAdapterTest {

  @Autowired
  private NoteRepository noteRepository;

  @Test
  @DisplayName("노트를 저장한다.")
  void testSave() {
    // given
    Note note = Note.create("test", NoteCategory.BASE, 1L);

    // when
    Note createdNote = noteRepository.save(note);

    // then
    assertThat(createdNote.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNote.getName()).isEqualTo("test");
    assertThat(createdNote.getCategory()).isEqualTo(NoteCategory.BASE);
    assertThat(createdNote.getThumbnailId()).isEqualTo(1L);
  }

  @Test
  void testNoteUserSave() {
    // given
    LocalDateTime now = LocalDateTime.now();
    NoteUser note = NoteUser.create(1L, 2L, now);

    // when
    NoteUser createdNote = noteRepository.save(note);

    // then
    assertThat(createdNote.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNote.getNoteId()).isGreaterThanOrEqualTo(1L);
    assertThat(createdNote.getUserId()).isGreaterThanOrEqualTo(2L);
  }
}
