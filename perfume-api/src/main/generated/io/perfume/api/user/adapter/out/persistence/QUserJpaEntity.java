package io.perfume.api.user.adapter.out.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserJpaEntity is a Querydsl query type for UserJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserJpaEntity extends EntityPathBase<UserJpaEntity> {

    private static final long serialVersionUID = -1533263352L;

    public static final QUserJpaEntity userJpaEntity = new QUserJpaEntity("userJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    public final NumberPath<Long> businessId = createNumber("businessId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath marketingConsent = createBoolean("marketingConsent");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final BooleanPath promotionConsent = createBoolean("promotionConsent");

    public final EnumPath<io.perfume.api.user.domain.Role> role = createEnum("role", io.perfume.api.user.domain.Role.class);

    public final NumberPath<Long> thumbnailId = createNumber("thumbnailId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QUserJpaEntity(String variable) {
        super(UserJpaEntity.class, forVariable(variable));
    }

    public QUserJpaEntity(Path<? extends UserJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserJpaEntity(PathMetadata metadata) {
        super(UserJpaEntity.class, metadata);
    }

}

