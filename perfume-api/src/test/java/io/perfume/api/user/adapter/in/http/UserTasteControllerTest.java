package io.perfume.api.user.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import io.perfume.api.note.domain.NoteCategory;
import io.perfume.api.note.domain.NoteUser;
import io.perfume.api.user.adapter.in.http.dto.CreateUserTasteRequestDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class UserTasteControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void testGetTastes() throws Exception {
    // given
    Note createNote = noteRepository.save(Note.create("sample", NoteCategory.BASE, 1L));

    LocalDateTime now = LocalDateTime.now();
    noteRepository.save(NoteUser.create(1L, createNote, now));

    // when & then
    mockMvc
        .perform(MockMvcRequestBuilders.get("/v1/user/tastes")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("[0].id").value(createNote.getId()))
        .andExpect(jsonPath("[0].name").value("sample"))
        .andExpect(jsonPath("[0].category").value("BASE"))
        .andDo(
            document("get-user-notes",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("노트 ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("노트 이름"),
                    fieldWithPath("[].category").type(JsonFieldType.STRING).description("노트 종류"),
                    fieldWithPath("[].thumbnail").type(JsonFieldType.STRING).description("노트 이미지")
                )));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void testCreateUserTaste() throws Exception {
    // given
    Note createNote = noteRepository.save(Note.create("sample", NoteCategory.BASE, 1L));
    CreateUserTasteRequestDto dto = new CreateUserTasteRequestDto(createNote.getId());

    // when & then
    mockMvc
        .perform(MockMvcRequestBuilders.post("/v1/user/tastes")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
