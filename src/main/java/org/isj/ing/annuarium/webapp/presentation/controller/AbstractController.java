package org.isj.ing.annuarium.webapp.presentation.controller;

import org.isj.ing.annuarium.webapp.model.entities.User;
import org.isj.ing.annuarium.webapp.service.IActe;
import org.isj.ing.annuarium.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
@Controller
public class AbstractController {

    @Autowired
    IActe iActe;
    @Autowired
    UserService userService;

    public void getInfoUser(Model model) {
        //appel de la couche service pour avoir l'acte
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findUserByEmail(auth.getName());
        if(user!= null )
            model.addAttribute("userName", user.getName() + " " + user.getLastName());
        else
            model.addAttribute("");
    }

}
