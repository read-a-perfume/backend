package io.perfume.api.user.adapter.out.persistence;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({UserPersistenceAdapter.class})
public class UserJpaRepositoryTest {

}
