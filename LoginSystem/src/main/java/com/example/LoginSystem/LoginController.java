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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    private Account current_user;
    private boolean logged_in;

    @Autowired
    private LoginService service;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Account> accountlist = service.listAll();
        model.addAttribute("accountlist", accountlist);
        if (this.logged_in) {
            model.addAttribute("logged_in", true);
            model.addAttribute("current_user", current_user);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("current_user", new Account());
        return "login";
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String attemptLogin(@ModelAttribute("current_user") Account account, final RedirectAttributes redirectAttributes) {
        //find and verify account exists in repo
        //handle negative case - doesn't exist
        //positive case - create new signed in user element and go to home page?
        redirectAttributes.addFlashAttribute("logged_in", true);
		redirectAttributes.addFlashAttribute("current_user", account);
        this.logged_in = true;
        this.current_user = account;
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
