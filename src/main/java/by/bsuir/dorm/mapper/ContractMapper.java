package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ContractDto;
import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import by.bsuir.dorm.model.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                RoomMapper.class,
                UserMapper.class,
                RepresentativeMapper.class
        }
)
public interface ContractMapper {

    @Mapping(target = "fullName", source = ".")
    @Mapping(target = "passport.number", source = "passportNumber")
    @Mapping(target = "passport.series", source = "passportSeries")
    @Mapping(target = "passport.issuePlace", source = "passportIssuePlace")
    @Mapping(target = "passport.issueDate", source = "passportIssueDate")
    @Mapping(target = "passport.passportId", source = "passportId")
    @Mapping(target = "date", expression = "java(LocalDate.now())")
    @Mapping(target = "terminationDate", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "room", ignore = true)
    Contract toEntity(ContractCreateRequestDto contractCreateRequestDto);

    @Mapping(target = ".", source = "fullName")
    ContractDto toDto(Contract contract);
}
