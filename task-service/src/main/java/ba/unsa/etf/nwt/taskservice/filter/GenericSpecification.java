package ba.unsa.etf.nwt.taskservice.filter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@RequiredArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root,
                                 @NonNull CriteriaQuery<?> criteriaQuery,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        String key = searchCriteria.getKey();
        String value = searchCriteria.getValue();
        switch (searchCriteria.getOperation()) {
            case EQ -> {
                return criteriaBuilder.equal(root.get(key), value);
            }
            case NEQ -> {
                return criteriaBuilder.notEqual(root.get(key), value);
            }
            case LTE -> {
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), value);
            }
            case GTE -> {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), value);
            }
            case LIKE -> {
                return criteriaBuilder.like(root.get(key), value);
            }
        }
        return null;
    }
}
