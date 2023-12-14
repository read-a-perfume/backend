package io.perfume.api.brand.adapter.in.http;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.perfume.api.brand.application.exception.BrandNotFoundException;
import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class FindBrandControllerTest {
  private MockMvc mockMvc;

  @MockBean private FindBrandUseCase findBrandUseCase;

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
  @DisplayName("브랜드 정보를 조회할 수 있다.")
  void get() throws Exception {
    // given
    Long id = 1L;
    String name = "CHANEL";
    String story =
        "샤넬 향수는 특별한 향과 함께 있는 그대로의 모습을 드러내는 작품입니다. 특별한 시리즈를 통해 샤넬 향수에 관련된 노하우와 전문 기술, 창의성을 끊임없이 추구하는 샤넬의 여정을 확인해 보세요.";
    String thumbnail = "testUrl.com";

    BrandResult brandResult = new BrandResult(id, name, story, thumbnail);

    given(findBrandUseCase.findBrandById(anyLong())).willReturn(brandResult);

    // when
    // then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/brands/{brandId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(brandResult.name()))
        .andExpect(jsonPath("$.story").value(brandResult.story()))
        .andExpect(jsonPath("$.thumbnail").value(brandResult.thumbnail()))
        .andDo(
            document(
                "get-brand",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("브랜드 이름"),
                    fieldWithPath("story").type(JsonFieldType.STRING).description("브랜드 이야기"),
                    fieldWithPath("thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("브랜드 썸네일 이미지"))));
  }

  @Test
  @DisplayName("id에 해당하는 브랜드가 없으면 404 에러를 반환한다.")
  void getFailed() throws Exception {
    // given
    Long id = 100L;
    String name = "CHANEL";
    String story =
        "샤넬 향수는 특별한 향과 함께 있는 그대로의 모습을 드러내는 작품입니다. 특별한 시리즈를 통해 샤넬 향수에 관련된 노하우와 전문 기술, 창의성을 끊임없이 추구하는 샤넬의 여정을 확인해 보세요.";
    String thumbnail = "testUrl.com";

    BrandResult brandResult = new BrandResult(id, name, story, thumbnail);

    given(findBrandUseCase.findBrandById(anyLong())).willThrow(new BrandNotFoundException(id));

    // when
    // then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/brands/{brandId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(document("get-brand-failed"));
  }

  @Test
  @DisplayName("브랜드 정보를 모두 조회할 수 있다.")
  void getAll() throws Exception {
    // given
    List<BrandResult> list = new ArrayList<>();
    list.add(new BrandResult(1L, "샤넬", "브랜드 스토리", "testUrl.com"));
    list.add(new BrandResult(2L, "조말론", "브랜드 스토리", "testUrl.com"));
    given(findBrandUseCase.findAll()).willReturn(list);

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/brands"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(list.get(0).id()))
        .andExpect(jsonPath("$[0].name").value(list.get(0).name()))
        .andExpect(jsonPath("$[0].story").value(list.get(0).story()))
        .andExpect(jsonPath("$[0].thumbnail").value(list.get(0).thumbnail()))
        .andDo(
            document(
                "get-all-brands",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("브랜드 이름"),
                    fieldWithPath("[].story").type(JsonFieldType.STRING).description("브랜드 이야기"),
                    fieldWithPath("[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("브랜드 썸네일 이미지"))));
  }
}
