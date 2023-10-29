package io.perfume.api.note.adapter.out.persistence.categoryUser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryUserJpaEntity is a Querydsl query type for CategoryUserJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryUserJpaEntity extends EntityPathBase<CategoryUserJpaEntity> {

    private static final long serialVersionUID = 405601748L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryUserJpaEntity categoryUserJpaEntity = new QCategoryUserJpaEntity("categoryUserJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    public final io.perfume.api.note.adapter.out.persistence.category.QCategoryJpaEntity category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCategoryUserJpaEntity(String variable) {
        this(CategoryUserJpaEntity.class, forVariable(variable), INITS);
    }

    public QCategoryUserJpaEntity(Path<? extends CategoryUserJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryUserJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryUserJpaEntity(PathMetadata metadata, PathInits inits) {
        this(CategoryUserJpaEntity.class, metadata, inits);
    }

    public QCategoryUserJpaEntity(Class<? extends CategoryUserJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new io.perfume.api.note.adapter.out.persistence.category.QCategoryJpaEntity(forProperty("category")) : null;
    }

}

