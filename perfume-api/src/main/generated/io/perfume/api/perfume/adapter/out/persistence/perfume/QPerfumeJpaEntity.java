package io.perfume.api.perfume.adapter.out.persistence.perfume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerfumeJpaEntity is a Querydsl query type for PerfumeJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerfumeJpaEntity extends EntityPathBase<PerfumeJpaEntity> {

    private static final long serialVersionUID = -37469694L;

    public static final QPerfumeJpaEntity perfumeJpaEntity = new QPerfumeJpaEntity("perfumeJpaEntity");

    public final io.perfume.api.base.QBaseTimeEntity _super = new io.perfume.api.base.QBaseTimeEntity(this);

    public final NumberPath<Long> brandId = createNumber("brandId", Long.class);

    public final NumberPath<Long> capacity = createNumber("capacity", Long.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final EnumPath<io.perfume.api.perfume.domain.Concentration> concentration = createEnum("concentration", io.perfume.api.perfume.domain.Concentration.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath perfumeShopUrl = createString("perfumeShopUrl");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath story = createString("story");

    public final NumberPath<Long> thumbnailId = createNumber("thumbnailId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPerfumeJpaEntity(String variable) {
        super(PerfumeJpaEntity.class, forVariable(variable));
    }

    public QPerfumeJpaEntity(Path<? extends PerfumeJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerfumeJpaEntity(PathMetadata metadata) {
        super(PerfumeJpaEntity.class, metadata);
    }

}

