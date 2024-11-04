package com.project.instagramclone.entity.token;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefreshTokenEntity is a Querydsl query type for RefreshTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshTokenEntity extends EntityPathBase<RefreshTokenEntity> {

    private static final long serialVersionUID = 1651675328L;

    public static final QRefreshTokenEntity refreshTokenEntity = new QRefreshTokenEntity("refreshTokenEntity");

    public final StringPath expiration = createString("expiration");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath refresh = createString("refresh");

    public final StringPath username = createString("username");

    public QRefreshTokenEntity(String variable) {
        super(RefreshTokenEntity.class, forVariable(variable));
    }

    public QRefreshTokenEntity(Path<? extends RefreshTokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefreshTokenEntity(PathMetadata metadata) {
        super(RefreshTokenEntity.class, metadata);
    }

}

