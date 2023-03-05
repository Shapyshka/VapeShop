package com.example.restik.controllers;

import com.example.restik.models.*;
import com.example.restik.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.LazyContextVariable;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/products/") // This means URL's start with /demo (after Application path)
public class productscontroller {

    @Autowired
    private productsrepository productsrepository;
    @Autowired
    private userrepository userrepository;
    @Autowired
    private commentrepository commentrepository;

    @Autowired
    private cartrepository cartrepository;


    @GetMapping(path="/")
    public String homeproducts(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("rozn");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });


        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });


        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsList";
    }
    @GetMapping(path="/opt")
    public String opt(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

//        Iterable<products> listproducts = productsrepository.findByOptIliRoznOrderByDateDesc("opt");
//        model.addAttribute("products",listproducts);
//
//        Iterable<products> listproducts1 = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",Sort.by(Sort.Direction.DESC, "date"));
//        model.addAttribute("productstype1",listproducts1);
//
//        Iterable<products> listproducts2 = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt", Sort.by(Sort.Direction.DESC, "date"));
//        model.addAttribute("productstype2",listproducts2);
//
//        Iterable<products> listproducts3 = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",Sort.by(Sort.Direction.DESC, "date"));
//        model.addAttribute("productstype3",listproducts3);
//
//        Iterable<products> listproducts4 = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt", Sort.by(Sort.Direction.DESC, "date"));
//        model.addAttribute("productstype4",listproducts4);
//
//        Iterable<products> listproducts5 = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt", Sort.by(Sort.Direction.DESC, "date"));
//        model.addAttribute("productstype5",listproducts5);
//
//        Iterable<products> listspecials = productsrepository.findSpecials("opt");
//        model.addAttribute("specials",listspecials);


        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("opt");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });



        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        //model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));
        return "productsList";
    }

    @GetMapping("/add")
    public String addnews(products products, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);
        if(Objects.equals(currentPrincipalName, "admin"))
            return "productsAdd";
        else
            return "redirect:/products/";
    }

    @PostMapping("/add")
    public String saveproducts(@Valid products products, BindingResult bindingResult) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(bindingResult.hasErrors())
            return "productsAdd";
//        products.setAuthor(userrepository.findByUsername(currentPrincipalName));

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        products.setDate(now);

        productsrepository.save(products);

        return "redirect:/products/";
    }

    @GetMapping("/{id}")
//    @RequestMapping
    public String viewproducts(@PathVariable("id") Long id, TimeZone timezone, Model model){

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Optional<products> oneproducts= productsrepository.findById(id);
        ArrayList<products> res = new ArrayList<>();
        oneproducts.ifPresent(res::add);
        model.addAttribute("products",res);

        if(res.get(0).getColors()!=null)
            model.addAttribute("colorarray",res.get(0).getColors().split(";"));

        Iterable<comment> listcomments = commentrepository.findByZapis_idOrderByDateDesc(id);
        model.addAttribute("comments",listcomments);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);
        model.addAttribute("cartrep",cartrepository);


        model.addAttribute("curusname",currentPrincipalName);

        return "productsView";
    }

    @GetMapping("/{id}/edit")
    public String editview(@PathVariable("id")Long id, products products, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        if(Objects.equals(currentPrincipalName, "admin")) {
            Optional<products> oneproducts = productsrepository.findById(id);
            ArrayList<products> res = new ArrayList<>();

//        if(!Objects.equals(oneproducts.get().getAuthorName(), currentPrincipalName))
//            return "redirect:/products/"+id.toString();

            oneproducts.ifPresent(res::add);
            model.addAttribute("onenew", res);
            return "productsEdit";
        }
        else return "redirect:/products/";
    }

    @PostMapping("/{id}/edit")
    public String editview(@Valid products products, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<products> oneproducts= productsrepository.findById(id);
        ArrayList<products> res = new ArrayList<>();
        oneproducts.ifPresent(res::add);
        model.addAttribute("onenew",res);
//        products.setAuthor(userrepository.findByUsername(currentPrincipalName));
        products.setDate(productsrepository.findById(products.getId()).get().getDate());
        if(bindingResult.hasErrors()) {
            return "productsEdit";
        }

        productsrepository.save(products);
        return ("redirect:/products/");
    }
    @PostMapping("/{id}/comment")
    public String comment(@Valid comment comment, BindingResult bindingResult, @PathVariable("id") Long id) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if(bindingResult.hasErrors())
            return "productsView";
        comment.setAuthor(userrepository.findByUsername(currentPrincipalName));
        comment.setZapis(productsrepository.findById(id).orElseThrow());
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        comment.setDate(now);
        commentrepository.save(comment);
        return "redirect:/products/"+id;
    }

    @PostMapping("/comment/{id}/del")
    public String delcomment(@PathVariable("id") Long id, Model model) throws ParseException {
        comment comment = commentrepository.findById(id).orElseThrow();
        Long prodid = comment.getZapis().getId();
        commentrepository.delete(comment);
        return ("redirect:/products/"+prodid.toString());
    }

    @PostMapping("/{id}/del")
    public String delproducts(@PathVariable("id") Long id, Model model){
        Iterable<comment> comments= commentrepository.findByZapis_idOrderByDateDesc(id);
        for(comment com:comments)
            commentrepository.delete(com);

        Iterable<cart> cartList= cartrepository.findByProduct_id(id);
        for(cart cart:cartList)
            cartrepository.delete(cart);

        products products = productsrepository.findById(id).orElseThrow();
        productsrepository.delete(products);
        return ("redirect:/products/");
    }

    @PostMapping("/{id}/addcart")
    @ResponseStatus(value = HttpStatus.OK)
    public void addcart(@PathVariable("id") Long id, @Valid cart cart, BindingResult bindingResult) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        user user = userrepository.findByUsername(currentPrincipalName);
        cart.setUser(user);
        cart.setProduct(productsrepository.findById(id).get());
        cart.setStatus("inCart");
        cart.setOptIliRozn(productsrepository.findById(id).get().getOptIliRozn());
        cartrepository.save(cart);

    }

    @GetMapping(path="/sort/priceasc")
    public String homeproductssortasc(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

//        Iterable<products> listproducts = productsrepository.findByOptIliRoznOrderByDateDesc("rozn");
//        model.addAttribute("products",listproducts);
//
//        Iterable<products> listproducts1 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("odnoraz","rozn",Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype1",listproducts1);
//
//        Iterable<products> listproducts2 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("jija","rozn", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype2",listproducts2);
//
//        Iterable<products> listproducts3 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("cartridge","rozn",Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype3",listproducts3);
//
//        Iterable<products> listproducts4 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("ispar","rozn", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype4",listproducts4);
//
//        Iterable<products> listproducts5 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("pod","rozn", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype5",listproducts5);
//
//        Iterable<products> listspecials = productsrepository.findSpecials("aboba");
//        model.addAttribute("specials",listspecials);


        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("rozn");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("aboba");
            }
        });



        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsList";
    }

    @GetMapping(path="/sort/pricedesc")
    public String homeproductssortdesc(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

//        Iterable<products> listproducts = productsrepository.findByOptIliRoznOrderByDateDesc("rozn");
//        model.addAttribute("products",listproducts);
//
//        Iterable<products> listproducts1 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("odnoraz","rozn",Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype1",listproducts1);
//
//        Iterable<products> listproducts2 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("jija","rozn", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype2",listproducts2);
//
//        Iterable<products> listproducts3 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("cartridge","rozn",Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype3",listproducts3);
//
//        Iterable<products> listproducts4 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("ispar","rozn", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype4",listproducts4);
//
//        Iterable<products> listproducts5 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("pod","rozn", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype5",listproducts5);
//
//        Iterable<products> listspecials = productsrepository.findSpecials("aboba");
//        model.addAttribute("specials",listspecials);
//

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("rozn");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("aboba");
            }
        });


        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsList";
    }


    @GetMapping(path="/opt/sort/priceasc")
    public String optsortasc(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

//        Iterable<products> listproducts = productsrepository.findByOptIliRoznOrderByDateDesc("opt");
//        model.addAttribute("products",listproducts);
//
//        Iterable<products> listproducts1 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("odnoraz","opt",Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype1",listproducts1);
//
//        Iterable<products> listproducts2 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("jija","opt", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype2",listproducts2);
//
//        Iterable<products> listproducts3 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("cartridge","opt",Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype3",listproducts3);
//
//        Iterable<products> listproducts4 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("ispar","opt", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype4",listproducts4);
//
//        Iterable<products> listproducts5 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("pod","opt", Sort.by(Sort.Direction.ASC, "price"));
//        model.addAttribute("productstype5",listproducts5);
//
//        Iterable<products> listspecials = productsrepository.findSpecials("aboba");
//        model.addAttribute("specials",listspecials);


        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("opt");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                        Sort.by(Sort.Direction.ASC, "price"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("aboba");
            }
        });



        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsList";
    }

    @GetMapping(path="/opt/sort/pricedesc")
    public String optsortdesc(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

//        Iterable<products> listproducts = productsrepository.findByOptIliRoznOrderByDateDesc("opt");
//        model.addAttribute("products",listproducts);
//
//        Iterable<products> listproducts1 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("odnoraz","opt",Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype1",listproducts1);
//
//        Iterable<products> listproducts2 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("jija","opt", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype2",listproducts2);
//
//        Iterable<products> listproducts3 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("cartridge","opt",Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype3",listproducts3);
//
//        Iterable<products> listproducts4 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceDesc("ispar","opt", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype4",listproducts4);
//
//        Iterable<products> listproducts5 = productsrepository.findByTypeofproductAndOptIliRoznSortByPriceAsc("pod","opt", Sort.by(Sort.Direction.DESC, "price"));
//        model.addAttribute("productstype5",listproducts5);
//
//        Iterable<products> listspecials = productsrepository.findSpecials("aboba");
//        model.addAttribute("specials",listspecials);



        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByOptIliRoznOrderByDateDesc("opt");
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                        Sort.by(Sort.Direction.DESC, "price"));
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("aboba");
            }
        });


        model.addAttribute("opt",true);


        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsList";
    }


}
