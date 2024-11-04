package com.project.instagramclone.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = -485815641L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final BooleanPath activated = createBoolean("activated");

    public final StringPath bio = createString("bio");

    public final StringPath email = createString("email");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath oauth2Provider = createString("oauth2Provider");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath password = createString("password");

    public final StringPath profilePic = createString("profilePic");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

