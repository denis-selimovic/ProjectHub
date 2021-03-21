package ba.unsa.etf.nwt.taskservice.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenericSpecificationBuilder<T> {
    private final List<SearchCriteria> criteria = new ArrayList<>();
    private final List<Specification<T>> specifications = new ArrayList<>();

    public final GenericSpecificationBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public final GenericSpecificationBuilder<T> with(final String key,
                                                     final String value,
                                                     final SearchCriteria.SearchCriteriaOperation operation) {
        if (key != null && value != null && operation != null)
            criteria.add(new SearchCriteria(key, value, operation));
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if(!criteria.isEmpty()) {
            result = new GenericSpecification<>(criteria.get(0));
            for(int i = 1; i < criteria.size(); ++i) {
                SearchCriteria criterion = criteria.get(i);
                result = Specification.where(result).and(new GenericSpecification<>(criterion));
            }

        }
        if(!specifications.isEmpty()) {
            if(result == null) result = specifications.get(0);
            for(int i = 1; i < specifications.size(); ++i) {
                result = Specification.where(result).and(specifications.get(i));
            }
        }
        return result;
    }
}
