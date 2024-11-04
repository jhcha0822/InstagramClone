package com.project.instagramclone.entity.form;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFormUserEntity is a Querydsl query type for FormUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFormUserEntity extends EntityPathBase<FormUserEntity> {

    private static final long serialVersionUID = 1512603282L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFormUserEntity formUserEntity = new QFormUserEntity("formUserEntity");

    public final BooleanPath activated = createBoolean("activated");

    public final StringPath email = createString("email");

    public final com.project.instagramclone.entity.member.QMemberEntity memberEntity;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QFormUserEntity(String variable) {
        this(FormUserEntity.class, forVariable(variable), INITS);
    }

    public QFormUserEntity(Path<? extends FormUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFormUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFormUserEntity(PathMetadata metadata, PathInits inits) {
        this(FormUserEntity.class, metadata, inits);
    }

    public QFormUserEntity(Class<? extends FormUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new com.project.instagramclone.entity.member.QMemberEntity(forProperty("memberEntity")) : null;
    }

}

