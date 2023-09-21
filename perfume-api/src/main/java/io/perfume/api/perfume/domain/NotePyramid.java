package io.perfume.api.perfume.domain;

import java.util.List;
import lombok.Builder;

@Builder
public record NotePyramid(List<PerfumeNote> topNotes, List<PerfumeNote> middleNotes, List<PerfumeNote> baseNotes) {

}
