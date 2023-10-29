package io.perfume.api.review.adapter.out.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewEntity is a Querydsl query type for ReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEntity extends EntityPathBase<ReviewEntity> {

    private static final long serialVersionUID = -1732815224L;

    public static final QReviewEntity reviewEntity = new QReviewEntity("reviewEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> duration = createNumber("duration", Long.class);

    public final StringPath feeling = createString("feeling");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> perfumeId = createNumber("perfumeId", Long.class);

    public final EnumPath<io.perfume.api.review.domain.type.SEASON> season = createEnum("season", io.perfume.api.review.domain.type.SEASON.class);

    public final StringPath situation = createString("situation");

    public final EnumPath<io.perfume.api.review.domain.type.STRENGTH> strength = createEnum("strength", io.perfume.api.review.domain.type.STRENGTH.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReviewEntity(String variable) {
        super(ReviewEntity.class, forVariable(variable));
    }

    public QReviewEntity(Path<? extends ReviewEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewEntity(PathMetadata metadata) {
        super(ReviewEntity.class, metadata);
    }

}

