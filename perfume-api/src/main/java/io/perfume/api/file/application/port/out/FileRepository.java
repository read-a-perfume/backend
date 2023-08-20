package io.perfume.api.file.application.port.out;

import io.perfume.api.file.domain.File;
import java.util.List;

public interface FileRepository {

  File save(File file);

  List<File> saveAll(List<File> files);
}
