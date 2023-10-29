package io.perfume.api.review.adapter.out.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewTagId is a Querydsl query type for ReviewTagId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReviewTagId extends BeanPath<ReviewTagId> {

    private static final long serialVersionUID = -1982107888L;

    public static final QReviewTagId reviewTagId = new QReviewTagId("reviewTagId");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QReviewTagId(String variable) {
        super(ReviewTagId.class, forVariable(variable));
    }

    public QReviewTagId(Path<? extends ReviewTagId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewTagId(PathMetadata metadata) {
        super(ReviewTagId.class, metadata);
    }

}

