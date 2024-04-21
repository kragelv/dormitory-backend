package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RepresentativeDto;
import by.bsuir.dorm.dto.request.ContractCreateRepresentativeDto;
import by.bsuir.dorm.model.entity.Representative;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepresentativeMapper {
    @Mapping(target = "fullName", source = ".")
    @Mapping(target = "passport.number", source = "passportNumber")
    @Mapping(target = "passport.series", source = "passportSeries")
    @Mapping(target = "passport.issuePlace", source = "passportIssuePlace")
    @Mapping(target = "passport.issueDate", source = "passportIssueDate")
    @Mapping(target = "passport.passportId", source = "passportId")
    Representative toEntity(ContractCreateRepresentativeDto representativeDto);

    @Mapping(target = ".", source = "fullName")
    RepresentativeDto toDto(Representative representative);
}