package ba.unsa.etf.nwt.taskservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

@Data
@RequiredArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria<T> searchCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root,
                                 @NonNull CriteriaQuery<?> criteriaQuery,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        String[] keys = searchCriteria.getKeys();
        String value = searchCriteria.getValue();
        Path<T> path = null;
        if (keys.length == 1) {
            path = root.get(keys[0]);
        }
        else {
            for (int i = 0; i < keys.length; ++i) {
                if (i == 0) path = root.join(keys[i]);
                else path = path.get(keys[i]);
            }
        }
        switch (searchCriteria.getOperation()) {
            case EQ -> {
                return criteriaBuilder.equal(path, searchCriteria.getAction().apply(value));
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(path, searchCriteria.getAction().apply(value));
            }
        }
        return null;
    }
}
