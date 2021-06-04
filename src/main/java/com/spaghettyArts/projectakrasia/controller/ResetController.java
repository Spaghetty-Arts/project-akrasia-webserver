package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.ResetFormModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * O controller das rotas associadas ao reset da password na Web
 * @author Fabian Nunes
 * @version 0.1
 */
@Controller
public class ResetController {

    @Autowired
    private UserService service;


    /**
     * A função que irá receber o GET Request do browser para renderizar a página de reset da password com os dados especificos
     * @param token O token associado ao pedido do reset de password que é necessário para identificar o pedido
     * @param model O model que irá receber os dados para serem apresentado no Thymeleaf do Spring
     * @return Irá retornar uma string  que é o nome da página html que queremos abrir
     * @author Fabian Nunes
     */
    @GetMapping("/resetPassword")
    public String greetingForm(@RequestParam(value = "token") String token, Model model) {
        model.addAttribute("reset", new ResetFormModel());
        model.addAttribute("token", token);
        return "resetPassword";
    }

    /**
     * A função que irá receber o POST Request do browser com os dados inseridos no form
     * @param token O token associado ao pedido do reset de password que é necessário para identificar o pedido
     * @param reset Os dados do form preenchido que serão o email e a password
     * @param model O model que irá receber os dados para serem apresentado no Thymeleaf do Spring
     * @return Irá retornar uma string  que é o nome da página html que queremos abrir
     * @author Fabian Nunes
     */
    @PostMapping("/resetPassword")
    public String greetingSubmit(@RequestParam(value = "token") String token, @ModelAttribute ResetFormModel reset, Model model) {
        UserModel user = service.reset(token, reset.getEmail(), reset.getPassword());
        if(user ==  null) {
            return "error";
        }
        model.addAttribute("reset", reset);
        return "result";
    }
}
