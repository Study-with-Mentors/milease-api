package vn.id.milease.mileaseapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.ProvinceDto;
import vn.id.milease.mileaseapi.service.ProvinceService;

import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public List<ProvinceDto> getAll(@RequestParam(defaultValue = "false") boolean withDistricts) {
        return provinceService.getAll(withDistricts);
    }
}
