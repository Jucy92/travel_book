package travel_book.service.domain.map;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTravel is a Querydsl query type for Travel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTravel extends EntityPathBase<Travel> {

    private static final long serialVersionUID = 1661513597L;

    public static final QTravel travel = new QTravel("travel");

    public final DateTimePath<java.time.LocalDateTime> cdt = createDateTime("cdt", java.time.LocalDateTime.class);

    public final NumberPath<Long> cid = createNumber("cid", Long.class);

    public final ListPath<Location, QLocation> locations = this.<Location, QLocation>createList("locations", Location.class, QLocation.class, PathInits.DIRECT2);

    public final NumberPath<Long> oid = createNumber("oid", Long.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> travelId = createNumber("travelId", Long.class);

    public QTravel(String variable) {
        super(Travel.class, forVariable(variable));
    }

    public QTravel(Path<? extends Travel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTravel(PathMetadata metadata) {
        super(Travel.class, metadata);
    }

}

