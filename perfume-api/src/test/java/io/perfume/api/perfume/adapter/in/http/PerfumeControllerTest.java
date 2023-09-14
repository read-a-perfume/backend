package io.perfume.api.perfume.adapter.in.http;

import io.perfume.api.note.domain.Category;
import io.perfume.api.perfume.adapter.in.http.dto.PerfumeResponse;
import io.perfume.api.perfume.application.port.out.PerfumeQueryRepository;
import io.perfume.api.perfume.domain.Concentration;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
public class PerfumeControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private PerfumeQueryRepository perfumeQueryRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @DisplayName("향수를 카테고리, 브랜드 정보와 함께 조회할 수 있다.")
  void get() throws Exception {
    // given
    PerfumeResponse perfumeResponse = PerfumeResponse.builder()
        .name("샹스 오 드 빠르펭")
        .story("예측할 수 없이 ... 놀라움을 줍니다.")
        .capacity(100)
        .price(255000L)
        .concentration(Concentration.EAU_DE_PERFUME)
        .perfumeShopUrl("https://www.chanel.com/kr/fragrance/p/126520/chance-eau-de-parfum-spray/")
        .categoryName("플로럴")
        .categoryDescription("#달달한 #우아한 #꽃")
        .brandName("CHANEL")
        .thumbnailUrl("akfjsalfdkj")
        .build();

    // TODO: perfumeRepository.save()


    // when
    // then
    mockMvc
        .perform(MockMvcRequestBuilders.get("/v1/perfumes/{perfumeId}", 1L)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value(perfumeResponse.name()))
        .andExpect(jsonPath("$.story").value(perfumeResponse.story()))
        .andExpect(jsonPath("$.capacity").value(perfumeResponse.capacity()))
        .andExpect(jsonPath("$.price").value(perfumeResponse.price()))
        .andExpect(jsonPath("$.concentration").value(perfumeResponse.concentration()))
        .andExpect(jsonPath("$.perfume_shop_url").value(perfumeResponse.perfumeShopUrl()))
        .andExpect(jsonPath("$.category_name").value(perfumeResponse.categoryName()))
        .andExpect(jsonPath("$.category_keyword").value(perfumeResponse.categoryDescription()))
        .andExpect(jsonPath("$.brand_name").value(perfumeResponse.brandName()))
        .andExpect(jsonPath("$.thumbnail_url").value(perfumeResponse.thumbnailUrl()))
        .andDo(
            document("get-perfume",
                requestFields(
                    fieldWithPath("").type(JsonFieldType.STRING).description("")
                ),
                responseFields(
                    fieldWithPath("").type(JsonFieldType.STRING).description("")
                )));
  }

//    @Test
//    @DisplayName("존재하지 않는 향수를 조회해서 NOT FOUND PERFUME EXCEPTION 을 던진다.")
//    void getWithException() {
//        // when & then
//        Assertions.assertThatThrownBy(()-> mockMvc
//                .perform(MockMvcRequestBuilders.get("/api/v1/perfumes/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                ))
//                .isInstanceOf(PerfumeNotFoundException.class);
//    }
//
//
//    @Test
//    @DisplayName("향수에 해당하는 노트 정보를 모두 가져온다.")
//    void getNotesByPerfume() throws Exception {
//
//        // given
//        NotesByPerfumeResponse response;
//
//        // when & then
//        mockMvc
//                .perform(MockMvcRequestBuilders.get("/v1/perfumes/{perfumeId}/notes", 1L)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.top.[0].name").value(response.getTop().get(0).getName()))
//                .andExpect(jsonPath("$.top.[0].thumbnail_url").value(response.getTop().get(0).getThumbnailUrl()))
//                .andExpect(jsonPath("$.middle.[0].name").value(response.getMiddle().get(0).getName()))
//                .andExpect(jsonPath("$.middle.[0].thumbnail_url").value(response.getMiddle().get(0).getThumbnailUrl()))
//                .andExpect(jsonPath("$.base.[0].name").value(response.getBase().get(0).getName()))
//                .andExpect(jsonPath("$.base.[0].thumbnail_url").value(response.getBase().get(0).getThumbnailUrl()));
//    }

}
