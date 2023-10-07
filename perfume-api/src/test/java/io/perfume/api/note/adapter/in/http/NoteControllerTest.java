package io.perfume.api.note.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.note.adapter.in.http.dto.CreateNoteRequestDto;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class NoteControllerTest {
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private NoteRepository noteRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void findNoteById() throws Exception {
    Note note =
        noteRepository.save(Note.create("white musk", "A light, white floral note with a fresh green scent and aquatic undertones.", 1L));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/notes/{id}", note.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(note.getId()))
        .andExpect(jsonPath("$.name").value(note.getName()))
        .andExpect(jsonPath("$.description").value(note.getDescription()))
        .andExpect(jsonPath("$.thumbnail").value(""))
        .andDo(
            document("get-note-by-id",
                pathParameters(
                    parameterWithName("id").description("노트 ID")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("노트 ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("노트 이름"),
                    fieldWithPath("description").type(JsonFieldType.STRING).description("노트 설명"),
                    fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("노트 이미지")
                )));
  }

  @Test
  void createNote() throws Exception {
    CreateNoteRequestDto dto =
        new CreateNoteRequestDto("white musk", "A light, white floral note with a fresh green scent and aquatic undertones.", 1L);

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/notes")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(dto))
        )
        .andExpect(status().isCreated())
        .andDo(
            document("create-note",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("노트 이름"),
                    fieldWithPath("description").type(JsonFieldType.STRING).description("노트 설명"),
                    fieldWithPath("thumbnailId").type(JsonFieldType.NUMBER).description("노트 썸네일 ID(PK)")
                )
            ));
  }
}
