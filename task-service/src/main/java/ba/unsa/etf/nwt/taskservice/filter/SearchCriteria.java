package ba.unsa.etf.nwt.taskservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.criteria.Path;

@Data
@AllArgsConstructor
public class SearchCriteria<T> {
    public enum SearchCriteriaOperation { EQ, NEQ, LTE, GTE, LIKE }

    private Path<T> key;
    private String value;
    private SearchCriteriaOperation operation;
}
