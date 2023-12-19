package io.perfume.api.auth.adapter.in.http;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

  @PostMapping("/password/reset")
  public ResponseEntity<Object> sendPasswordToUsersEmail(@Email String email) {
    return ResponseEntity.ok().build();
  }
}
