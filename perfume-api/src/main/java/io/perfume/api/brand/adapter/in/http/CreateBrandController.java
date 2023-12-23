package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.CreateBrandRequestDto;
import io.perfume.api.brand.adapter.in.http.dto.CreateBrandResponseDto;
import io.perfume.api.brand.application.port.in.CreateBrandUseCase;
import io.perfume.api.brand.application.port.in.dto.BrandResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/brands")
public class CreateBrandController {

  private final CreateBrandUseCase createBrandUseCase;

  public CreateBrandController(CreateBrandUseCase createBrandUseCase) {
    this.createBrandUseCase = createBrandUseCase;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateBrandResponseDto createBrand(@RequestBody CreateBrandRequestDto requestDto) {
    BrandResult response = createBrandUseCase.create(requestDto.toCommand());
    return new CreateBrandResponseDto(response.name());
  }
}
