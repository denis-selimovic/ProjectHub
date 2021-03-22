package ba.unsa.etf.nwt.taskservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;

@Data
@AllArgsConstructor
public class SearchCriteria<T> {
    public enum SearchCriteriaOperation { EQ, NEQ, LTE, GTE, LIKE }

    private String[] keys;
    private String value;
    private SearchCriteriaOperation operation;
    private Function<String, Object> action;
}
