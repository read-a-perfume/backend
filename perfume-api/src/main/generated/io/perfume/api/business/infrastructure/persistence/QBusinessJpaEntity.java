package io.perfume.api.business.infrastructure.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessJpaEntity is a Querydsl query type for BusinessJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessJpaEntity extends EntityPathBase<BusinessJpaEntity> {

    private static final long serialVersionUID = 764412588L;

    public static final QBusinessJpaEntity businessJpaEntity = new QBusinessJpaEntity("businessJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    public final NumberPath<Long> companyLogoId = createNumber("companyLogoId", Long.class);

    public final StringPath companyName = createString("companyName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath registrationNumber = createString("registrationNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBusinessJpaEntity(String variable) {
        super(BusinessJpaEntity.class, forVariable(variable));
    }

    public QBusinessJpaEntity(Path<? extends BusinessJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessJpaEntity(PathMetadata metadata) {
        super(BusinessJpaEntity.class, metadata);
    }

}

