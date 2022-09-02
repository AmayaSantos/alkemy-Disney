package disney.api.controllers;

import disney.api.payload.request.LoginRequest;
import disney.api.payload.request.RegisterRequest;
import disney.api.payload.response.JwtResponse;
import disney.api.payload.response.MessageResponse;
import disney.api.security.jwt.JwtUtils;
import disney.api.services.UserDetailsImpl;
import disney.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class SessionController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserService userService;

  @Autowired JwtUtils jwtUtils;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
    if (userService.existsUser(registerRequest.getUsername(), registerRequest.getEmail())) {
      String repeatCredential =
          userService.whoExistsUser(registerRequest.getUsername(), registerRequest.getEmail());
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: User " + repeatCredential + " is already taken!"));
    }
    userService.register(
        registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new JwtResponse(
            jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/user/{id_user}/role/{id_role}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addRoleToUser(
      @PathVariable Long id_user, @PathVariable Integer id_role) {
    try {
      userService.setRole(id_user, id_role);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
    return ResponseEntity.ok(new MessageResponse("User ROLE asigned!!"));
  }

  @GetMapping("/{id_user}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> searchUser(@PathVariable Long id_user) {
    try {
      return ResponseEntity.ok(userService.getUser(id_user));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/updatePass")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> changePass(
      @RequestParam("pass") String pass,
      @RequestParam("repass") String repass,
      @RequestParam("oldpass") String oldpass) {
    try {
      userService.changePass(
          SecurityContextHolder.getContext().getAuthentication().getName(), pass, repass, oldpass);
      return ResponseEntity.ok().body("Susccesfull Pass Cahanged");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }
}
