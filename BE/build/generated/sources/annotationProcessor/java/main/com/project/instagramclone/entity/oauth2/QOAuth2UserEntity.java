package com.project.instagramclone.entity.oauth2;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOAuth2UserEntity is a Querydsl query type for OAuth2UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOAuth2UserEntity extends EntityPathBase<OAuth2UserEntity> {

    private static final long serialVersionUID = -634122990L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOAuth2UserEntity oAuth2UserEntity = new QOAuth2UserEntity("oAuth2UserEntity");

    public final BooleanPath activated = createBoolean("activated");

    public final StringPath email = createString("email");

    public final QIdpEntity idpEntity;

    public final com.project.instagramclone.entity.member.QMemberEntity memberEntity;

    public final StringPath nickname = createString("nickname");

    public final StringPath role = createString("role");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QOAuth2UserEntity(String variable) {
        this(OAuth2UserEntity.class, forVariable(variable), INITS);
    }

    public QOAuth2UserEntity(Path<? extends OAuth2UserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOAuth2UserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOAuth2UserEntity(PathMetadata metadata, PathInits inits) {
        this(OAuth2UserEntity.class, metadata, inits);
    }

    public QOAuth2UserEntity(Class<? extends OAuth2UserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idpEntity = inits.isInitialized("idpEntity") ? new QIdpEntity(forProperty("idpEntity")) : null;
        this.memberEntity = inits.isInitialized("memberEntity") ? new com.project.instagramclone.entity.member.QMemberEntity(forProperty("memberEntity")) : null;
    }

}

