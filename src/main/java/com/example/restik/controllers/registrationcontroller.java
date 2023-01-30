package com.example.restik.controllers;


import com.example.restik.models.role;
import com.example.restik.models.user;
import com.example.restik.repository.userrepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class registrationcontroller {
    @Autowired
    userrepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(Model model){
//        LocalDate date1= LocalDate.now().minusYears(18);
//        Date date2= Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        model.addAttribute("maxdate", date2);
        model.addAttribute("fonum","+7(___)___-__-__");
        return "registration";
    }

    public static boolean isValid(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }
    @PostMapping("/registration")
    public String newuser(@RequestParam String password2, @RequestParam String bday,
                          user user, @RequestParam String captcha, Model model) throws ParseException, IOException {

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd",new Locale("ru", "RU"));

        model.addAttribute("fname",user.getFirstname());
        model.addAttribute("sname",user.getSecondname());
        model.addAttribute("mname",user.getMiddlename());

        model.addAttribute("fonum",user.getPhonenumber());
        model.addAttribute("email",user.getEmail());
        if(bday.length()>0)
            model.addAttribute("birthday",df1.parse(bday));
//////////////////////////////////////////////////////////////////////////////////////////
//
//
//        URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json; utf-8");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setDoOutput(true);
//        conn.setUseCaches(true);
//
//
//        String jsonInputString = "secret=6LfBiSgkAAAAAFiHoY4Av9petNH5sfhGYahu4bMG&response=" + captcha;
//
//
//        try (OutputStream os = conn.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
//            StringBuilder sb = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                sb.append(responseLine.trim());
//            }
//            JSONParser parse = new JSONParser();
//            JSONObject data_obj = (JSONObject) parse.parse(sb.toString());
//
//            if(! (boolean) data_obj.get("success")){
//                model.addAttribute("message","ReCaptcha не пройден");
//                model.addAttribute("usern",user.getUsername());
//                model.addAttribute("userp",user.getPassword());
//
//                return "registration";
//            }
//
//        }
//        catch (Exception e) {
//            model.addAttribute("message","Ошибка ReCaptcha");
//            model.addAttribute("usern",user.getUsername());
//            model.addAttribute("userp",user.getPassword());
//            e.printStackTrace();
//
//            return "registration";
//        }


        user userFromDb=userRepository.findByUsername(user.getUsername());
        if(userFromDb!=null|| Objects.equals(user.getUsername(), "anonymousUser")){
            model.addAttribute("message","Данный логин уже занят");
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            return "registration";
        }

        user userFromDbPhone=userRepository.findByPhonenumber(user.getPhonenumber());
        if(userFromDbPhone!=null){
            model.addAttribute("message","Данный номер телефона уже занят");
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            return "registration";
        }

        user userFromDbEmail=userRepository.findByEmail(user.getEmail());
        if(userFromDbEmail!=null){
            model.addAttribute("message","Данный адрес электронной почты уже занят");
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            return "registration";
        }

        if(!isValid(user.getUsername())){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","В логине недопустима кириллица");
            return "registration";
        }

        if(bday.length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите дату рождения");
            return "registration";
        }


        LocalDate date1= LocalDate.now().minusYears(18);
        boolean isAfter = date1.isBefore(LocalDate.parse(bday));

        if(isAfter){

            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Регистрация доступна только совершеннолетним людям");
            return "registration";
        }

        if(user.getUsername().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите логин");
            return "registration";
        }

        if(user.getFirstname().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите свое имя");
            return "registration";
        }
        if(user.getSecondname().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите свою фамилию");
            return "registration";
        }

        if(user.getPhonenumber().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите номер телефона");
            return "registration";
        }
        if(user.getEmail().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите электронную почту");
            return "registration";
        }

        if(!Objects.equals(user.getPassword(), password2)){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Пароли не совпадают");
            return "registration";
        }

        if(user.getPassword().length()<8){
            model.addAttribute("usern",user.getUsername());

            model.addAttribute("message","Придумайте пароль длиной минимум 8 символов");
            return "registration";
        }


        Date datebirth = df1.parse(bday);
        user.setBirthday(datebirth);
        user.setActive(true);
        user.setAvatarlink("https://i.imgur.com/b9Kgwzn.png");
        user.setRoles(Collections.singleton(role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "redirect:/login";
    }

}
