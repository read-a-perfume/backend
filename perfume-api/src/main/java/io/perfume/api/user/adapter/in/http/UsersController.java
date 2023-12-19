package io.perfume.api.user.adapter.in.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

  @RequestMapping(value = "/{username}", method = RequestMethod.GET)
  public ResponseEntity<Void> checkUsername(@PathVariable String username) {
    final HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status).build();
  }
}
