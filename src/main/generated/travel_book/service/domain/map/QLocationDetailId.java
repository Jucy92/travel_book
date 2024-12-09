package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationDetailId is a Querydsl query type for LocationDetailId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLocationDetailId extends BeanPath<LocationDetailId> {

    private static final long serialVersionUID = -203808572L;

    public static final QLocationDetailId locationDetailId = new QLocationDetailId("locationDetailId");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final NumberPath<Long> locationSq = createNumber("locationSq", Long.class);

    public final NumberPath<Long> travelId = createNumber("travelId", Long.class);

    public QLocationDetailId(String variable) {
        super(LocationDetailId.class, forVariable(variable));
    }

    public QLocationDetailId(Path<? extends LocationDetailId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationDetailId(PathMetadata metadata) {
        super(LocationDetailId.class, metadata);
    }

}

