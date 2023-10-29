package io.perfume.api.perfume.adapter.out.persistence.perfumeFollow;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerfumeFollowJpaEntity is a Querydsl query type for PerfumeFollowJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerfumeFollowJpaEntity extends EntityPathBase<PerfumeFollowJpaEntity> {

    private static final long serialVersionUID = -610731040L;

    public static final QPerfumeFollowJpaEntity perfumeFollowJpaEntity = new QPerfumeFollowJpaEntity("perfumeFollowJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> perfumeId = createNumber("perfumeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPerfumeFollowJpaEntity(String variable) {
        super(PerfumeFollowJpaEntity.class, forVariable(variable));
    }

    public QPerfumeFollowJpaEntity(Path<? extends PerfumeFollowJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfumeFollowJpaEntity(PathMetadata metadata) {
        super(PerfumeFollowJpaEntity.class, metadata);
    }

}

