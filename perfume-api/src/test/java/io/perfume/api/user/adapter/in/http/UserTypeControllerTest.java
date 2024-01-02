package io.perfume.api.user.adapter.in.http;

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
import io.perfume.api.note.application.port.out.CategoryRepository;
import io.perfume.api.note.domain.Category;
import io.perfume.api.note.domain.CategoryUser;
import io.perfume.api.user.adapter.in.http.dto.AddUserTypeRequestDto;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class UserTypeControllerTest {

  private MockMvc mockMvc;

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ObjectMapper objectMapper;

  private static final String categoryName = "프루티";
  private static final String categoryDesc = "달콤한 과일의 향이 지속되어 생동감과 매력적인 느낌을 줍니다.";
  private static final String categoryTags = "#달달한 #과즙미";
  private static final List<Category> sampleCategories =
      List.of(
          Category.create(categoryName, categoryDesc, categoryTags, 1L, LocalDateTime.now()),
          Category.create(
              categoryName + 1, categoryDesc + 1, categoryTags + 1, 2L, LocalDateTime.now()));

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
  void getTypes() throws Exception {
    // given
    User user = User.generalUserJoin("user", "user@user.com", "useruser", false, false);
    User savedUser = userRepository.save(user).get();

    Category category = categoryRepository.save(sampleCategories.get(0));
    categoryRepository.saveUserTypes(
        savedUser.getId(),
        List.of(CategoryUser.create(savedUser.getId(), category, LocalDateTime.now())));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/user/{id}/types", savedUser.getId())
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
                "get-user-types",
                pathParameters(parameterWithName("id").description("유저 ID(PK)")),
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
  void getTypesFail() throws Exception {
    // given
    Category category = categoryRepository.save(sampleCategories.get(0));
    categoryRepository.saveUserTypes(
        1L, List.of(CategoryUser.create(1L, category, LocalDateTime.now())));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/user/{id}/types", 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(document("get-user-types-failed"));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void addUserType() throws Exception {
    // given
    Category category = categoryRepository.save(sampleCategories.get(0));
    AddUserTypeRequestDto dto = new AddUserTypeRequestDto(List.of(category.getId()));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/user/types")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "add-user-types",
                requestFields(
                    fieldWithPath("categoryIds")
                        .type(JsonFieldType.ARRAY)
                        .description("카테고리 아이디 목록"))));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void addUserTypeFailByBadRequest() throws Exception {
    // given
    Category category = categoryRepository.save(sampleCategories.get(0));
    AddUserTypeRequestDto dto = new AddUserTypeRequestDto(List.of(1L, 2L, 3L, category.getId()));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/user/types")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(document("add-user-types-failed-bad-request"));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void addUserTypeFailByCategoryNotFound() throws Exception {
    // given
    AddUserTypeRequestDto dto = new AddUserTypeRequestDto(List.of(1L));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/user/types")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(document("add-user-types-failed-not-found"));
  }
}
