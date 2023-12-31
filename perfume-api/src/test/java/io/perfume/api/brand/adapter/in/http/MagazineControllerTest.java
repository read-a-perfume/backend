package io.perfume.api.brand.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineRequestDto;
import io.perfume.api.brand.application.port.out.BrandRepository;
import io.perfume.api.brand.application.port.out.MagazineRepository;
import io.perfume.api.brand.domain.Brand;
import io.perfume.api.brand.domain.Magazine;
import io.perfume.api.user.application.port.out.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.tomcat.util.codec.binary.Base64;
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
class MagazineControllerTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private BrandRepository brandRepository;

  @Autowired private MagazineRepository magazineRepository;

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
  @WithMockUser(username = "1", roles = "ADMIN")
  void testCreateMagazine() throws Exception {
    // given
    var now = LocalDateTime.now();
    var brand =
        brandRepository.save(
            Brand.builder()
                .id(1L)
                .createdAt(now)
                .updatedAt(now)
                .name("test")
                .story("test")
                .brandUrl("test")
                .build());

    var request =
        new CreateMagazineRequestDto(
            "title", "suTitle", "content", 1L, 1L, List.of("태그1", "태그2", "태그3"));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/brand/{id}/magazines", brand.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "create-magazine",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("매거진 제목"),
                    fieldWithPath("subTitle").type(JsonFieldType.STRING).description("매거진 소제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("매거진 내용"),
                    fieldWithPath("coverThumbnailId")
                        .type(JsonFieldType.NUMBER)
                        .description("커버 사진 ID"),
                    fieldWithPath("thumbnailId")
                        .type(JsonFieldType.NUMBER)
                        .description("매거진 썸네일 ID"),
                    fieldWithPath("tags").type(JsonFieldType.ARRAY).description("매거진 태그")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("매거진 ID"))));
  }

  @Test
  void testGetMagazines() throws Exception {
    // given
    var now = LocalDateTime.now();
    var brand =
        Brand.builder()
            .id(1L)
            .createdAt(now)
            .updatedAt(now)
            .name("test")
            .story("test")
            .brandUrl("test")
            .build();
    brandRepository.save(brand);
    final List<Magazine> magazines =
        IntStream.range(0, 15)
            .mapToObj(
                (index) ->
                    Magazine.create(
                        "test",
                        "test",
                        "test",
                        1L,
                        1L,
                        1L,
                        brand.getId(),
                        now.plusSeconds(1000 * index)))
            .map(magazineRepository::save)
            .toList();

    final String cursor =
        Base64.encodeBase64String(magazines.get(2).getCreatedAt().toString().getBytes());

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/brand/{id}/magazines", 1L)
                .queryParam("pageSize", "5")
                .queryParam("after", cursor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-magazines",
                queryParameters(
                    parameterWithName("pageSize").optional().description("조회 데이터 개수"),
                    parameterWithName("after").optional().description("이후 데이터 조회 시 커서"),
                    parameterWithName("before").optional().description("이전 데이터 조회 시 커서")),
                responseFields(
                    fieldWithPath("items").type(JsonFieldType.ARRAY).description("이후 데이터 유무"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("이후 데이터 유무"),
                    fieldWithPath("hasPrev").type(JsonFieldType.BOOLEAN).description("이전 데이터 유무"),
                    fieldWithPath("nextCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("이후 데이터 조회 커서"),
                    fieldWithPath("prevCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("이전 데이터 조회 커서"),
                    fieldWithPath("items[].id").type(JsonFieldType.NUMBER).description("매거진 ID"),
                    fieldWithPath("items[].title").type(JsonFieldType.STRING).description("매거진 제목"),
                    fieldWithPath("items[].content")
                        .type(JsonFieldType.STRING)
                        .description("매거진 내용"),
                    fieldWithPath("items[].coverThumbnailId")
                        .type(JsonFieldType.NUMBER)
                        .description("매거진 커버 썸네일 ID"),
                    fieldWithPath("items[].tags")
                        .type(JsonFieldType.ARRAY)
                        .description("매거진 태그"))));
  }
}
