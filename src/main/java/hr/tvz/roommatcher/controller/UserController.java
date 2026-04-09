package hr.tvz.roommatcher.controller;

import hr.tvz.roommatcher.dto.UserDTO;
import hr.tvz.roommatcher.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        Optional<UserDTO> findTaskById = userService.findById(id);

        if(findTaskById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(findTaskById.get());
        }

    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe() {
        Optional<UserDTO> me = userService.findMe();

        if (me.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(me.get());
        }
    }


}


