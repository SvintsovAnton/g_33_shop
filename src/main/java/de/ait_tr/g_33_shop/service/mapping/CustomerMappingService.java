package de.ait_tr.g_33_shop.service.mapping;

import de.ait_tr.g_33_shop.domain.dto.CustomerDto;
import de.ait_tr.g_33_shop.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMappingService {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "active", constant = "true")

    Customer mapDtoInEntity(CustomerDto dto);
    CustomerDto mapEntityToDto(Customer entity);

}
