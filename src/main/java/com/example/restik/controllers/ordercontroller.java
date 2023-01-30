package com.example.restik.controllers;

import com.example.restik.models.cart;
import com.example.restik.models.orders;
import com.example.restik.models.products;
import com.example.restik.models.user;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
@RequestMapping(path="/order/")
public class ordercontroller {
    @Autowired
    private com.example.restik.repository.productsrepository productsrepository;
    @Autowired
    private com.example.restik.repository.userrepository userrepository;
    @Autowired
    private com.example.restik.repository.commentrepository commentrepository;

    @Autowired
    private com.example.restik.repository.cartrepository cartrepository;

    @Autowired
    private com.example.restik.repository.orderrepository orderrepository;


    @GetMapping(path="/")
    public String order(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

        if(Objects.equals(currentPrincipalName, "admin")) {
            Iterable<orders> listorder = orderrepository.findAllByOrderByDateDesc();
            model.addAttribute("orders", listorder);
        }
        else{
            Iterable<orders> listorder = orderrepository.findByUser_idOrderByDateDesc(userrepository.findByUsername(currentPrincipalName).getId());
            model.addAttribute("orders", listorder);
        }

        model.addAttribute("cartrep",cartrepository);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);


        model.addAttribute("curusname",currentPrincipalName);
        model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));

        return "order";
    }

    @PostMapping("/addorder")
    public String addorder(@Valid orders orders, BindingResult bindingResult) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        user user = userrepository.findByUsername(currentPrincipalName);
        Iterable<cart> listcart = cartrepository.findByUser_idAndStatus(userrepository.findByUsername(currentPrincipalName),"inCart");
        Iterable<cart> listcartopt = cartrepository.findByUser_idAndStatusAndOptIliRozn(userrepository.findByUsername(currentPrincipalName),"inCart","opt");

        int totaloptprice=0;
        for(cart i: listcartopt)
            totaloptprice = totaloptprice+(i.getProduct().getPrice()*i.getQuantity());

        if(totaloptprice>0 && totaloptprice<6500){
            return "redirect:/cart/";
        }

        if(Objects.equals(orders.getSposobpoluch(), "post") && (orders.getZipcode()==null || orders.getAdress()==null)){
            return "redirect:/cart/";
        }

        int totalprice=0;
        double totalmass=0;
        int postprice=0;
        for(cart i: listcart){
            totalprice = totalprice+(i.getProduct().getPrice()*i.getQuantity());
            totalmass=totalmass+(i.getProduct().getMass()*i.getQuantity())+150;
        }

        if(orders.getZipcode()!=null) {
            try {
                URL url = new URL("https://postprice.ru/engine/russia/api.php?from=101000&to=" + orders.getZipcode() + "&mass=" + totalmass + "&valuation=" + totalprice + "&vat=1");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setUseCaches(true);
                con.connect();
                int status = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream(), "utf-8"));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(response.toString());
                Double postpricedouble = (Double) data_obj.get("pkg");
                postprice = postpricedouble.intValue();
    //            if(postprice==0){
    //                return "redirect:/cart/";
    //            }

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/cart/";
            }
        }


        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        orders.setDate(now);
        orders.setUser(user);
        orders.setStatus("WaitingPayment");
        orders.setTotalPrice(totalprice);
        orders.setTotalMass(totalmass);
        orders.setTotalPostPrice(postprice);

        orderrepository.save(orders);


        for(cart i:listcart){
            i.setId(i.getId());
            i.setProduct(i.getProduct());
            i.setQuantity(i.getQuantity());
            i.setUser(i.getUser());
            i.setStatus("inOrder");
            i.setOrder(orders);
            cartrepository.save(i);
        }

        return "redirect:/order/";
    }

    @PostMapping("/{id}/confirmpayment")
    public String confirmpayment(@Valid orders orders, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<orders> oneorder= orderrepository.findById(id);
        ArrayList<orders> res = new ArrayList<>();
        oneorder.ifPresent(res::add);
        model.addAttribute("oneorder",res);


        orders.setSposobpoluch(orderrepository.findById(orders.getId()).get().getSposobpoluch());
        orders.setZipcode(orderrepository.findById(orders.getId()).get().getZipcode());
        orders.setUser(orderrepository.findById(orders.getId()).get().getUser());
        orders.setTotalPrice(orderrepository.findById(orders.getId()).get().getTotalPrice());
        orders.setTotalMass(orderrepository.findById(orders.getId()).get().getTotalMass());
        orders.setTotalPostPrice(orderrepository.findById(orders.getId()).get().getTotalPostPrice());
        orders.setDate(orderrepository.findById(orders.getId()).get().getDate());


        orders.setStatus("WaitingConfirm");

        orderrepository.save(orders);
        return ("redirect:/order/");
    }

    @PostMapping("/{id}/cancelorder")
    public String cancelorder(@Valid orders orders, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<orders> oneorder= orderrepository.findById(id);
        ArrayList<orders> res = new ArrayList<>();
        oneorder.ifPresent(res::add);
        model.addAttribute("oneorder",res);


        orders.setSposobpoluch(orderrepository.findById(orders.getId()).get().getSposobpoluch());
        orders.setZipcode(orderrepository.findById(orders.getId()).get().getZipcode());
        orders.setUser(orderrepository.findById(orders.getId()).get().getUser());
        orders.setTotalPrice(orderrepository.findById(orders.getId()).get().getTotalPrice());
        orders.setTotalMass(orderrepository.findById(orders.getId()).get().getTotalMass());
        orders.setTotalPostPrice(orderrepository.findById(orders.getId()).get().getTotalPostPrice());
        orders.setDate(orderrepository.findById(orders.getId()).get().getDate());


        orders.setStatus("Canceled");

        orderrepository.save(orders);
        return ("redirect:/order/");
    }

    @PostMapping("/{id}/confirmpaymentadmin")
    public String confirmpaymentadmin(@Valid orders orders, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<orders> oneorder= orderrepository.findById(id);
        ArrayList<orders> res = new ArrayList<>();
        oneorder.ifPresent(res::add);
        model.addAttribute("oneorder",res);


        orders.setSposobpoluch(orderrepository.findById(orders.getId()).get().getSposobpoluch());
        orders.setZipcode(orderrepository.findById(orders.getId()).get().getZipcode());
        orders.setUser(orderrepository.findById(orders.getId()).get().getUser());
        orders.setTotalPrice(orderrepository.findById(orders.getId()).get().getTotalPrice());
        orders.setTotalMass(orderrepository.findById(orders.getId()).get().getTotalMass());
        orders.setTotalPostPrice(orderrepository.findById(orders.getId()).get().getTotalPostPrice());
        orders.setDate(orderrepository.findById(orders.getId()).get().getDate());


        orders.setStatus("Confirmed");

        orderrepository.save(orders);
        return ("redirect:/order/");
    }


    @PostMapping("/{id}/notconfirmpaymentadmin")
    public String notconfirmpaymentadmin(@Valid orders orders, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<orders> oneorder= orderrepository.findById(id);
        ArrayList<orders> res = new ArrayList<>();
        oneorder.ifPresent(res::add);
        model.addAttribute("oneorder",res);


        orders.setSposobpoluch(orderrepository.findById(orders.getId()).get().getSposobpoluch());
        orders.setZipcode(orderrepository.findById(orders.getId()).get().getZipcode());
        orders.setUser(orderrepository.findById(orders.getId()).get().getUser());
        orders.setTotalPrice(orderrepository.findById(orders.getId()).get().getTotalPrice());
        orders.setTotalMass(orderrepository.findById(orders.getId()).get().getTotalMass());
        orders.setTotalPostPrice(orderrepository.findById(orders.getId()).get().getTotalPostPrice());
        orders.setDate(orderrepository.findById(orders.getId()).get().getDate());


        orders.setStatus("notConfirmed");

        orderrepository.save(orders);
        return ("redirect:/order/");
    }


    @PostMapping("/{id}/finish")
    public String finish(@Valid orders orders, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<orders> oneorder= orderrepository.findById(id);
        ArrayList<orders> res = new ArrayList<>();
        oneorder.ifPresent(res::add);
        model.addAttribute("oneorder",res);


        orders.setSposobpoluch(orderrepository.findById(orders.getId()).get().getSposobpoluch());
        orders.setZipcode(orderrepository.findById(orders.getId()).get().getZipcode());
        orders.setUser(orderrepository.findById(orders.getId()).get().getUser());
        orders.setTotalPrice(orderrepository.findById(orders.getId()).get().getTotalPrice());
        orders.setTotalMass(orderrepository.findById(orders.getId()).get().getTotalMass());
        orders.setTotalPostPrice(orderrepository.findById(orders.getId()).get().getTotalPostPrice());
        orders.setDate(orderrepository.findById(orders.getId()).get().getDate());


        orders.setStatus("Finished");

        orderrepository.save(orders);
        int quan=0;

        for(cart c:cartrepository.findByOrder_id(orders.getId())){
            products myproduct = c.getProduct();
            myproduct.setQuantity(myproduct.getQuantity()-c.getQuantity());
            productsrepository.save(myproduct);
        }

        return ("redirect:/order/");
    }

}
