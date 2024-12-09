package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationId is a Querydsl query type for LocationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLocationId extends BeanPath<LocationId> {

    private static final long serialVersionUID = 2070140563L;

    public static final QLocationId locationId1 = new QLocationId("locationId1");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final NumberPath<Long> travelId = createNumber("travelId", Long.class);

    public QLocationId(String variable) {
        super(LocationId.class, forVariable(variable));
    }

    public QLocationId(Path<? extends LocationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationId(PathMetadata metadata) {
        super(LocationId.class, metadata);
    }

}

