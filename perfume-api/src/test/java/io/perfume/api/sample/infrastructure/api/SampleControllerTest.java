package io.perfume.api.sample.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.sample.application.SampleService;
import io.perfume.api.sample.application.dto.SampleResult;
import io.perfume.api.sample.infrastructure.api.dto.CreateSampleRequestDto;
import io.perfume.api.sample.infrastructure.api.dto.UpdateSampleRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SampleService sampleService;

    @Test
    void samples() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        SampleResult sampleResult = new SampleResult(1L, "sample", now);
        given(sampleService.getSamples()).willReturn(List.of(sampleResult));

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/samples")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].name").value("sample"))
                .andExpect(jsonPath("[0].createdAt").value(now.toString()));
    }

    @Test
    void createSample() throws Exception {
        // given
        CreateSampleRequestDto createSampleRequestDto = new CreateSampleRequestDto("name");
        LocalDateTime now = LocalDateTime.now();
        SampleResult sampleResult = new SampleResult(1L, "sample", now);
        given(sampleService.createSample("name")).willReturn((sampleResult));

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/samples")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSampleRequestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("sample"))
                .andExpect(jsonPath("$.createdAt").value(now.toString()));
    }

    @Test
    void updateSample() throws Exception {
        // given
        UpdateSampleRequestDto updateSampleRequestDto = new UpdateSampleRequestDto("name");
        LocalDateTime now = LocalDateTime.now();
        SampleResult sampleResult = new SampleResult(1L, "sample", now);
        given(sampleService.updateSample(1L, "name")).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSampleRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("sample"))
                .andExpect(jsonPath("$.createdAt").value(now.toString()));
    }

    @Test
    void sample() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        SampleResult sampleResult = new SampleResult(1L, "sample", now);
        given(sampleService.getSample(1L)).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("sample"))
                .andExpect(jsonPath("$.createdAt").value(now.toString()));
    }

    @Test
    void deleteSample() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        SampleResult sampleResult = new SampleResult(1L, "sample", now);
        given(sampleService.deleteSample(1L)).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("sample"))
                .andExpect(jsonPath("$.createdAt").value(now.toString()));
    }
}
