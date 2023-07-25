package io.perfume.api.note.adapter.out.persistence.note;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.perfume.api.note.adapter.out.persistence.noteUser.NoteUserMapper;
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
    Note createdNote = noteRepository.save(Note.create("test", NoteCategory.BASE, 1L));
    NoteUser noteUser = NoteUser.create(1L, createdNote, now);

    // when
    NoteUser createdNoteUser = noteRepository.save(noteUser);

    // then
    assertThat(createdNoteUser.getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNoteUser.getNote().getId()).isGreaterThanOrEqualTo(0L);
    assertThat(createdNoteUser.getUserId()).isGreaterThanOrEqualTo(1L);
  }
}