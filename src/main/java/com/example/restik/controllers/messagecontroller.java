package com.example.restik.controllers;

import com.example.restik.models.message;
import com.example.restik.repository.messagerepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

@Controller // This means that this class is a Controller
@RequestMapping(path="/msg/") // This means URL's start with /demo (after Application path)
public class messagecontroller {


    @Autowired
    private userrepository userrepository;

    @Autowired
    private messagerepository messagerepository;


    @GetMapping(path = "/")
    //@RequestMapping
    public String msg(TimeZone timezone, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        if(!Objects.equals(currentPrincipalName, "admin")) {
            return "redirect:/msg/admin";
        }

        Long curuserid = userrepository.findByUsername(currentPrincipalName).getId();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Iterable<message> listmsg = messagerepository.findByFrom_idOrTo_idOrderByDateDesc(curuserid,curuserid);
        //ArrayList<Long> list = new ArrayList<>();
        Map<String,Long> map = new TreeMap<String,Long>();

        for(message m:listmsg){
            if(Objects.equals(m.getFrom().getId(), curuserid)){
                if(!map.containsKey(m.getTo().getUsername()))
                    map.put(m.getTo().getUsername(),m.getId());
            }

            else if(Objects.equals(m.getTo().getId(), curuserid)){
                if(!map.containsKey(m.getFrom().getUsername()))
                    map.put(m.getFrom().getUsername(),m.getId());
            }
        }

        Map<Long,String> map2 = new TreeMap<Long, String>(Collections.reverseOrder());
        for(Map.Entry<String, Long> entry : map.entrySet()){
            map2.put(entry.getValue(), entry.getKey());
        }

        model.addAttribute("talkto", map2);
        model.addAttribute("userrep", userrepository);
        model.addAttribute("mesrep", messagerepository);

        return "messages";
    }


    @GetMapping(path = "/{id}")
    //@RequestMapping
    public String msgwithuser(@PathVariable("id") String msgwith, TimeZone timezone, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        if((!Objects.equals(currentPrincipalName, "admin"))&&(!Objects.equals(msgwith, "admin"))) {
            return "redirect:/msg/admin";
        }

        Long curuserid = userrepository.findByUsername(currentPrincipalName).getId();
        Long hisid = userrepository.findByUsername(msgwith).getId();

        model.addAttribute("usrnm",msgwith);

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        List<message> listmsg1 = messagerepository.findFromMeToDude(userrepository.findById(curuserid), userrepository.findById(hisid), Sort.by(Sort.Direction.DESC, "date"));
        List<message> listmsg2 = messagerepository.findToMeFromDude(userrepository.findById(curuserid), userrepository.findById(hisid), Sort.by(Sort.Direction.DESC, "date"));
        List<message> allmsgs = new ArrayList<>(Stream.concat(listmsg1.stream(), listmsg2.stream()).toList());

        //allmsgs.sort(Collections.reverseOrder());
        Collections.sort(allmsgs);
        model.addAttribute("msgs",allmsgs);
        model.addAttribute("userrep",userrepository);

        return "messagesView";

    }
    @PostMapping("/{id}/sndmsg")
    public ModelAndView sendmsg(@Valid message message, BindingResult bindingResult, @PathVariable("id") String id) throws ParseException, UnsupportedEncodingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

//        if(bindingResult.hasErrors())
//            return "redirect:/msg/"+id;

        message.setFrom(userrepository.findByUsername(currentPrincipalName));
        message.setTo(userrepository.findByUsername(id));

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        message.setDate(now);

        messagerepository.save(message);

        String encodedId = URLEncoder.encode(id, "UTF-8");
        return new ModelAndView(new RedirectView("/msg/" + encodedId, true, true, false));
    }
}