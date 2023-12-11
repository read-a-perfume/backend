package io.perfume.api.brand.adapter.in.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.brand.adapter.in.http.dto.CreateMagazineRequestDto;
import io.perfume.api.user.application.port.out.UserRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class MagazineControllerTest {

    private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;

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
        var request = new CreateMagazineRequestDto(
                "title",
                "suTitle",
                "content",
                1L,
                1L,
                1L,
                List.of("태그1", "태그2", "태그3")
        );

        // when & then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/v1/magazines")
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
                                        fieldWithPath("coverThumbnailId").type(JsonFieldType.NUMBER).description("커버 사진 ID"),
                                        fieldWithPath("thumbnailId").type(JsonFieldType.NUMBER).description("매거진 썸네일 ID"),
                                        fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("매거진 태그")),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("매거진 ID"))
                        )
                );
    }

}