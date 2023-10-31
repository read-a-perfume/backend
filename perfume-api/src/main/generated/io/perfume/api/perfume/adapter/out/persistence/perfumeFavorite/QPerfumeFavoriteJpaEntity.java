package io.perfume.api.perfume.adapter.out.persistence.perfumeFavorite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerfumeFavoriteJpaEntity is a Querydsl query type for PerfumeFavoriteJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerfumeFavoriteJpaEntity extends EntityPathBase<PerfumeFavoriteJpaEntity> {

<<<<<<< HEAD
<<<<<<< HEAD
    private static final long serialVersionUID = -1388910710L;
=======
    private static final long serialVersionUID = -1699187307L;
>>>>>>> a03dde0 ([RDPF-193] refactor: PerfumeFollow -> PerfumeFavorite 변경)
=======
    private static final long serialVersionUID = -1388910710L;
>>>>>>> ff96a83 ([RDPF-193] feat: 서비스 로직 변경, api 추가)

    public static final QPerfumeFavoriteJpaEntity perfumeFavoriteJpaEntity = new QPerfumeFavoriteJpaEntity("perfumeFavoriteJpaEntity");

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

    public QPerfumeFavoriteJpaEntity(String variable) {
        super(PerfumeFavoriteJpaEntity.class, forVariable(variable));
    }

    public QPerfumeFavoriteJpaEntity(Path<? extends PerfumeFavoriteJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfumeFavoriteJpaEntity(PathMetadata metadata) {
        super(PerfumeFavoriteJpaEntity.class, metadata);
    }

}

