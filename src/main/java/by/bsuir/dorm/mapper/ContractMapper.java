package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ContractDto;
import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import by.bsuir.dorm.model.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                RoomMapper.class,
                UserMapper.class
        }
)
public interface ContractMapper {

    @Mapping(target = "fullName", source = ".")
    @Mapping(target = "passport.number", source = "passportNumber")
    @Mapping(target = "passport.series", source = "passportSeries")
    @Mapping(target = "passport.issuePlace", source = "passportIssuePlace")
    @Mapping(target = "passport.issueDate", source = "passportIssueDate")
    @Mapping(target = "date", expression = "java(LocalDate.now())")
    @Mapping(target = "terminationDate", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "room", ignore = true)
    Contract toEntity(ContractCreateRequestDto contractCreateRequestDto);

    @Mapping(target = ".", source = "fullName")
    @Mapping(target = "passportNumber", source = "passport.number")
    @Mapping(target = "passportSeries", source = "passport.series")
    @Mapping(target = "passportIssuePlace", source = "passport.series")
    @Mapping(target = "passportIssueDate", source = "passport.issueDate")
    ContractDto toDto(Contract contract);
}
