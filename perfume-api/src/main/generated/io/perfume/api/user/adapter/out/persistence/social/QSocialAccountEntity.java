package io.perfume.api.user.adapter.out.persistence.social;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSocialAccountEntity is a Querydsl query type for SocialAccountEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialAccountEntity extends EntityPathBase<SocialAccountEntity> {

    private static final long serialVersionUID = -1345167891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSocialAccountEntity socialAccountEntity = new QSocialAccountEntity("socialAccountEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath identifier = createString("identifier");

    public final EnumPath<io.perfume.api.user.domain.SocialProvider> socialProvider = createEnum("socialProvider", io.perfume.api.user.domain.SocialProvider.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final io.perfume.api.user.adapter.out.persistence.user.QUserEntity user;

    public QSocialAccountEntity(String variable) {
        this(SocialAccountEntity.class, forVariable(variable), INITS);
    }

    public QSocialAccountEntity(Path<? extends SocialAccountEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSocialAccountEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSocialAccountEntity(PathMetadata metadata, PathInits inits) {
        this(SocialAccountEntity.class, metadata, inits);
    }

    public QSocialAccountEntity(Class<? extends SocialAccountEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new io.perfume.api.user.adapter.out.persistence.user.QUserEntity(forProperty("user")) : null;
    }

}

