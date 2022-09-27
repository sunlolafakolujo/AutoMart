package com.dapafol.userregistration.appuser.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.entity.AppUserDto;
import com.dapafol.userregistration.appuser.entity.UpdateAppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.service.AppUserService;
import com.dapafol.userregistration.staticdata.UserType;
import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import com.dapafol.userregistration.verificationtoken.exception.VerificationTokenNotFoundException;
import com.dapafol.userregistration.verificationtoken.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class AppUserSearchController {
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;
    private final VerificationService verificationService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable(value = "id")Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserById(id);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findByFirstName/{firstName}")
    public ResponseEntity<List<AppUserDto>> getUserByFirstName(@PathVariable(value = "firstName") String firstName)
                                                                                throws AppUserNotFoundException {

        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByFirstName(firstName, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findByLastName/{lastName}")
    public ResponseEntity<List<AppUserDto>> getUserByLastName(@PathVariable(value = "lastName")String lastname)
                                                                            throws AppUserNotFoundException {

        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByLastName(lastname, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findByFirstNameAndLastName/{firstName},{lastName}")
    public ResponseEntity<List<AppUserDto>> getUserByFirstNameAndLastName(@PathVariable(value = "firstName") String firstName,
                                                                          @PathVariable(value = "lastName")String lastName)
                                                                          throws AppUserNotFoundException {

        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByFirstAndLastName(firstName,
                        lastName,
                        pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<AppUserDto> getUserByUsername(@PathVariable(value ="username") String username)
                                                                        throws AppUserNotFoundException {

        AppUser appUser= appUserService.findUserByUsername(username);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<AppUserDto> getUserByEmail(@PathVariable(value ="email") String email)
                                                               throws AppUserNotFoundException {

        AppUser appUser= appUserService.findUserByEmail(email);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findByPhone/{phone}")
    public ResponseEntity<AppUserDto> getUserByPhone(@PathVariable(value ="phone") String phone)
                                                               throws AppUserNotFoundException {

        AppUser appUser= appUserService.findUserByPhone(phone);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findUserByStatus/{status}")
    public ResponseEntity<List<AppUserDto>> getUserByStatus(@PathVariable(value = "status") Boolean isEnabled)
                                                                             throws AppUserNotFoundException {

        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByRegistrationStatus(isEnabled, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findByType/{userType}")
    public ResponseEntity<List<AppUserDto>> getUserByType(@PathVariable(value = "userType") UserType userType)
                                                                            throws AppUserNotFoundException {
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByType(userType,pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findAllUsers(pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UpdateAppUser> updateAppUser(@RequestBody @Valid UpdateAppUser updateAppUser,
                                                       @PathVariable(value = "id")Long id) throws AppUserNotFoundException {
        AppUser appUser=modelMapper.map(updateAppUser, AppUser.class);
        AppUser updated=appUserService.updateUser(appUser, id);
        UpdateAppUser posted=modelMapper.map(updated, UpdateAppUser.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id")Long id) throws AppUserNotFoundException {
        appUserService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers(){
        appUserService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }



    private AppUserDto convertAppUserToDto(AppUser appUser){
        AppUserDto appUserDto=new AppUserDto();
        appUserDto.setUserId(appUser.getUserId());
        appUserDto.setUserRoles(appUser.getUserRoles());
        appUserDto.setUserType(appUser.getUserType());
        appUserDto.setFirstName(appUser.getFirstName());
        appUserDto.setLastName(appUser.getLastName());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setPassword(appUser.getPassword());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setPhone(appUser.getPhone());
        appUserDto.setHouseNumber(appUser.getAddress().getHouseNumber());
        appUserDto.setStreetName(appUser.getAddress().getStreetName());
        appUserDto.setCity(appUser.getAddress().getCity());
        appUserDto.setLandMark(appUser.getAddress().getLandMark());
        appUserDto.setStateProvince(appUser.getAddress().getStateProvince());
        appUserDto.setCountry(appUser.getAddress().getCountry());

        return appUserDto;
    }
}
