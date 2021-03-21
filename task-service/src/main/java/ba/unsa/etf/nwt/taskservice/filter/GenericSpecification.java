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
        Expression<T> path = searchCriteria.getKey();
        String value = searchCriteria.getValue();
        switch (searchCriteria.getOperation()) {
            case EQ -> {
                return criteriaBuilder.equal(path, value);
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(path, value);
            }
        }
        return null;
    }
}
