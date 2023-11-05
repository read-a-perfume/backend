package io.perfume.api.perfume.adapter.in.http;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeNameResponseDto;
import io.perfume.api.perfume.application.exception.PerfumeNotFoundException;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.NotePyramidResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNoteResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.domain.Concentration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class PerfumeControllerTest {

  private MockMvc mockMvc;
  @MockBean
  private FindPerfumeUseCase findPerfumeUseCase;
  @MockBean
  private CreatePerfumeUseCase createPerfumeUseCase;
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
  @DisplayName("향수를 카테고리, 브랜드 정보, 노트 정보와 함께 조회할 수 있다.")
  void get() throws Exception {
    // given
    NotePyramidResult notePyramidResult = new NotePyramidResult(
        List.of(new PerfumeNoteResult(1L, "핑크 페퍼", "testUrl.com/1")),
        List.of(new PerfumeNoteResult(2L, "자스민", "testUrl.com/2")),
        List.of(new PerfumeNoteResult(3L, "머스크", "testUrl.com/3")));

    PerfumeResult perfumeResult = PerfumeResult.builder()
        .name("샹스 오 드 빠르펭")
        .story("예측할 수 없는 놀라움을 줍니다.")
        .concentration(Concentration.EAU_DE_PERFUME)
        .capacity(100L)
        .price(255000L)
        .perfumeShopUrl("https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/")
        .brandName("CHANEL")
        .categoryName("플로럴")
        .categoryDescription("#달달한 #우아한 #꽃")
        .thumbnailUrl("testUrl.com")
        .notePyramidResult(notePyramidResult)
        .build();

    given(findPerfumeUseCase.findPerfumeById(anyLong())).willReturn(perfumeResult);

    // when
    // then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfumes/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(perfumeResult.name()))
        .andExpect(jsonPath("$.story").value(perfumeResult.story()))
        .andExpect(jsonPath("$.concentration").value(perfumeResult.concentration().toString()))
        .andExpect(jsonPath("$.price").value(perfumeResult.price()))
        .andExpect(jsonPath("$.capacity").value(perfumeResult.capacity()))
        .andExpect(jsonPath("$.perfumeShopUrl").value(perfumeResult.perfumeShopUrl()))
        .andExpect(jsonPath("$.brandName").value(perfumeResult.brandName()))
        .andExpect(jsonPath("$.categoryName").value(perfumeResult.categoryName()))
        .andExpect(jsonPath("$.categoryDescription").value(perfumeResult.categoryDescription()))
        .andExpect(jsonPath("$.thumbnailUrl").value(perfumeResult.thumbnailUrl()))
        .andExpect(jsonPath("$.topNotes[0].name").value(perfumeResult.notePyramidResult().topNotes().get(0).name()))
        .andExpect(jsonPath("$.middleNotes[0].name").value(perfumeResult.notePyramidResult().middleNotes().get(0).name()))
        .andExpect(jsonPath("$.baseNotes[0].name").value(perfumeResult.notePyramidResult().baseNotes().get(0).name()))
        .andDo(
            document("get-perfume",
                pathParameters(
                    parameterWithName("id").description("향수 ID")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("story").type(JsonFieldType.STRING).description("향수 스토리"),
                    fieldWithPath("concentration").type(JsonFieldType.STRING).description("향수 농도"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("향수 가격"),
                    fieldWithPath("capacity").type(JsonFieldType.NUMBER).description("향수 용량"),
                    fieldWithPath("perfumeShopUrl").type(JsonFieldType.STRING).description("향수 쇼핑몰 URL"),
                    fieldWithPath("brandName").type(JsonFieldType.STRING).description("향수 브랜드 이름"),
                    fieldWithPath("categoryName").type(JsonFieldType.STRING).description("향수 카테고리 이름"),
                    fieldWithPath("categoryDescription").type(JsonFieldType.STRING).description("향수 카테고리 설명"),
                    fieldWithPath("thumbnailUrl").type(JsonFieldType.STRING).description("향수 썸네일 URL"),
                    fieldWithPath("topNotes").type(JsonFieldType.ARRAY).description("향수 탑 노트"),
                    fieldWithPath("topNotes[].id").type(JsonFieldType.NUMBER).description("향수 탑 노트 ID"),
                    fieldWithPath("topNotes[].name").type(JsonFieldType.STRING).description("향수 탑 노트 이름"),
                    fieldWithPath("topNotes[].thumbnailUrl").type(JsonFieldType.STRING).description("향수 탑 노트 썸네일 URL"),
                    fieldWithPath("middleNotes").type(JsonFieldType.ARRAY).description("향수 미들 노트"),
                    fieldWithPath("middleNotes[].id").type(JsonFieldType.NUMBER).description("향수 미들 노트 ID"),
                    fieldWithPath("middleNotes[].name").type(JsonFieldType.STRING).description("향수 미들 노트 이름"),
                    fieldWithPath("middleNotes[].thumbnailUrl").type(JsonFieldType.STRING).description("향수 미들 노트 썸네일 URL"),
                    fieldWithPath("baseNotes").type(JsonFieldType.ARRAY).description("향수 베이스 노트"),
                    fieldWithPath("baseNotes[].id").type(JsonFieldType.NUMBER).description("향수 베이스 노트 ID"),
                    fieldWithPath("baseNotes[].name").type(JsonFieldType.STRING).description("향수 베이스 노트 이름"),
                    fieldWithPath("baseNotes[].thumbnailUrl").type(JsonFieldType.STRING).description("향수 베이스 노트 썸네일 URL")
                )));
  }

  @Test
  @DisplayName("존재하지 않는 향수를 조회해서 NOT FOUND PERFUME EXCEPTION 을 던진다.")
  void getWithException() throws Exception {
    //given
    given(findPerfumeUseCase.findPerfumeById(anyLong())).willThrow(new PerfumeNotFoundException(1L));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfumes/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createPerfume() throws Exception {
    // given
    CreatePerfumeRequestDto requestDto = new CreatePerfumeRequestDto("샹스 오 드 빠르펭", "예측할 수 없는 놀라움을 줍니다.", Concentration.EAU_DE_PERFUME, 100L, 255000L,
        1L, 1L, 1L, "https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/",
        List.of(1L, 2L, 3L), List.of(4L, 5L, 6L), List.of(7L, 8L, 9L));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/perfumes")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestDto))
        )
        .andExpect(status().isCreated())
        .andDo(
            document("create-perfume",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("story").type(JsonFieldType.STRING).description("향수 스토리"),
                    fieldWithPath("concentration").type(JsonFieldType.STRING).description("향수 농도 ex) EAU_DE_PERFUME, EAU_DE_TOILETTE"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("향수 가격"),
                    fieldWithPath("capacity").type(JsonFieldType.NUMBER).description("향수 용량"),
                    fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("향수 브랜드 ID(PK)"),
                    fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("향수 카테고리 ID(PK)"),
                    fieldWithPath("thumbnailId").type(JsonFieldType.NUMBER).description("향수 썸네일 ID(PK)"),
                    fieldWithPath("perfumeShopUrl").type(JsonFieldType.STRING).description("향수 쇼핑몰 URL"),
                    fieldWithPath("topNoteIds").type(JsonFieldType.ARRAY).description("향수 탑 노트 ID 목록"),
                    fieldWithPath("middleNoteIds").type(JsonFieldType.ARRAY).description("향수 미들 노트 ID 목록"),
                    fieldWithPath("baseNoteIds").type(JsonFieldType.ARRAY).description("향수 베이스 노트 ID 목록")
                )
            ));
  }

  @Test
  void searchPerfumeByQuery() throws Exception {
    // given
    List<PerfumeNameResult> perfumeNameResults =
        List.of(new PerfumeNameResult("딥티크 롬브로단로 오 드 뚜왈렛", 1L),
            new PerfumeNameResult("딥티크 롬브로단로 오 드 퍼퓸", 2L));

    given(findPerfumeUseCase.searchPerfumeByQuery("딥티크 롬브로")).willReturn(perfumeNameResults);
    // when
    // then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfumes/search")
            .queryParam("query", "딥티크 롬브로")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].perfumeNameWithBrand").value(perfumeNameResults.get(0).perfumeNameWithBrand()))
        .andExpect(jsonPath("$[0].perfumeId").value(perfumeNameResults.get(0).perfumeId()))
        .andExpect(jsonPath("$[1].perfumeNameWithBrand").value(perfumeNameResults.get(1).perfumeNameWithBrand()))
        .andExpect(jsonPath("$[1].perfumeId").value(perfumeNameResults.get(1).perfumeId()))
        .andDo(
            document("get-perfume",
                queryParameters(
                    parameterWithName("query").description("검색어 (브랜드 이름 / 향수 이름 / 브랜드 이름 + 향수 이름)만 입력 가능")
                ),
                responseFields(
                    fieldWithPath("[].perfumeNameWithBrand").type(JsonFieldType.STRING).description("브랜드 이름 + 향수 이름"),
                    fieldWithPath("[].perfumeId").type(JsonFieldType.NUMBER).description("향수 아이디")
                )));
  }
}
