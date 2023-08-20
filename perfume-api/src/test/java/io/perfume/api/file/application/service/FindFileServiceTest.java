package io.perfume.api.file.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import io.perfume.api.file.adapter.out.persistence.file.FileQueryPersistenceAdapter;
import io.perfume.api.file.domain.File;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindFileServiceTest {

  @InjectMocks
  private FindFileService findFileService;

  @Mock
  private FileQueryPersistenceAdapter fileQueryPersistenceAdapter;

  @Test
  void findFileById() {
    // given
    Long id = 1L;
    String url = "testUrl";
    LocalDateTime now = LocalDateTime.now();
    File file = File.builder().id(id).url(url).createdAt(now).build();
    given(fileQueryPersistenceAdapter.findOneByFileId(id)).willReturn(Optional.ofNullable(file));
    // when
    Optional<File> optionalFile = findFileService.findFileById(id);

    // then
    assertTrue(optionalFile.isPresent());
    File resultFile = optionalFile.get();
    assertEquals(id, resultFile.getId());
    assertEquals(url, resultFile.getUrl());
    assertEquals(now, resultFile.getCreatedAt());
    assertNull(resultFile.getUpdatedAt());
    assertNull(resultFile.getDeletedAt());
  }
}
