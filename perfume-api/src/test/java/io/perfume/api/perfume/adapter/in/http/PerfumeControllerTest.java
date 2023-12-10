package io.perfume.api.perfume.adapter.in.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.common.page.CustomPage;
import io.perfume.api.common.page.CustomSlice;
import io.perfume.api.perfume.adapter.in.http.dto.CreatePerfumeRequestDto;
import io.perfume.api.perfume.application.exception.PerfumeNotFoundException;
import io.perfume.api.perfume.application.port.in.CreatePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.FindPerfumeUseCase;
import io.perfume.api.perfume.application.port.in.GetFavoritePerfumesUseCase;
import io.perfume.api.perfume.application.port.in.UserFavoritePerfumeUseCase;
import io.perfume.api.perfume.application.port.in.dto.NotePyramidResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeFavoriteResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNameResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeNoteResult;
import io.perfume.api.perfume.application.port.in.dto.PerfumeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeResult;
import io.perfume.api.perfume.application.port.out.PerfumeFavoriteRepository;
import io.perfume.api.perfume.application.port.out.PerfumeRepository;
import io.perfume.api.perfume.domain.Concentration;
import io.perfume.api.perfume.domain.NotePyramidIds;
import io.perfume.api.perfume.domain.Perfume;
import io.perfume.api.review.application.in.dto.ReviewStatisticResult;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.adapter.out.persistence.user.Sex;
import io.perfume.api.user.application.port.out.UserRepository;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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
class PerfumeControllerTest {

  private MockMvc mockMvc;
  @MockBean private FindPerfumeUseCase findPerfumeUseCase;
  @MockBean private CreatePerfumeUseCase createPerfumeUseCase;
  @MockBean private GetFavoritePerfumesUseCase getFavoritePerfumesUseCase;
  @MockBean private UserFavoritePerfumeUseCase userFavoritePerfumeUseCase;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private PerfumeRepository perfumeRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private PerfumeFavoriteRepository perfumeFavoriteRepository;

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
  @DisplayName("향수를 카테고리, 브랜드 정보, 노트 정보와 함께 조회할 수 있다.")
  void get() throws Exception {
    // given
    NotePyramidResult notePyramidResult =
        new NotePyramidResult(
            List.of(new PerfumeNoteResult(1L, "핑크 페퍼", "testUrl.com/1")),
            List.of(new PerfumeNoteResult(2L, "자스민", "testUrl.com/2")),
            List.of(new PerfumeNoteResult(3L, "머스크", "testUrl.com/3")));

    PerfumeResult perfumeResult =
        PerfumeResult.builder()
            .name("샹스 오 드 빠르펭")
            .story("예측할 수 없는 놀라움을 줍니다.")
            .concentration(Concentration.EAU_DE_PARFUM)
            .perfumeShopUrl(
                "https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/")
            .brandName("CHANEL")
            .categoryName("플로럴")
            .categoryTags("#달달한 #우아한 #꽃")
            .thumbnail("testUrl.com")
            .notePyramidResult(notePyramidResult)
            .build();

    given(findPerfumeUseCase.findPerfumeById(anyLong())).willReturn(perfumeResult);

    // when
    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(perfumeResult.name()))
        .andExpect(jsonPath("$.story").value(perfumeResult.story()))
        .andExpect(jsonPath("$.concentration").value(perfumeResult.concentration().toString()))
        .andExpect(jsonPath("$.perfumeShopUrl").value(perfumeResult.perfumeShopUrl()))
        .andExpect(jsonPath("$.brandName").value(perfumeResult.brandName()))
        .andExpect(jsonPath("$.categoryName").value(perfumeResult.categoryName()))
        .andExpect(jsonPath("$.categoryTags").value(perfumeResult.categoryTags()))
        .andExpect(jsonPath("$.thumbnail").value(perfumeResult.thumbnail()))
        .andExpect(
            jsonPath("$.topNotes[0].name")
                .value(perfumeResult.notePyramidResult().topNotes().get(0).name()))
        .andExpect(
            jsonPath("$.middleNotes[0].name")
                .value(perfumeResult.notePyramidResult().middleNotes().get(0).name()))
        .andExpect(
            jsonPath("$.baseNotes[0].name")
                .value(perfumeResult.notePyramidResult().baseNotes().get(0).name()))
        .andDo(
            document(
                "get-perfume",
                pathParameters(parameterWithName("id").description("향수 ID")),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("story").type(JsonFieldType.STRING).description("향수 스토리"),
                    fieldWithPath("concentration").type(JsonFieldType.STRING).description("향수 농도"),
                    fieldWithPath("perfumeShopUrl")
                        .type(JsonFieldType.STRING)
                        .description("향수 쇼핑몰 URL"),
                    fieldWithPath("brandName").type(JsonFieldType.STRING).description("향수 브랜드 이름"),
                    fieldWithPath("categoryName")
                        .type(JsonFieldType.STRING)
                        .description("향수 카테고리 이름"),
                    fieldWithPath("categoryTags")
                        .type(JsonFieldType.STRING)
                        .description("향수 카테고리 태그"),
                    fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("향수 썸네일 URL"),
                    fieldWithPath("topNotes").type(JsonFieldType.ARRAY).description("향수 탑 노트"),
                    fieldWithPath("topNotes[].id")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 탑 노트 ID"),
                    fieldWithPath("topNotes[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 탑 노트 이름"),
                    fieldWithPath("topNotes[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 탑 노트 썸네일 URL"),
                    fieldWithPath("middleNotes").type(JsonFieldType.ARRAY).description("향수 미들 노트"),
                    fieldWithPath("middleNotes[].id")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 미들 노트 ID"),
                    fieldWithPath("middleNotes[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 미들 노트 이름"),
                    fieldWithPath("middleNotes[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 미들 노트 썸네일 URL"),
                    fieldWithPath("baseNotes").type(JsonFieldType.ARRAY).description("향수 베이스 노트"),
                    fieldWithPath("baseNotes[].id")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 베이스 노트 ID"),
                    fieldWithPath("baseNotes[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 베이스 노트 이름"),
                    fieldWithPath("baseNotes[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 베이스 노트 썸네일 URL"))));
  }

  @Test
  @DisplayName("존재하지 않는 향수를 조회해서 NOT FOUND PERFUME EXCEPTION 을 던진다.")
  void getWithException() throws Exception {
    // given
    given(findPerfumeUseCase.findPerfumeById(anyLong()))
        .willThrow(new PerfumeNotFoundException(1L));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfumes/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createPerfume() throws Exception {
    // given
    CreatePerfumeRequestDto requestDto =
        new CreatePerfumeRequestDto(
            "샹스 오 드 빠르펭",
            "예측할 수 없는 놀라움을 줍니다.",
            Concentration.EAU_DE_PARFUM,
            1L,
            1L,
            1L,
            "https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/",
            List.of(1L, 2L, 3L),
            List.of(4L, 5L, 6L),
            List.of(7L, 8L, 9L));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/perfumes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "create-perfume",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("story").type(JsonFieldType.STRING).description("향수 스토리"),
                    fieldWithPath("concentration")
                        .type(JsonFieldType.STRING)
                        .description("향수 농도 ex) EAU_DE_PERFUME, EAU_DE_TOILETTE"),
                    fieldWithPath("brandId")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 브랜드 ID(PK)"),
                    fieldWithPath("categoryId")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 카테고리 ID(PK)"),
                    fieldWithPath("thumbnailId")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 썸네일 ID(PK)"),
                    fieldWithPath("perfumeShopUrl")
                        .type(JsonFieldType.STRING)
                        .description("향수 쇼핑몰 URL"),
                    fieldWithPath("topNoteIds")
                        .type(JsonFieldType.ARRAY)
                        .description("향수 탑 노트 ID 목록"),
                    fieldWithPath("middleNoteIds")
                        .type(JsonFieldType.ARRAY)
                        .description("향수 미들 노트 ID 목록"),
                    fieldWithPath("baseNoteIds")
                        .type(JsonFieldType.ARRAY)
                        .description("향수 베이스 노트 ID 목록"))));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void favoritePerfume() throws Exception {
    var perfume =
        Perfume.builder()
            .id(1L)
            .name("샹스 오 드 빠르펭")
            .story("예측할 수 없는 놀라움을 줍니다.")
            .concentration(Concentration.EAU_DE_PARFUM)
            .brandId(1L)
            .categoryId(1L)
            .thumbnailId(1L)
            .perfumeShopUrl(
                "https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/")
            .notePyramidIds(
                new NotePyramidIds(List.of(1L, 2L, 3L), List.of(4L, 5L, 6L), List.of(7L, 8L, 9L)))
            .build();

    perfumeRepository.save(perfume);

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/perfumes/favorite/{id}", perfume.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("id", perfume.getId()))))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(
            document(
                "favorite-perfume",
                pathParameters(parameterWithName("id").description("즐겨찾기 향수 ID")),
                requestFields(fieldWithPath("id").type(JsonFieldType.NUMBER).description("향수 ID")),
                responseFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                    fieldWithPath("perfumeId").type(JsonFieldType.NUMBER).description("향수 ID"))));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void getFavoritePerfumes() throws Exception {
    // given
    List<PerfumeFavoriteResult> perfumeFavoriteResult =
        List.of(
            new PerfumeFavoriteResult("딥티크 롬브로단로 오 드 뚜왈렛"),
            new PerfumeFavoriteResult("딥티크 롬브로단로 오 드 퍼퓸"));

    given(getFavoritePerfumesUseCase.getFavoritePerfumes(1L)).willReturn(perfumeFavoriteResult);

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes/favorites")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-favorites",
                responseFields(
                    fieldWithPath("[].perfumeName")
                        .type(JsonFieldType.STRING)
                        .description("향수 이름"))));
  }

  @Test
  void getPerfumesByBrand() throws Exception {
    // given
    List<SimplePerfumeResult> perfumeResults = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      perfumeResults.add(
          new SimplePerfumeResult(
              (long) i,
              "perfume" + i,
              Concentration.EAU_DE_PARFUM,
              "brand" + i,
              "http://thumbnail.url"));
    }
    given(findPerfumeUseCase.findPerfumesByBrand(anyLong(), any(), anyInt()))
        .willReturn(new CustomSlice<>(perfumeResults, true));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes")
                .queryParam("sort", "brand")
                .queryParam("brandId", "1")
                .queryParam("lastPerfumeId", "")
                .queryParam("pageSize", "3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-perfume-by-brand",
                queryParameters(
                    parameterWithName("sort").description("정렬 기준. \"brand\"를 입력한다."),
                    parameterWithName("brandId").description("브랜드 ID"),
                    parameterWithName("lastPerfumeId")
                        .description("앞서 조회한 향수 중 마지막 향수 ID. 첫 조회 시 `lastPerfumeId=` 와 같이 비워둔다."),
                    parameterWithName("pageSize").description("페이지에 존재하는 향수 개수")),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("content[].name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("content[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 썸네일 사진 주소"),
                    fieldWithPath("content[].brandName")
                        .type(JsonFieldType.STRING)
                        .description("향수 브랜드 이름"),
                    fieldWithPath("content[].strength")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도"),
                    fieldWithPath("content[].duration")
                        .type(JsonFieldType.STRING)
                        .description("향수 지속 시간"),
                    fieldWithPath("hasNext")
                        .type(JsonFieldType.BOOLEAN)
                        .description("다음 페이지 존재하는지 여부"))));
  }

  @Test
  @DisplayName("향수의 즐겨찾기 개수 순으로 향수 목록을 조회할 수 있다.")
  void getPerfumesByFavorite() throws Exception {
    // given
    List<SimplePerfumeResult> perfumeResults = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      perfumeResults.add(
          new SimplePerfumeResult(
              (long) i,
              "perfume" + i,
              Concentration.EAU_DE_PARFUM,
              "brand" + i,
              "http://thumbnail.url"));
    }
    given(findPerfumeUseCase.findPerfumesByFavorite(any(), anyInt()))
        .willReturn(new CustomSlice<>(perfumeResults, true));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes")
                .queryParam("sort", "favorite")
                .queryParam("lastPerfumeId", "")
                .queryParam("pageSize", "3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-perfume-by-favorite",
                queryParameters(
                    parameterWithName("sort").description("정렬 기준. \"favorite\"를 입력한다."),
                    parameterWithName("lastPerfumeId")
                        .description("앞서 조회한 향수 중 마지막 향수 ID. 첫 조회 시 `lastPerfumeId=` 와 같이 비워둔다."),
                    parameterWithName("pageSize").description("페이지에 존재하는 향수 개수")),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("content[].name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("content[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 썸네일 사진 주소"),
                    fieldWithPath("content[].brandName")
                        .type(JsonFieldType.STRING)
                        .description("향수 브랜드 이름"),
                    fieldWithPath("content[].strength")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도"),
                    fieldWithPath("content[].duration")
                        .type(JsonFieldType.STRING)
                        .description("향수 지속 시간"),
                    fieldWithPath("hasNext")
                        .type(JsonFieldType.BOOLEAN)
                        .description("다음 페이지 존재하는지 여부"))));
  }

  @Test
  void getPerfumesByCategory() throws Exception {
    // given
    List<SimplePerfumeResult> perfumeResults = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      perfumeResults.add(
          new SimplePerfumeResult(
              (long) i,
              "perfume" + i,
              Concentration.EAU_DE_PARFUM,
              "brand" + i,
              "http://thumbnail.url"));
    }
    given(findPerfumeUseCase.findPerfumesByCategory(anyLong(), any()))
        .willReturn(new CustomPage<>(new PageImpl<>(perfumeResults)));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes/category/{id}", 1)
                .queryParam("page", "0")
                .queryParam("size", "3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "get-perfume-by-category",
                pathParameters(parameterWithName("id").description("카테고리 ID")),
                queryParameters(
                    parameterWithName("page").description("페이지 번호 (0부터 시작)"),
                    parameterWithName("size").description("페이지에 존재하는 향수 개수")),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("content[].name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("content[].thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("향수 썸네일 사진 주소"),
                    fieldWithPath("content[].brandName")
                        .type(JsonFieldType.STRING)
                        .description("향수 브랜드 이름"),
                    fieldWithPath("content[].strength")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도"),
                    fieldWithPath("content[].duration")
                        .type(JsonFieldType.STRING)
                        .description("향수 지속 시간"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("페이지 처음인지 여부"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("페이지 마지막인지 여부"),
                    fieldWithPath("hasNext")
                        .type(JsonFieldType.BOOLEAN)
                        .description("다음 페이지 존재하는지 여부"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수"),
                    fieldWithPath("totalElements")
                        .type(JsonFieldType.NUMBER)
                        .description("총 향수 개수"),
                    fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                    fieldWithPath("size")
                        .type(JsonFieldType.NUMBER)
                        .description("현재 페이지의 향수 개수"))));
  }

  @Test
  void searchPerfumeByQuery() throws Exception {
    // given
    List<PerfumeNameResult> perfumeNameResults =
        List.of(
            new PerfumeNameResult("딥티크 롬브로단로 오 드 뚜왈렛", 1L),
            new PerfumeNameResult("딥티크 롬브로단로 오 드 퍼퓸", 2L));

    given(findPerfumeUseCase.searchPerfumeByQuery("딥티크")).willReturn(perfumeNameResults);
    // when
    // then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes/search")
                .queryParam("query", "딥티크")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            jsonPath("$[0].perfumeNameWithBrand")
                .value(perfumeNameResults.get(0).perfumeNameWithBrand()))
        .andExpect(jsonPath("$[0].perfumeId").value(perfumeNameResults.get(0).perfumeId()))
        .andExpect(
            jsonPath("$[1].perfumeNameWithBrand")
                .value(perfumeNameResults.get(1).perfumeNameWithBrand()))
        .andExpect(jsonPath("$[1].perfumeId").value(perfumeNameResults.get(1).perfumeId()))
        .andDo(
            document(
                "search-perfume",
                queryParameters(
                    parameterWithName("query")
                        .description("검색어 (브랜드 이름 / 향수 이름 / 브랜드 이름 + 향수 이름)만 입력 가능")),
                responseFields(
                    fieldWithPath("[].perfumeNameWithBrand")
                        .type(JsonFieldType.STRING)
                        .description("브랜드 이름 + 향수 이름"),
                    fieldWithPath("[].perfumeId")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 아이디"))));
  }

  @Test
  void getStatistics() throws Exception {
    // given

    Map<Strength, Long> strengthMap = new HashMap<>();
    EnumSet.allOf(Strength.class).forEach(enumValue -> strengthMap.put(enumValue, 25L));
    Map<Duration, Long> durationMap = new HashMap<>();
    EnumSet.allOf(Duration.class).forEach(enumValue -> durationMap.put(enumValue, 25L));
    Map<Season, Long> seasonMap = new HashMap<>();
    EnumSet.allOf(Season.class).forEach(enumValue -> seasonMap.put(enumValue, 25L));
    Map<DayType, Long> dayTypeMap = new HashMap<>();
    EnumSet.allOf(DayType.class).forEach(enumValue -> dayTypeMap.put(enumValue, 25L));
    Map<Sex, Long> sexMap = new HashMap<>();
    EnumSet.allOf(Sex.class).forEach(enumValue -> sexMap.put(enumValue, 25L));

    ReviewStatisticResult result =
        new ReviewStatisticResult(strengthMap, durationMap, seasonMap, dayTypeMap, sexMap);
    given(findPerfumeUseCase.getStatistics(anyLong())).willReturn(result);

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/perfumes/{id}/statistics", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-perfume-statistics",
                pathParameters(parameterWithName("id").description("향수 ID")),
                responseFields(
                    fieldWithPath("strength.LIGHT")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 강도가 약하다고 리뷰한 비율"),
                    fieldWithPath("strength.MODERATE")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 강도가 적당하다고 리뷰한 비율"),
                    fieldWithPath("strength.HEAVY")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 강도가 강하다고 리뷰한 비율"),
                    fieldWithPath("duration.TOO_SHORT")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 지속력이 매우 약하다고 리뷰한 비율"),
                    fieldWithPath("duration.SHORT")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 지속력이 약하다고 리뷰한 비율"),
                    fieldWithPath("duration.MEDIUM")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 지속력이 적당하다고 리뷰한 비율"),
                    fieldWithPath("duration.LONG")
                        .type(JsonFieldType.NUMBER)
                        .description("향수 지속력이 강하다고 리뷰한 비율"),
                    fieldWithPath("season.SPRING")
                        .type(JsonFieldType.NUMBER)
                        .description("봄이 어울리는 계절이라고 리뷰한 비율"),
                    fieldWithPath("season.SUMMER")
                        .type(JsonFieldType.NUMBER)
                        .description("여름이 어울리는 계절이라고 리뷰한 비율"),
                    fieldWithPath("season.FALL")
                        .type(JsonFieldType.NUMBER)
                        .description("가을이 어울리는 계절이라고 리뷰한 비율"),
                    fieldWithPath("season.WINTER")
                        .type(JsonFieldType.NUMBER)
                        .description("겨울이  어울리는 계절이라고 리뷰한 비율"),
                    fieldWithPath("dayType.DAILY")
                        .type(JsonFieldType.NUMBER)
                        .description("매일 뿌리기 좋다고 리뷰한 비율"),
                    fieldWithPath("dayType.SPECIAL")
                        .type(JsonFieldType.NUMBER)
                        .description("특별한 날 뿌리기 좋다고 리뷰한 비율"),
                    fieldWithPath("dayType.REST")
                        .type(JsonFieldType.NUMBER)
                        .description("휴식할 때 뿌리기 좋다고 리뷰한 비율"),
                    fieldWithPath("dayType.TRAVEL")
                        .type(JsonFieldType.NUMBER)
                        .description("여행갈 때 뿌리기 좋다고 리뷰한 비율"),
                    fieldWithPath("sex.MALE").type(JsonFieldType.NUMBER).description("남자가 리뷰한 비율"),
                    fieldWithPath("sex.FEMALE")
                        .type(JsonFieldType.NUMBER)
                        .description("여자가 리뷰한 비율"),
                    fieldWithPath("sex.OTHER")
                        .type(JsonFieldType.NUMBER)
                        .description("성별 지정 안 한 사람이 리뷰한 비율"))));
  }
}
