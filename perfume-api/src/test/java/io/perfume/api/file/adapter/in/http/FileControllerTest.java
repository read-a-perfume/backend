package io.perfume.api.file.adapter.in.http;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class FileControllerTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

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
  @DisplayName("파일을 업로드한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testFileUpload() throws Exception {
    // given
    final MockMultipartFile file =
        new MockMultipartFile("file", "test.txt", "text/plain", "file".getBytes());

    // when & then
    mockMvc
        .perform(
            multipart("/v1/file")
                .file(file)
                .characterEncoding("UTF-8")
                .contentType("multipart/form-data"))
        .andDo(
            document(
                "file-upload",
                requestParts(partWithName("file").description("업로드할 파일")),
                responseFields(
                    fieldWithPath("id").description("파일 식별자"),
                    fieldWithPath("url").description("파일 URL"))));
  }

  @Test
  @DisplayName("파일을 여러 개 업로드한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testUploadMultipleFiles() throws Exception {
    // given
    final MockMultipartFile file1 =
        new MockMultipartFile("files", "test1.txt", "text/plain", "file1".getBytes());
    final MockMultipartFile file2 =
        new MockMultipartFile("files", "test2.txt", "text/plain", "file2".getBytes());

    // when & then
    mockMvc
        .perform(
            multipart("/v1/files")
                .file(file1)
                .file(file2)
                .characterEncoding("UTF-8")
                .contentType("multipart/form-data"))
        .andDo(
            document(
                "files-upload",
                requestParts(partWithName("files").description("업로드할 파일")),
                responseFields(
                    fieldWithPath("[].id").description("파일 식별자"),
                    fieldWithPath("[].url").description("파일 URL"))));
  }
}
