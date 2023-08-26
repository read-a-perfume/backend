package io.perfume.api.review.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.review.adapter.in.http.dto.CreateReviewRequestDto;
import io.perfume.api.review.domain.type.SEASON;
import io.perfume.api.review.domain.type.STRENGTH;
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
class ReviewControllerTest {

  private MockMvc mockMvc;

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
  @DisplayName("리뷰를 작성한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testCreateReview() throws Exception {
    var dto = new CreateReviewRequestDto(
        1L,
        SEASON.DAILY,
        STRENGTH.LIGHT,
        100L,
        "",
        "",
        List.of(1L, 2L, 3L, 4L, 5L)
    );

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
                    fieldWithPath("season").type(JsonFieldType.STRING).description("추천 날씨"),
                    fieldWithPath("strength").type(JsonFieldType.STRING).description("향 확산력"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("향 지속력"),
                    fieldWithPath("feeling").type(JsonFieldType.STRING).description("향수 느낌"),
                    fieldWithPath("situation").type(JsonFieldType.STRING).description("추천 상황"),
                    fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰 태그")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 ID")
                )));
  }
}
