package com.ccsw.tutorial_loan.loan;

import com.ccsw.tutorial_loan.common.criteria.SearchCriteria;
import com.ccsw.tutorial_loan.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;
    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        }
        if (criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null) {
            Path<?> path = getPath(root);
            if (path.getJavaType() == LocalDate.class) {
                Path<LocalDate> expression = root.get(criteria.getKey());
                return builder.greaterThanOrEqualTo(expression, (LocalDate) criteria.getValue());
            }
        }

        if (criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null) {
            Path<?> path = getPath(root);
            if (path.getJavaType() == LocalDate.class) {
                Path<LocalDate> expression = root.get(criteria.getKey());
                return builder.lessThanOrEqualTo(expression, (LocalDate) criteria.getValue());
            }
        }

        return null;
    }

    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}