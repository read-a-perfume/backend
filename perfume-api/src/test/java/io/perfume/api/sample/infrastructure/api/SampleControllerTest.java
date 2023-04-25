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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        SampleResult sampleResult = new SampleResult(1L, "sample");
        given(sampleService.getSamples()).willReturn(List.of(sampleResult));

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/samples")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void createSample() throws Exception {
        // given
        CreateSampleRequestDto createSampleRequestDto = new CreateSampleRequestDto("name");
        SampleResult sampleResult = new SampleResult(1L, "sample");
        given(sampleService.createSample("name")).willReturn((sampleResult));

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/samples")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSampleRequestDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void updateSample() throws Exception {
        // given
        UpdateSampleRequestDto updateSampleRequestDto = new UpdateSampleRequestDto("name");
        SampleResult sampleResult = new SampleResult(1L, "sample");
        given(sampleService.updateSample(1L, "name")).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSampleRequestDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    void sample() throws Exception {
        // given
        SampleResult sampleResult = new SampleResult(1L, "sample");
        given(sampleService.getSample(1L)).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void deleteSample() throws Exception {
        // given
        SampleResult sampleResult = new SampleResult(1L, "sample");
        given(sampleService.deleteSample(1L)).willReturn(sampleResult);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/samples/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
