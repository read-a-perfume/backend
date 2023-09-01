package io.perfume.api.brand.adapter.in.http;

import io.perfume.api.brand.adapter.in.http.dto.BrandResponse;
import io.perfume.api.brand.application.port.in.FindBrandUseCase;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/brands")
public class FindBrandController {

  private final FindBrandUseCase findBrandUseCase;

  @GetMapping("/{id}")
  public BrandResponse get(@PathVariable Long id) throws FileNotFoundException {
    return BrandResponse.of(findBrandUseCase.findBrandById(id));
  }
}
