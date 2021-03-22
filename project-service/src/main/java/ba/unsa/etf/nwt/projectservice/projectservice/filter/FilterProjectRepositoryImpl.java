package ba.unsa.etf.nwt.projectservice.projectservice.filter;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterProjectRepositoryImpl implements FilterProjectRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Project> findAllByFilter(final ProjectFilter filter, final Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        Root<Project> root = criteriaQuery.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getOwnerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("ownerId"), filter.getOwnerId()));
        }

        if(filter.getCollaboratorId() != null) {
            Join<Project, ProjectCollaborator> collab = root.join("projectCollaborators", JoinType.INNER);
            collab.on(criteriaBuilder.equal(collab.get("collaboratorId"), filter.getCollaboratorId()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        TypedQuery<Project> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        List<Project> resultList = typedQuery.getResultList();
        Long total = getTotal(criteriaBuilder, predicates);

        return new PageImpl<>(resultList, pageable, total);
    }

    private Long getTotal(final CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Project> projectRootCount = countQuery.from(Project.class);
        countQuery
                .select(criteriaBuilder.count(projectRootCount))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
