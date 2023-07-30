package io.perfume.api.note.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.application.port.out.NoteRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.Note;
import java.time.LocalDateTime;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class FindCategoryControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  void testFindNotes() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    Category category = categoryRepository.save(Category.create("sample", "sample description", 1L, now));

    // when & then
    mockMvc
        .perform(MockMvcRequestBuilders.get("/v1/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("[0].id").value(category.getId()))
        .andExpect(jsonPath("[0].name").value("sample"))
        .andExpect(jsonPath("[0].description").value("sample description"))
        .andExpect(jsonPath("[0].thumbnail").value(""))
        .andDo(
            document("get-categories",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("노트 ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("노트 이름"),
                    fieldWithPath("[].description").type(JsonFieldType.STRING).description("노트 설명"),
                    fieldWithPath("[].thumbnail").type(JsonFieldType.STRING).description("노트 이미지")
                )));
  }

  @Test
  void testFindNoteById() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    Category category = categoryRepository.save(Category.create("sample", "sample description", 1L, now));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/categories/{id}", category.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(category.getId()))
        .andExpect(jsonPath("$.name").value("sample"))
        .andExpect(jsonPath("$.description").value("sample description"))
        .andExpect(jsonPath("$.thumbnail").value(""))
        .andDo(
            document("get-category-by-id",
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
}
