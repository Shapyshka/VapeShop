package com.example.restik.controllers;

import com.example.restik.models.cart;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Controller
@RequestMapping(path="/cart/")
public class cartcontroller {
    @Autowired
    private com.example.restik.repository.productsrepository productsrepository;
    @Autowired
    private com.example.restik.repository.userrepository userrepository;
    @Autowired
    private com.example.restik.repository.commentrepository commentrepository;
    @Autowired
    private com.example.restik.repository.cartrepository cartrepository;


    @GetMapping(path="/")
    public String cart(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();


        Iterable<cart> listcart = cartrepository.findByUser_idAndStatus(userrepository.findByUsername(currentPrincipalName),"inCart");
        model.addAttribute("cartproducts",listcart);

        Iterable<cart> listcartrozn = cartrepository.findByUser_idAndStatusAndOptIliRozn(userrepository.findByUsername(currentPrincipalName),"inCart","rozn");
        model.addAttribute("cartproductsRozn",listcartrozn);

        Iterable<cart> listcartopt = cartrepository.findByUser_idAndStatusAndOptIliRozn(userrepository.findByUsername(currentPrincipalName),"inCart","opt");
        model.addAttribute("cartproductsOpt",listcartopt);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);


        model.addAttribute("postprice",null);
        model.addAttribute("zip",null);

        return "cart";
    }

    @PostMapping("/{id}/del")
    public String delcart(@PathVariable("id") Long id, Model model){

        cart cart = cartrepository.findById(id).orElseThrow();
        cartrepository.delete(cart);
        return ("redirect:/cart/");
    }

    @PostMapping("/{id}/edit")
    public String editcart(@Valid cart cart, BindingResult bindingResult, @PathVariable("id") Long id, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<cart> onecart= cartrepository.findById(id);
        ArrayList<cart> res = new ArrayList<>();
        onecart.ifPresent(res::add);
        model.addAttribute("onecart",res);
        cart.setProduct(res.get(0).getProduct());
        cart.setUser(res.get(0).getUser());
        cart.setStatus("inCart");
        cart.setOptIliRozn(res.get(0).getOptIliRozn());

        cartrepository.save(cart);

        return ("redirect:/cart/");
    }

    @GetMapping("/count/{zip}/{mass}/{price}/{adress}")
    public String count(@PathVariable("zip") int zip,
                        @PathVariable("mass") int mass,
                        @PathVariable("price") int price,
                        @PathVariable("adress") String adress,
                        Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();


        Iterable<cart> listcart = cartrepository.findByUser_idAndStatus(userrepository.findByUsername(currentPrincipalName),"inCart");
        model.addAttribute("cartproducts",listcart);

        Iterable<cart> listcartrozn = cartrepository.findByUser_idAndStatusAndOptIliRozn(userrepository.findByUsername(currentPrincipalName),"inCart","rozn");
        model.addAttribute("cartproductsRozn",listcartrozn);

        Iterable<cart> listcartopt = cartrepository.findByUser_idAndStatusAndOptIliRozn(userrepository.findByUsername(currentPrincipalName),"inCart","opt");
        model.addAttribute("cartproductsOpt",listcartopt);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);


        model.addAttribute("zip",zip);
        model.addAttribute("adressatercount",adress);


        mass = mass+150;
        try {
                URL url = new URL("https://postprice.ru/engine/russia/api.php?from=101000&to=" + zip + "&mass=" + mass + "&valuation=" + price + "&vat=1");
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
                model.addAttribute("postprice",postpricedouble.intValue());



        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("postprice","Введен неверный индекс");
            return "cart";
        }



        return ("cart");
    }

}
