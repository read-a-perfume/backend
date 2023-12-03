package io.perfume.api.user.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;
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

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private ObjectMapper objectMapper;

  private static final String categoryName = "프루티";
  private static final String categoryDesc = "달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.";
  private static final String categoryTags = "#달달한 #과즙미";
  private static final Category sampleCategory =
      Category.create(categoryName, categoryDesc, categoryTags, 1L, LocalDateTime.now());

  @BeforeEach
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void testGetTastes() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    Category category = categoryRepository.save(sampleCategory);
    categoryRepository.save(CategoryUser.create(1L, category, LocalDateTime.now()));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/user/tastes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("[0].id").value(category.getId()))
        .andExpect(jsonPath("[0].name").value(categoryName))
        .andExpect(jsonPath("[0].description").value(categoryDesc))
        .andExpect(jsonPath("[0].thumbnail").value(""))
        .andDo(
            document(
                "get-user-tastes",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("[].description")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 설명"),
                    fieldWithPath("[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("카테고리 이미지"))));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void testCreateUserTaste() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    Category category = categoryRepository.save(sampleCategory);
    CreateUserTasteRequestDto dto = new CreateUserTasteRequestDto(category.getId());

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/user/tastes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
