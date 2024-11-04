package com.project.instagramclone.entity.oauth2;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdpEntity is a Querydsl query type for IdpEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdpEntity extends EntityPathBase<IdpEntity> {

    private static final long serialVersionUID = -336195505L;

    public static final QIdpEntity idpEntity = new QIdpEntity("idpEntity");

    public final NumberPath<Long> idpId = createNumber("idpId", Long.class);

    public final StringPath idpName = createString("idpName");

    public QIdpEntity(String variable) {
        super(IdpEntity.class, forVariable(variable));
    }

    public QIdpEntity(Path<? extends IdpEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdpEntity(PathMetadata metadata) {
        super(IdpEntity.class, metadata);
    }

}

