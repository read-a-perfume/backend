package io.perfume.api.perfume.adapter.out.persistence.perfumeNote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerfumeNoteEntity is a Querydsl query type for PerfumeNoteEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerfumeNoteEntity extends EntityPathBase<PerfumeNoteEntity> {

    private static final long serialVersionUID = -451012317L;

    public static final QPerfumeNoteEntity perfumeNoteEntity = new QPerfumeNoteEntity("perfumeNoteEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> noteId = createNumber("noteId", Long.class);

    public final EnumPath<NoteLevel> noteLevel = createEnum("noteLevel", NoteLevel.class);

    public final NumberPath<Long> perfumeId = createNumber("perfumeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPerfumeNoteEntity(String variable) {
        super(PerfumeNoteEntity.class, forVariable(variable));
    }

    public QPerfumeNoteEntity(Path<? extends PerfumeNoteEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfumeNoteEntity(PathMetadata metadata) {
        super(PerfumeNoteEntity.class, metadata);
    }

}

