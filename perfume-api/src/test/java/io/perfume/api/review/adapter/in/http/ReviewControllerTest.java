package io.perfume.api.review.adapter.in.http;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewCommentRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.application.in.dto.TagResult;
import io.perfume.api.review.application.in.tag.GetTagUseCase;
import io.perfume.api.review.application.out.comment.ReviewCommentRepository;
import io.perfume.api.review.application.out.review.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
class ReviewControllerTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private ReviewRepository reviewRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ReviewCommentRepository reviewCommentRepository;

  @MockBean private GetTagUseCase getTagUseCase;

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
  @DisplayName("리뷰 작성에 필요한 태그를 조회한다.")
  void getAllTags() throws Exception {
    // given
    given(getTagUseCase.getAll())
        .willReturn(List.of(new TagResult(1L, "싱그러운"), new TagResult(1L, "화려한")));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/reviews/tags"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("싱그러운"))
        .andDo(
            document(
                "get-all-tags",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("태그 ID(PK)"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("태그 내용"))));
  }

  @Test
  @DisplayName("리뷰를 작성한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReview() throws Exception {
    // given
    var dto =
        new CreateReviewRequestDto(
            new CreateReviewRequestDto.Perfume(1L, "test"),
            DayType.DAILY,
            Strength.LIGHT,
            Season.SPRING,
            Duration.TOO_SHORT,
            "",
            "",
            List.of(1L, 2L, 3L, 4L, 5L),
            List.of(1L, 2L, 3L, 4L, 5L));

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/reviews")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "create-review",
                requestFields(
                    fieldWithPath("perfume.id").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("perfume.name").type(JsonFieldType.STRING).description("향수 이름"),
                    fieldWithPath("season").type(JsonFieldType.STRING).description("어울리는 계절"),
                    fieldWithPath("dayType").type(JsonFieldType.STRING).description("어울리는 날"),
                    fieldWithPath("strength").type(JsonFieldType.STRING).description("향수 강도"),
                    fieldWithPath("duration").type(JsonFieldType.STRING).description("향수 지속력"),
                    fieldWithPath("shortReview").type(JsonFieldType.STRING).description("한줄 리뷰"),
                    fieldWithPath("fullReview").type(JsonFieldType.STRING).description("상세 리뷰"),
                    fieldWithPath("keywords").type(JsonFieldType.ARRAY).description("리뷰 태그"),
                    fieldWithPath("thumbnails")
                        .type(JsonFieldType.ARRAY)
                        .description("리뷰 썸네일 이미지")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID"))));
  }

  @Test
  @DisplayName("리뷰를 삭제한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReview() throws Exception {
    // given
    var now = LocalDateTime.now();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                1L,
                Season.SPRING,
                now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "delete-review",
                pathParameters(parameterWithName("id").description("삭제한 리뷰 ID")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 리뷰 ID"))));
  }

  @Test
  @DisplayName("내 리뷰가 아닌 경우 삭제에 실패한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewForbidden() throws Exception {
    // given
    var now = LocalDateTime.now();
    var userId = 2L;
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                userId,
                Season.SPRING,
                now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("존재하지 않는 리뷰를 삭제할 경우 실패한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteNotExistsReview() throws Exception {
    // given
    var now = LocalDateTime.now();
    var userId = 1L;

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", 999)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("리뷰를 조회한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testGetReviews() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    reviewRepository.save(
        Review.create(
            "test",
            "test description",
            Strength.LIGHT,
            Duration.TOO_SHORT,
            DayType.DAILY,
            1L,
            user.getId(),
            Season.SPRING,
            now));
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("page", "1");
    params.add("size", "10");

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/reviews")
                .params(params)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-reviews",
                responseFields(
                    fieldWithPath("content").type(JsonFieldType.ARRAY).description("리뷰 목록"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                    fieldWithPath("totalElements")
                        .type(JsonFieldType.NUMBER)
                        .description("전체 요소 수"),
                    fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("content.[].id").type(JsonFieldType.NUMBER).description("리뷰 ID"),
                    fieldWithPath("content.[].shortReview")
                        .type(JsonFieldType.STRING)
                        .description("한줄 리뷰"),
                    fieldWithPath("content.[].author.id")
                        .type(JsonFieldType.NUMBER)
                        .description("리뷰 작성자 ID"),
                    fieldWithPath("content.[].author.username")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 작성자 이름"),
                    fieldWithPath("content.[].author.thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 작성자 프로필 이미지"),
                    fieldWithPath("content.[].keywords")
                        .type(JsonFieldType.ARRAY)
                        .description("리뷰 태그"),
                    fieldWithPath("content.[].thumbnails")
                        .type(JsonFieldType.ARRAY)
                        .description("리뷰 이미지"),
                    fieldWithPath("content.[].likeCount")
                        .type(JsonFieldType.NUMBER)
                        .description("좋아요 수"),
                    fieldWithPath("content.[].commentCount")
                        .type(JsonFieldType.NUMBER)
                        .description("댓글 수"))));
  }

  @Test
  @DisplayName("리뷰 댓글을 조회한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testGetReviewComments() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));
    reviewCommentRepository.save(ReviewComment.create(review.getId(), user.getId(), "test", now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/reviews/{id}/comments", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-review-comments",
                pathParameters(parameterWithName("id").description("리뷰 ID")),
                queryParameters(
                    parameterWithName("size").description("조회할 댓글 수").optional(),
                    parameterWithName("before").description("이전 댓글 조회").optional(),
                    parameterWithName("after").description("이후 댓글 조회").optional()),
                responseFields(
                    fieldWithPath("items").type(JsonFieldType.ARRAY).description("리뷰 댓글 목록"),
                    fieldWithPath("items[].id").type(JsonFieldType.NUMBER).description("리뷰 댓글 ID"),
                    fieldWithPath("items[].content")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 댓글 내용"),
                    fieldWithPath("items[].author.id")
                        .type(JsonFieldType.NUMBER)
                        .description("리뷰 댓글 작성자 ID"),
                    fieldWithPath("items[].author.name")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 댓글 작성자 이름"),
                    fieldWithPath("items[].author.thumbnail")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 댓글 작성자 프로필 이미지"),
                    fieldWithPath("items[].createdAt")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 댓글 작성 시간"),
                    fieldWithPath("hasNext")
                        .type(JsonFieldType.BOOLEAN)
                        .description("다음 페이지 존재 여부"),
                    fieldWithPath("hasPrev")
                        .type(JsonFieldType.BOOLEAN)
                        .description("이전 페이지 존재 여부"),
                    fieldWithPath("nextCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("다음 페이지 토큰"),
                    fieldWithPath("prevCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("이전 페이지 토큰"))));
  }

  @Test
  @DisplayName("리뷰 댓글을 작성한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReviewComment() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));
    String content = "사실적인 리뷰라서 좋네요.";
    var dto = new CreateReviewCommentRequestDto(content);

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/reviews/{id}/comments", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "create-review-comment",
                pathParameters(parameterWithName("id").description("리뷰 ID")),
                requestFields(
                    fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 댓글 내용")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 댓글 ID"))));
  }

  @Test
  @DisplayName("존재하지 않는 리뷰 댓글 작성 요청 시 NOT_FOUND 응답을 반환한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReviewCommentIfNotExists() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var dto = new CreateReviewCommentRequestDto("test");

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/reviews/{id}/comments", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.message").value("존재하지 않는 리뷰 정보입니다."))
        .andDo(
            document(
                "create-review-comment-failed",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"))));
  }

  @Test
  @DisplayName("리뷰 댓글을 삭제한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewComment() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));
    var comment =
        reviewCommentRepository.save(
            ReviewComment.create(review.getId(), user.getId(), "test", now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete(
                    "/v1/reviews/{id}/comments/{commentId}", review.getId(), comment.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "delete-review-comment",
                pathParameters(
                    parameterWithName("id").description("리뷰 ID"),
                    parameterWithName("commentId").description("삭제할 리뷰 댓글의 ID")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 댓글 ID"))));
  }

  @Test
  @DisplayName("존재하지 않는 리뷰 댓글을 삭제하는 경우 NOT_FOUND 응답을 반환한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewCommentIfNotExists() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete(
                    "/v1/reviews/{id}/comments/{commentId}", review.getId(), 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
        .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.message").value("존재하지 않는 리뷰 댓글입니다."))
        .andDo(
            document(
                "delete-review-comment-failed",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"))));
  }

  @Test
  @DisplayName("리뷰에 좋아요 표시한다.")
  @WithMockUser(username = "2", roles = "USER")
  void testLikeReview() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.post("/v1/reviews/{id}/like", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "like-review",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID"))));
  }

  @Test
  @DisplayName("리뷰의 상세정보를 조회한다.")
  @WithMockUser(username = "2", roles = "USER")
  void testGetReviewDetail() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user =
        userRepository
            .save(User.generalUserJoin("test", "test@mail.com", "test", false, false))
            .orElseThrow();
    var review =
        reviewRepository.save(
            Review.create(
                "test",
                "test description",
                Strength.LIGHT,
                Duration.TOO_SHORT,
                DayType.DAILY,
                1L,
                user.getId(),
                Season.SPRING,
                now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/reviews/{id}", review.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-review-detail",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID"),
                    fieldWithPath("shortReview").type(JsonFieldType.STRING).description("한줄 리뷰"),
                    fieldWithPath("fullReview").type(JsonFieldType.STRING).description("상세 리뷰"),
                    fieldWithPath("dayType").type(JsonFieldType.STRING).description("향수와 어울리는 날"),
                    fieldWithPath("strength").type(JsonFieldType.STRING).description("향수 강도"),
                    fieldWithPath("season").type(JsonFieldType.STRING).description("향수와 어울리는 계절"),
                    fieldWithPath("duration").type(JsonFieldType.STRING).description("향수 지속력"),
                    fieldWithPath("perfumeId").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("리뷰 작성자 ID"),
                    fieldWithPath("author.name")
                        .type(JsonFieldType.STRING)
                        .description("리뷰 작성자 이름"),
                    fieldWithPath("keywords").type(JsonFieldType.ARRAY).description("리뷰 태그"),
                    fieldWithPath("thumbnails").type(JsonFieldType.ARRAY).description("리뷰 이미지"),
                    fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("좋아요 수"),
                    fieldWithPath("commentCount").type(JsonFieldType.NUMBER).description("댓글 수"))));
  }

  @Test
  @WithMockUser(username = "2", roles = "USER")
  void 리뷰_옵션_목록_조회() throws Exception {
    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.get("/v1/reviews/options")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-review-options",
                responseFields(
                    fieldWithPath("strength[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도 이름"),
                    fieldWithPath("strength[].code")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도 코드"),
                    fieldWithPath("season[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도 이름"),
                    fieldWithPath("season[].code")
                        .type(JsonFieldType.STRING)
                        .description("향수 강도 코드"),
                    fieldWithPath("duration[].name")
                        .type(JsonFieldType.STRING)
                        .description("향수 지속력 이름"),
                    fieldWithPath("duration[].code")
                        .type(JsonFieldType.STRING)
                        .description("향수 지속력 코드"),
                    fieldWithPath("dayType[].name")
                        .type(JsonFieldType.STRING)
                        .description("어울리는 상황 이름"),
                    fieldWithPath("dayType[].code")
                        .type(JsonFieldType.STRING)
                        .description("어울리는 상황 코드"))));
  }
}
