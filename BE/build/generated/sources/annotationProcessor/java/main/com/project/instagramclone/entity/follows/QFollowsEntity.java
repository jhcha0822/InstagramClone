package com.project.instagramclone.entity.follows;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollowsEntity is a Querydsl query type for FollowsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollowsEntity extends EntityPathBase<FollowsEntity> {

    private static final long serialVersionUID = 1066994255L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollowsEntity followsEntity = new QFollowsEntity("followsEntity");

    public final com.project.instagramclone.entity.member.QMemberEntity follower;

    public final com.project.instagramclone.entity.member.QMemberEntity following;

    public final NumberPath<Long> followsId = createNumber("followsId", Long.class);

    public QFollowsEntity(String variable) {
        this(FollowsEntity.class, forVariable(variable), INITS);
    }

    public QFollowsEntity(Path<? extends FollowsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollowsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollowsEntity(PathMetadata metadata, PathInits inits) {
        this(FollowsEntity.class, metadata, inits);
    }

    public QFollowsEntity(Class<? extends FollowsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.follower = inits.isInitialized("follower") ? new com.project.instagramclone.entity.member.QMemberEntity(forProperty("follower")) : null;
        this.following = inits.isInitialized("following") ? new com.project.instagramclone.entity.member.QMemberEntity(forProperty("following")) : null;
    }

}

