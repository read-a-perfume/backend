package io.perfume.api.mypage.adapter.port.in.http;

import static org.junit.jupiter.api.Assertions.*;
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
import io.perfume.api.mypage.application.port.in.FollowUserUseCase;
import io.perfume.api.review.application.out.ReviewRepository;
import io.perfume.api.review.domain.Review;
import io.perfume.api.review.domain.type.DayType;
import io.perfume.api.review.domain.type.Duration;
import io.perfume.api.review.domain.type.Season;
import io.perfume.api.review.domain.type.Strength;
import io.perfume.api.user.application.port.out.UserRepository;
import io.perfume.api.user.domain.User;
import java.time.LocalDateTime;
import java.util.Map;
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
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class MypageControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FollowUserUseCase followUserUseCase;

  @Autowired
  private ReviewRepository reviewRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @WithMockUser(username = "100", roles = "USER")
  void testFollowUser() throws Exception {
    // given
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.post("/v1/mypage/{id}/follow", user.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(Map.of("id", user.getId())))
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(
            document("follow-user",
                pathParameters(
                    parameterWithName("id").description("팔로우 할 유저 ID")
                ),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("팔로우 한 유저 ID")
                )));
  }
  @Test
  @WithMockUser(username = "2", roles = "USER")
  void testGetFollowCount() throws Exception {
    // given
    var user = userRepository.save(User.generalUserJoin(
        "test",
        "test@mail.com",
        "test",
        false,
        false
    )).orElseThrow();

    followUserUseCase.followAndUnFollow(2L, user.getId());

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/mypage/follows")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("get-follows",
                responseFields(
                    fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("팔로워 개수"),
                    fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("팔로잉 개수")
                )
            ));
  }

  @Test
  @WithMockUser(username = "1", roles = "USER")
  void test() throws Exception {
    // given
    var now = LocalDateTime.now();
    var review = reviewRepository.save(Review.create(
        "test",
        "test description",
        Strength.LIGHT,
        Duration.LONG,
        DayType.DAILY,
        1L,
        1L,
        Season.SPRING,
        now
    ));

    var review2 = reviewRepository.save(Review.create(
        "test2",
        "test2 description",
        Strength.HEAVY,
        Duration.LONG,
        DayType.DAILY,
        1L,
        1L,
        Season.FALL,
        now
    ));

    // when & then
    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/mypage/reviews")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("get-reviews",
                responseFields(
                    fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("리뷰 개수")
                )
            ));
  }
}