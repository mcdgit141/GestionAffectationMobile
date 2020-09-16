package com.epita.filrouge;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractMapper<T, S> {
    /**
     * @param dto dto
     * @return the mapped dto
     */
    public abstract T mapToDomain(S dto);

    /**
     * @param domain dto
     * @return the mapped entity
     */
    public abstract S mapToDTO(T domain);

    /**
     * @param dtoList dtoList
     * @return a List of the mapped entity
     */
    public List<T> mapToDomainList(final List<S> dtoList) {
        return dtoList.stream().filter(Objects::nonNull).map(this::mapToDomain).collect(Collectors.toList());
    }

    /**
     * @param domain domain
     * @return a List of the mapped entity
     */
    public List<S> mapToDTOList(final List<T> domain) {
        return domain.stream().filter(Objects::nonNull).map(this::mapToDTO).collect(Collectors.toList());
    }

}
