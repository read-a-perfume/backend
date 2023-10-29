package io.perfume.api.note.adapter.out.persistence.note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoteJpaEntity is a Querydsl query type for NoteJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoteJpaEntity extends EntityPathBase<NoteJpaEntity> {

    private static final long serialVersionUID = 1013827380L;

    public static final QNoteJpaEntity noteJpaEntity = new QNoteJpaEntity("noteJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> thumbnailId = createNumber("thumbnailId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QNoteJpaEntity(String variable) {
        super(NoteJpaEntity.class, forVariable(variable));
    }

    public QNoteJpaEntity(Path<? extends NoteJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoteJpaEntity(PathMetadata metadata) {
        super(NoteJpaEntity.class, metadata);
    }

}

