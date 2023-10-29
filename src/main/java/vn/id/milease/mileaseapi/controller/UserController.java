package vn.id.milease.mileaseapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.id.milease.mileaseapi.model.dto.PageResult;
import vn.id.milease.mileaseapi.model.dto.TravelerDto;
import vn.id.milease.mileaseapi.model.dto.search.UserSearchDto;
import vn.id.milease.mileaseapi.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public PageResult<TravelerDto> getUser(UserSearchDto searchDto) {
        return userService.getUsers(searchDto);
    }

    @GetMapping("/count")
    public int countUser(UserSearchDto searchDto) {
        return userService.countUser(searchDto);
    }

    @GetMapping("/premium/count")
    public int countPremiumUser(UserSearchDto searchDto) {
        return userService.countPremiumUser(searchDto);
    }
}
