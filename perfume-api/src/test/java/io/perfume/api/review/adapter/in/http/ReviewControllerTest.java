package io.perfume.api.review.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewCommentRequestDto;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.application.out.ReviewCommentRepository;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.ReviewComment;
import io.perfume.api.review.domain.type.DayType;
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
class ReviewControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ReviewCommentRepository reviewCommentRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @DisplayName("리뷰를 작성한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReview() throws Exception {
    // given
    var dto = new CreateReviewRequestDto(
        1L,
        DayType.DAILY,
        Strength.LIGHT,
        Season.SPRING,
        100L,
        "",
        "",
        List.of(1L, 2L, 3L, 4L, 5L)
    );

    // when & then
    mockMvc
        .perform(MockMvcRequestBuilders.post("/v1/reviews")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("create-review",
                requestFields(
                    fieldWithPath("perfumeId").type(JsonFieldType.NUMBER).description("향수 ID"),
                    fieldWithPath("season").type(JsonFieldType.STRING).description("추천 계절"),
                    fieldWithPath("dayType").type(JsonFieldType.STRING).description("추천 시간대"),
                    fieldWithPath("strength").type(JsonFieldType.STRING).description("향 확산력"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("향 지속력"),
                    fieldWithPath("feeling").type(JsonFieldType.STRING).description("향수 느낌"),
                    fieldWithPath("shortReview").type(JsonFieldType.STRING).description("추천 상황"),
                    fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰 태그")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID")
                )));
  }

  @Test
  @DisplayName("리뷰를 삭제한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReview() throws Exception {
    // given
    var now = LocalDateTime.now();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        1L,
        Season.SPRING,
        now
    ));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", review.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("delete-review",
                pathParameters(
                    parameterWithName("id").description("삭제한 리뷰 ID")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 리뷰 ID")
                )));
  }

  @Test
  @DisplayName("내 리뷰가 아닌 경우 삭제에 실패한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewForbidden() throws Exception {
    // given
    var now = LocalDateTime.now();
    var userId = 2L;
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        userId,
        Season.SPRING,
        now
    ));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", review.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
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
        .perform(RestDocumentationRequestBuilders.delete("/v1/reviews/{id}", 999)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("리뷰를 조회한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testGetReviews() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        user.getId(),
        Season.SPRING,
        now
    ));
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("page", "1");
    params.add("size", "10");

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/reviews")
            .params(params)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("get-reviews",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("리뷰 ID"),
                    fieldWithPath("[].feeling").type(JsonFieldType.STRING).description("향수 느낌"),
                    fieldWithPath("[].user.id").type(JsonFieldType.NUMBER).description("리뷰 작성자 ID"),
                    fieldWithPath("[].user.username").type(JsonFieldType.STRING)
                        .description("리뷰 작성자 이름"),
                    fieldWithPath("[].user.thumbnailUrl").type(JsonFieldType.STRING)
                        .description("리뷰 작성자 프로필 이미지"),
                    fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("리뷰 태그")
                )
            ));
  }

  @Test
  @DisplayName("리뷰 댓글을 작성한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReviewComment() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        user.getId(),
        Season.SPRING,
        now
    ));
    var dto = new CreateReviewCommentRequestDto("test");

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/reviews/{id}/comments", review.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("create-review-comment",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID")
                )
            ));
  }

  @Test
  @DisplayName("존재하지 않는 리뷰 댓글 작성 요청 시 NOT_FOUND 응답을 반환한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReviewCommentIfNotExists() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var dto = new CreateReviewCommentRequestDto("test");

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/reviews/{id}/comments", 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        )
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("create-review-comment",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                )
            ));
  }

  @Test
  @DisplayName("리뷰 댓글을 삭제한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewComment() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        user.getId(),
        Season.SPRING,
        now
    ));
    var comment = reviewCommentRepository
        .save(ReviewComment.create(review.getId(), user.getId(), "test", now));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/v1/reviews/{id}/comments/{commentId}",
                review.getId(), comment.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("delete-review-comment",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 댓글 ID")
                )
            ));
  }

  @Test
  @DisplayName("존재하지 않는 리뷰 댓글을 삭제하는 경우 NOT_FOUND 응답을 반환한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteReviewCommentIfNotExists() throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        user.getId(),
        Season.SPRING,
        now
    ));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/v1/reviews/{id}/comments/{commentId}",
                review.getId(), 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("delete-review-comment",
                responseFields(
                    fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                )
            ));
  }

  @Test
  @DisplayName("리뷰에 좋아요 표시한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testLikeReview () throws Exception {
    // given
    var now = LocalDateTime.now();
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        1000L,
        DayType.DAILY,
        1L,
        user.getId(),
        Season.SPRING,
        now
    ));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/reviews/{id}/like",
                review.getId(), 1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("like-review",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID")
                )
            ));
  }
}
