package ba.unsa.etf.nwt.taskservice.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public class GenericSpecificationBuilder<T> {
    private List<SearchCriteria<T>> criteria = new ArrayList<>();
    private List<Specification<T>> specifications = new ArrayList<>();
    public Root<T> root;

    @PersistenceContext
    private EntityManager entityManager;


    public Root<T> setup(Class<T> type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        this.root = criteriaQuery.from(type);
        return this.root;
    }

    public final GenericSpecificationBuilder<T> with(Specification<T> specification) {
        if (specifications == null) specifications = new ArrayList<>();
        specifications.add(specification);
        return this;
    }

    public final GenericSpecificationBuilder<T> with(final Path<T> key,
                                                     final String value,
                                                     final SearchCriteria.SearchCriteriaOperation operation) {
        if (key != null && value != null && operation != null)
            if (criteria == null) criteria = new ArrayList<>();
            criteria.add(new SearchCriteria<>(key, value, operation));
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
            if(result == null) result = specifications.get(0);
            for(int i = 1; i < specifications.size(); ++i) {
                result = Specification.where(result).and(specifications.get(i));
            }
        }
        return result;
    }
}
