package io.perfume.api.perfume.adapter.in.http;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.perfume.api.perfume.application.exception.PerfumeThemeNotFoundException;
import io.perfume.api.perfume.application.port.in.GetPerfumeThemeUseCase;
import io.perfume.api.perfume.application.port.in.dto.PerfumeThemeResult;
import io.perfume.api.perfume.application.port.in.dto.SimplePerfumeThemeResult;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class PerfumeThemeControllerTest {

  private MockMvc mockMvc;
  @MockBean private GetPerfumeThemeUseCase getPerfumeThemeUseCase;

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
  void getRecentTheme() throws Exception {
    List<SimplePerfumeThemeResult> perfumes = new ArrayList<>();
    for (int i = 1; i < 4; i++) {
      perfumes.add(
          new SimplePerfumeThemeResult(
              (long) i, "No." + i, "이 향수의 유래는 ...", "샤넬", "test-url.com/" + i));
    }
    PerfumeThemeResult perfumeThemeResult =
        new PerfumeThemeResult(
            "겨울에 포근한 분위기를 느끼다",
            "엄동설한에도 따뜻한 느낌을 주는 향수를 바로 만나보세요.",
            "test-url.com/theme/1",
            perfumes);
    given(getPerfumeThemeUseCase.getRecentTheme()).willReturn(perfumeThemeResult);

    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfume-themes/recent"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(perfumeThemeResult.title()))
        .andExpect(jsonPath("$.content").value(perfumeThemeResult.content()))
        .andExpect(jsonPath("$.thumbnail").value(perfumeThemeResult.thumbnail()))
        .andDo(
            document(
                "get-perfume-theme",
                responseFields(
                    fieldWithPath("title").description("테마 제목"),
                    fieldWithPath("content").description("테마 설명"),
                    fieldWithPath("thumbnail").description("테마 썸네일 URL"),
                    fieldWithPath("perfumes[].id").description("테마에 포함된 향수 ID"),
                    fieldWithPath("perfumes[].name").description("테마에 포함된 향수 이름"),
                    fieldWithPath("perfumes[].story").description("테마에 포함된 향수 스토리"),
                    fieldWithPath("perfumes[].thumbnail").description("테마에 포함된 향수 썸네일 URL"),
                    fieldWithPath("perfumes[].brandName").description("테마에 포함된 향수 브랜드 이름"))));
  }

  @Test
  void failedToGetRecentTheme() throws Exception {
    given(getPerfumeThemeUseCase.getRecentTheme()).willThrow(new PerfumeThemeNotFoundException());

    mockMvc
        .perform(RestDocumentationRequestBuilders.get("/v1/perfumes/themes/recent"))
        .andExpect(status().isNotFound())
        .andDo(document("get-perfume-theme-failed"));
  }
}
