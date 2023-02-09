package com.example.restik.controllers;

import com.example.restik.models.comment;
import com.example.restik.models.products;
import com.example.restik.models.role;
import com.example.restik.models.user;
import com.example.restik.repository.commentrepository;
import com.example.restik.repository.productsrepository;
import com.example.restik.repository.userrepository;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.LazyContextVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class maincontroller {

    @Autowired
    private productsrepository productsrepository;

    @Autowired
    private commentrepository commentrepository;
    @Autowired
    private userrepository userrepository;

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

        model.addAttribute("allspecials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return Iterables.concat(productsrepository.findSpecials("rozn"),productsrepository.findSpecials("opt"));
            }
        });

        model.addAttribute("specialsrozn",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });
        model.addAttribute("specialsopt",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });


        Iterable<comment> comments = commentrepository.findAllByOrderByDateDesc();
        List<comment> commentlist = new ArrayList<comment>();
        comments.forEach(commentlist::add);


        for(Iterator<comment> iterator = commentlist.iterator(); iterator.hasNext();){
            comment c = iterator.next();
            if(c.getText()==null){
                iterator.remove();
            }
            else{
                if(c.getText().length()==0) {
                    iterator.remove();
                }
            }
        }

        List<comment> comments3 = new ArrayList<comment>();
        for (int i = 0; i<=2; i++){
            comments3.add(commentlist.get(i));
        }
        model.addAttribute("comments", comments3);


        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "mainpage";
    }

    @GetMapping("/prf")
    //@RequestMapping
    public String profile(TimeZone timezone,  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("username",currentPrincipalName);
        model.addAttribute("avatar",userrepository.findByUsername(currentPrincipalName).getAvatarlink());

        model.addAttribute("fname", userrepository.findByUsername(currentPrincipalName).getFirstname());
        model.addAttribute("sname", userrepository.findByUsername(currentPrincipalName).getSecondname());
        model.addAttribute("mname", userrepository.findByUsername(currentPrincipalName).getMiddlename());

        model.addAttribute("fonum", userrepository.findByUsername(currentPrincipalName).getPhonenumber());
        model.addAttribute("email", userrepository.findByUsername(currentPrincipalName).getEmail());
        model.addAttribute("birthday", userrepository.findByUsername(currentPrincipalName).getBirthday());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);


        model.addAttribute("userrep",userrepository);
        model.addAttribute("newsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));


        Optional<user> oneuser= userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId());
        ArrayList<user> res = new ArrayList<>();

        oneuser.ifPresent(res::add);
        model.addAttribute("oneuser", res);

        return "myprofile";
    }


    @PostMapping("/prf/edit")
    public String avtr(@RequestParam String bday, user user, Model model) throws ParseException {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd",new Locale("ru", "RU"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        model.addAttribute("curusname",currentPrincipalName);


        Optional<user> oneuser= userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId());
        ArrayList<user> res = new ArrayList<>();
        oneuser.ifPresent(res::add);
        model.addAttribute("oneuser",res);

        model.addAttribute("username",currentPrincipalName);
        model.addAttribute("avatar",userrepository.findByUsername(currentPrincipalName).getAvatarlink());

        model.addAttribute("fonum",user.getPhonenumber());
        model.addAttribute("email",user.getEmail());
        if(bday.length()>0)
            model.addAttribute("birthday",df1.parse(bday));

        model.addAttribute("fname",user.getFirstname());
        model.addAttribute("sname",user.getSecondname());
        model.addAttribute("mname",user.getMiddlename());

        if(bday.length()==0){
            model.addAttribute("message","Введите дату рождения");
            return "myprofile";
        }

        LocalDate date1= LocalDate.now().minusYears(18);
        boolean isAfter = date1.isBefore(LocalDate.parse(bday));
        if(isAfter){
            model.addAttribute("message","Сервис доступен только совершеннолетним людям");
            return "myprofile";
        }


        user userFromDbPhone=userrepository.findByPhonenumber(user.getPhonenumber());
        if(userFromDbPhone!=null && ! Objects.equals(userFromDbPhone.getPhonenumber(), user.getPhonenumber())){
            model.addAttribute("message","Данный номер телефона уже занят");
            return "myprofile";
        }

        user userFromDbEmail=userrepository.findByEmail(user.getEmail());
        if(userFromDbEmail!=null && ! Objects.equals(userFromDbEmail.getEmail(), user.getEmail())){
            model.addAttribute("message","Данный адрес электронной почты уже занят");
            return "myprofile";
        }


        if(user.getFirstname().length()==0){
            model.addAttribute("message","Введите свое имя");
            return "myprofile";
        }
        if(user.getSecondname().length()==0){
            model.addAttribute("message","Введите свою фамилию");
            return "myprofile";
        }

        if(user.getPhonenumber().length()==0){
            model.addAttribute("message","Введите номер телефона");
            return "myprofile";
        }
        if(user.getEmail().length()==0){
            model.addAttribute("message","Введите электронную почту");
            return "myprofile";
        }


        Date datebirth = df1.parse(bday);
        user.setBirthday(datebirth);

        user.setId(userrepository.findByUsername(currentPrincipalName).getId());
        user.setUsername(currentPrincipalName);
        user.setActive(true);
        user.setAvatarlink(userrepository.findByUsername(currentPrincipalName).getAvatarlink());
        Set<role> roles=userrepository.findByUsername(currentPrincipalName).getRoles();
        if(Objects.equals(currentPrincipalName, "admin"))
            user.setRoles(Collections.singleton(role.ADMIN));
        else
            user.setRoles(Collections.singleton(role.USER));

        user.setPassword(userrepository.findByUsername(currentPrincipalName).getPassword());

        userrepository.save(user);
        return "redirect:/prf/";
    }

    @GetMapping("/soglasiepersdata")
    public String persdata(Model model) {
        return "soglobrpersdata";
    }

}
