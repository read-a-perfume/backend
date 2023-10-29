package io.perfume.api.auth.adapter.out.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthenticationKeyJpaEntity is a Querydsl query type for AuthenticationKeyJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthenticationKeyJpaEntity extends EntityPathBase<AuthenticationKeyJpaEntity> {

    private static final long serialVersionUID = -1141373499L;

    public static final QAuthenticationKeyJpaEntity authenticationKeyJpaEntity = new QAuthenticationKeyJpaEntity("authenticationKeyJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath signKey = createString("signKey");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final DateTimePath<java.time.LocalDateTime> verifiedAt = createDateTime("verifiedAt", java.time.LocalDateTime.class);

    public QAuthenticationKeyJpaEntity(String variable) {
        super(AuthenticationKeyJpaEntity.class, forVariable(variable));
    }

    public QAuthenticationKeyJpaEntity(Path<? extends AuthenticationKeyJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthenticationKeyJpaEntity(PathMetadata metadata) {
        super(AuthenticationKeyJpaEntity.class, metadata);
    }

}

