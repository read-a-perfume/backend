package io.perfume.api.review.adapter.out.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTagEntity is a Querydsl query type for TagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagEntity extends EntityPathBase<TagEntity> {

    private static final long serialVersionUID = -699737584L;

    public static final QTagEntity tagEntity = new QTagEntity("tagEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTagEntity(String variable) {
        super(TagEntity.class, forVariable(variable));
    }

    public QTagEntity(Path<? extends TagEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagEntity(PathMetadata metadata) {
        super(TagEntity.class, metadata);
    }

}

