package ba.unsa.etf.nwt.taskservice.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class GenericSpecificationBuilder<T> {
    private final List<SearchCriteria<T>> criteria = new ArrayList<>();
    private final List<Specification<T>> specifications = new ArrayList<>();

    public final GenericSpecificationBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public final GenericSpecificationBuilder<T> with(final String[] keys,
                                                     final String value,
                                                     final SearchCriteria.SearchCriteriaOperation operation,
                                                     final Function<String, Object> action) {
        if (keys != null && value != null && operation != null && action != null) {
            criteria.add(new SearchCriteria<>(keys, value, operation, action));
        }
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if(!criteria.isEmpty()) {
            result = new GenericSpecification<>(criteria.get(0));
            for(int i = 1; i < criteria.size(); ++i) {
                SearchCriteria<T> criterion = criteria.get(i);
                result = Specification.where(result).and(new GenericSpecification<>(criterion));
            }
        }
        if(!specifications.isEmpty()) {
            int ind = 0;
            if(result == null) {
                result = specifications.get(0);
                ind = 1;
            }
            for(int i = ind; i < specifications.size(); ++i) {
                result = Specification.where(result).and(specifications.get(i));
            }
        }
        criteria.clear();
        specifications.clear();
        return result;
    }
}
