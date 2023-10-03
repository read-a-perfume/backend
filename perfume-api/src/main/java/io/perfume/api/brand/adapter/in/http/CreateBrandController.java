package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.CreateBrandRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.CreateBrandResponseDto;
import io.perfume.api.brand.application.port.in.CreateBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/brands")
public class CreateBrandController {

    private final CreateBrandUseCase createBrandUseCase;

    public CreateBrandController(CreateBrandUseCase createBrandUseCase) {
        this.createBrandUseCase = createBrandUseCase;
    }

    /*
        TODO : 브랜드를 추가할 수 있는 권한에 따라 @PreAuthorize(), @AuthenticationPrincipal 추가
               Brand
     */
    @PostMapping
    public CreateBrandResponseDto createBrand(@RequestBody CreateBrandRequestDto requestDto) {
        BrandResult response = createBrandUseCase.create(requestDto.toCommand());
        return new CreateBrandResponseDto(response.name());
    }
}
