package io.perfume.api.user.adapter.in.http;

import io.perfume.api.user.adapter.in.http.dto.FindMyDetailInformationResponseDto;
import io.perfume.api.user.application.port.in.FindMyDetailInformationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class MyInformationController {

    private final FindMyDetailInformationUseCase findMyDetailInformationUseCase;

    @GetMapping
    public ResponseEntity<FindMyDetailInformationResponseDto> findMyDetailInformation(@RequestHeader(value = "Authorization") String accessToken) {
        FindMyDetailInformationResponseDto myDetailInformation = findMyDetailInformationUseCase.findMyDetailInformation(accessToken);
        return ResponseEntity.ok(myDetailInformation);
    }
}
