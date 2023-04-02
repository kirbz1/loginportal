package com.example.LoginSystem;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class LoginController {

    @Autowired
    private LoginService service;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Account> accountlist = service.listAll();
        model.addAttribute("accountlist", accountlist);
        System.out.print("Get / ");
        return "index";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("account", new Account());
        return "new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveAccount(@ModelAttribute("account") Account account) {
        service.save(account);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditAccountPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("new");
        Account account = service.get(id);
        mav.addObject("account", account);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteAccount(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }

}
