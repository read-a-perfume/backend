package io.perfume.api.note.adapter.in.http;

import io.perfume.api.note.adapter.in.http.dto.CategoryResponse;
import io.perfume.api.note.application.port.in.FindCategoryUseCase;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categories")
@Validated
public class FindCategoryController {

  private final FindCategoryUseCase findCategoryUseCase;

  public FindCategoryController(FindCategoryUseCase findCategoryUseCase) {
    this.findCategoryUseCase = findCategoryUseCase;
  }

  @GetMapping()
  public List<CategoryResponse> findCategories() {
    return findCategoryUseCase.findCategories().stream().map(CategoryResponse::from).toList();
  }

  @GetMapping("/{id}")
  public CategoryResponse findCategoryById(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {
    return CategoryResponse.from(findCategoryUseCase.findCategoryById(id));
  }
}
