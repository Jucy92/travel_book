package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -400080040L;

    public static final QLocation location = new QLocation("location");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final ListPath<LocationDetail, QLocationDetail> locationDetails = this.<LocationDetail, QLocationDetail>createList("locationDetails", LocationDetail.class, QLocationDetail.class, PathInits.DIRECT2);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Long> travelId = createNumber("travelId", Long.class);

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

