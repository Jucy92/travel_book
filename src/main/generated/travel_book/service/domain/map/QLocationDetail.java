package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationDetail is a Querydsl query type for LocationDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationDetail extends EntityPathBase<LocationDetail> {

    private static final long serialVersionUID = -648256055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationDetail locationDetail = new QLocationDetail("locationDetail");

    public final StringPath content = createString("content");

    public final QLocationDetailId id;

    public final QLocation location;

    public QLocationDetail(String variable) {
        this(LocationDetail.class, forVariable(variable), INITS);
    }

    public QLocationDetail(Path<? extends LocationDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationDetail(PathMetadata metadata, PathInits inits) {
        this(LocationDetail.class, metadata, inits);
    }

    public QLocationDetail(Class<? extends LocationDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QLocationDetailId(forProperty("id")) : null;
        this.location = inits.isInitialized("location") ? new QLocation(forProperty("location"), inits.get("location")) : null;
    }

}

