package io.perfume.api.notification.adapter.port.in;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.notification.application.port.out.notification.NotificationRepository;
import io.perfume.api.notification.domain.Notification;
import io.perfume.api.notification.domain.NotificationType;
import io.perfume.api.user.application.port.out.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Transactional
@SpringBootTest
class NotificationControllerTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private NotificationRepository notificationRepository;

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
  @DisplayName("SSE 연결한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testConnectSSE() throws Exception {
    // given

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/notification/subscribe")
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE)
                .header("Last-Event-Id", ""))
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andDo(
            document(
                "connect-sse",
                requestHeaders(
                    headerWithName(HttpHeaders.ACCEPT).description("요청 헤더 : text/event-stream)"),
                    headerWithName("Last-Event-Id").description("마지막 이벤트 ID"))));
  }

  @Test
  @DisplayName("알림을 확인한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testReadNotification() throws Exception {
    // given
    var now = LocalDateTime.now();
    var notification =
        notificationRepository.save(
            Notification.create("test", null, 1L, NotificationType.REVIEW, now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.patch("/v1/notification/{id}", notification.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andDo(
            document(
                "read-notification",
                pathParameters(parameterWithName("id").description("확인하는 알림 ID"))));
  }

  @Test
  @DisplayName("알림을 삭제한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testDeleteNotification() throws Exception {
    // given
    var now = LocalDateTime.now();
    var notification =
        notificationRepository.save(
            Notification.create("test", null, 1L, NotificationType.REVIEW, now));

    // when & then
    mockMvc
        .perform(
            RestDocumentationRequestBuilders.delete("/v1/notification/{id}", notification.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andDo(
            document(
                "delete-notification",
                pathParameters(parameterWithName("id").description("삭제하는 알림 ID")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 알림 ID"))));
  }

  @Test
  @DisplayName("알림을 조회한다.")
  @WithMockUser(username = "1", roles = "USER")
  void testGetNotifications() throws Exception {
    // given
    var now = LocalDateTime.now();
    final List<Notification> notifications =
        IntStream.range(1, 15)
            .mapToObj(
                (index) ->
                    Notification.create(
                        "새로운 댓글이 달렸습니다.", null, (long) index, NotificationType.REVIEW, now))
            .map(notificationRepository::save)
            .toList();

    final String cursor =
        Base64.encodeBase64String(notifications.get(2).getCreatedAt().toString().getBytes());

    // when & then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/v1/notification")
                .queryParam("pageSize", "5")
                .queryParam("after", cursor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "get-notifications",
                queryParameters(
                    parameterWithName("pageSize").optional().description("조회 데이터 개수"),
                    parameterWithName("after").optional().description("이후 데이터 조회 시 커서"),
                    parameterWithName("before").optional().description("이전 데이터 조회 시 커서")),
                responseFields(
                    fieldWithPath("items").type(JsonFieldType.ARRAY).description("이후 데이터 유무"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("이후 데이터 유무"),
                    fieldWithPath("hasPrev").type(JsonFieldType.BOOLEAN).description("이전 데이터 유무"),
                    fieldWithPath("nextCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("이후 데이터 조회 커서"),
                    fieldWithPath("prevCursor")
                        .type(JsonFieldType.STRING)
                        .optional()
                        .description("이전 데이터 조회 커서"),
                    fieldWithPath("items[].id").type(JsonFieldType.NUMBER).description("알림 ID"),
                    fieldWithPath("items[].content")
                        .type(JsonFieldType.STRING)
                        .description("알림 메시지"),
                    fieldWithPath("items[].notificationType")
                        .type(JsonFieldType.STRING)
                        .description("알림 타입"))));
  }
}
