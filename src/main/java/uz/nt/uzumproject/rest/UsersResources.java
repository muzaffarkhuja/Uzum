package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }
    @DeleteMapping
    public ResponseDto<UsersDto> deleteUser(@RequestParam Integer id){
        return  usersService.deleteUser(id);
    }

    @GetMapping
    public ResponseDto<List<UsersDto>> getAllUsers(){
        return usersService.getAllUsers();
    }
}