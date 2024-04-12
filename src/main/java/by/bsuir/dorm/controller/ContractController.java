package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void createContract(@Valid @RequestBody ContractCreateRequestDto dto){

    }
}
