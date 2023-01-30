package com.example.restik.controllers;

import com.example.restik.models.user;
import com.example.restik.repository.commentrepository;
import com.example.restik.repository.productsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping(path="/users/")
public class userscontroller {


    @Autowired
    private userrepository userrepository;
    @Autowired
    private productsrepository productsrepository;


    @Autowired
    private commentrepository commentrepository;

    @GetMapping(path = "/")
    public String homeusers(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);
        if(Objects.equals(currentPrincipalName, "admin"))
        {
            List<user> listusers = userrepository.findAll();
            Collections.reverse(listusers);
            model.addAttribute("users", listusers);

            return "userlist";
        }
        else return "redirect:/products/";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") String usern, TimeZone timezone, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);
        if(Objects.equals(currentPrincipalName, "admin")) {
            String username = userrepository.findByUsername(usern).getUsername();
            if (Objects.equals(username, currentPrincipalName))
                return "redirect:/prf";

            model.addAttribute("username", username);
            model.addAttribute("avatar", userrepository.findByUsername(username).getAvatarlink());

            model.addAttribute("firstname", userrepository.findByUsername(username).getFirstname());
            model.addAttribute("secondname", userrepository.findByUsername(username).getSecondname());
            model.addAttribute("middlename", userrepository.findByUsername(username).getMiddlename());

            model.addAttribute("fonum", userrepository.findByUsername(username).getPhonenumber());
            model.addAttribute("email", userrepository.findByUsername(username).getEmail());
            model.addAttribute("birthday", userrepository.findByUsername(username).getBirthday());

            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("ru", "RU"));
            df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
            model.addAttribute("df1", df1);

            SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("ru", "RU"));
            df2.setTimeZone(timezone);
            model.addAttribute("df2", df2);

            Long userid = userrepository.findByUsername(usern).getId();


            model.addAttribute("userrep", userrepository);
            model.addAttribute("newsrep", productsrepository);
            model.addAttribute("commrep", commentrepository);

            model.addAttribute("curuserid", userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));

            return "userprofile";
        }
        else return "redirect:/products/";

    }
}