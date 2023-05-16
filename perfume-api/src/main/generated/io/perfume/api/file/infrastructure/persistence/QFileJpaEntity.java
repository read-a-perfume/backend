package io.perfume.api.file.infrastructure.persistence;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileJpaEntity is a Querydsl query type for FileJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileJpaEntity extends EntityPathBase<FileJpaEntity> {

    private static final long serialVersionUID = 1350648372L;

    public static final QFileJpaEntity fileJpaEntity = new QFileJpaEntity("fileJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath url = createString("url");

    public QFileJpaEntity(String variable) {
        super(FileJpaEntity.class, forVariable(variable));
    }

    public QFileJpaEntity(Path<? extends FileJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileJpaEntity(PathMetadata metadata) {
        super(FileJpaEntity.class, metadata);
    }

}

