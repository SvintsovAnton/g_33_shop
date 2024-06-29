package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.entity.User;
import de.ait_tr.g_33_shop.exception_handling.Response;
import de.ait_tr.g_33_shop.service.interfaces.ConfirmationService;
import de.ait_tr.g_33_shop.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;


    public RegistrationController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping
   public Response register(@RequestBody User user){
       userService.register(user);
        return new Response("Registration complete.Please check your email");
   }

   @PutMapping
    public Response ConfirmRegistration(@RequestParam("code") String confirmCode){
String messageForResponse = userService.confirmRegistration(confirmCode);
return new Response(messageForResponse);
   }
}
