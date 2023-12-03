package io.perfume.api.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.perfume.api.auth.adapter.out.persistence.AuthenticationKeyMapper;
import io.perfume.api.auth.adapter.out.persistence.AuthenticationKeyQueryRepositoryImpl;
import io.perfume.api.sample.adapter.out.persistence.SampleQueryRepositoryImpl;
import io.perfume.api.user.adapter.out.persistence.user.UserMapper;
import io.perfume.api.user.adapter.out.persistence.user.UserQueryPersistenceAdapter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQueryDSLConfiguration {

  @PersistenceContext private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public SampleQueryRepositoryImpl sampleQueryRepository(JPAQueryFactory jpaQueryFactory) {
    return new SampleQueryRepositoryImpl(jpaQueryFactory);
  }

  @Bean
  public AuthenticationKeyQueryRepositoryImpl authenticationKeyQueryRepository(
      JPAQueryFactory jpaQueryFactory) {
    return new AuthenticationKeyQueryRepositoryImpl(new AuthenticationKeyMapper(), jpaQueryFactory);
  }

  @Bean
  public UserQueryPersistenceAdapter userQueryPersistenceAdapter(JPAQueryFactory jpaQueryFactory) {
    return new UserQueryPersistenceAdapter(jpaQueryFactory, new UserMapper());
  }
}
