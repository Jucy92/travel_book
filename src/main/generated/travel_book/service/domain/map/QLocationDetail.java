package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationDetail is a Querydsl query type for LocationDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationDetail extends EntityPathBase<LocationDetail> {

    private static final long serialVersionUID = -648256055L;

    public static final QLocationDetail locationDetail = new QLocationDetail("locationDetail");

    public final StringPath content = createString("content");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final NumberPath<Long> locationSq = createNumber("locationSq", Long.class);

    public final NumberPath<Long> travelId = createNumber("travelId", Long.class);

    public QLocationDetail(String variable) {
        super(LocationDetail.class, forVariable(variable));
    }

    public QLocationDetail(Path<? extends LocationDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationDetail(PathMetadata metadata) {
        super(LocationDetail.class, metadata);
    }

}

