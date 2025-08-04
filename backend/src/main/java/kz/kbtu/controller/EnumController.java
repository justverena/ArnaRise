package kz.kbtu.controller;

import kz.kbtu.dto.enums.EnumDto;
import kz.kbtu.service.EnumService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enums")
public class EnumController {
    private final EnumService enumService;
    public EnumController(EnumService enumService) {
        this.enumService = enumService;
    }

    @GetMapping("/{enumName}")
    public ResponseEntity<List<EnumDto>> getEnum(@PathVariable String enumName, @RequestHeader(name = "Accept-Language", defaultValue = "ru") Locale locale) {
        try {
            String className = "kz.kbtu.enums." + toPascalCase(enumName);
            Class<?> enumClass = Class.forName(className);
            if(!enumClass.isEnum()) return ResponseEntity.badRequest().build();
            Enum<?> [] constants = (Enum<?>[]) enumClass.getEnumConstants();
            List<EnumDto> result = Arrays.stream(constants).map(e -> new EnumDto(e.name(), enumService.translateEnum(e, locale))).toList();
            return ResponseEntity.ok(result);
        } catch (ClassNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String toPascalCase(String name) {
        return Arrays.stream(name.split("[-_]"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }
}
