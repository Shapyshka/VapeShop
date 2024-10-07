package com.example.restik.controllers;

import com.example.restik.models.*;
import com.example.restik.repository.*;
import com.google.common.collect.Iterables;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Autowired
    private com.example.restik.repository.orderrepository orderrepository;



    @GetMapping(path="/")
    public String homeproducts(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);


        List<products> productstype1 = new ArrayList<products>();
        Iterator<products> productsIterator1 = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator1.hasNext())
                productstype1.add(productsIterator1.next());
        }

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype1;
            }
        });

        List<products> productstype2 = new ArrayList<products>();
        Iterator<products> productsIterator2 = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator2.hasNext())
                productstype2.add(productsIterator2.next());
        }

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype2;
            }
        });

        List<products> productstype3 = new ArrayList<products>();
        Iterator<products> productsIterator3 = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator3.hasNext())
                productstype3.add(productsIterator3.next());
        }
        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype3;
            }
        });

        List<products> productstype4 = new ArrayList<products>();
        Iterator<products> productsIterator4 = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator4.hasNext())
                productstype4.add(productsIterator4.next());
        }
        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype4;
            }
        });

        List<products> productstype5 = new ArrayList<products>();
        Iterator<products> productsIterator5 = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator5.hasNext())
                productstype5.add(productsIterator5.next());
        }
        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype5;
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });

        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return Iterables.concat(odnoraz,jija,cartridge,ispar,pod);
            }
        });

        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("opt",false);

        model.addAttribute("mainpage",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        return "productsMain";
    }
    //////////////////////


    @GetMapping(path="/odnoraz")
    public String odnoraz(Model model) throws ParseException {
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
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });



        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });

        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());

        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","odnoraz");

        return "productsList";
    }

    @GetMapping(path="/jija")
    public String jija(Model model) throws ParseException {
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
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
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


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());



        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","jija");

        return "productsList";
    }

    @GetMapping(path="/pod")
    public String pod(Model model) throws ParseException {
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
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
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


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","pod");

        return "productsList";
    }

    @GetMapping(path="/ispar")
    public String ispar(Model model) throws ParseException {
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
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
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


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","ispar");

        return "productsList";
    }

    @GetMapping(path="/cartridge")
    public String cartridge(Model model) throws ParseException {
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
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
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


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("rozn");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());



        model.addAttribute("opt",false);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","cartridge");

        return "productsList";
    }


    ///////////////////////

    @GetMapping(path="/opt")
    public String opt(Model model) throws ParseException {

//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);



        List<products> productstype1 = new ArrayList<products>();
        Iterator<products> productsIterator1 = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator1.hasNext())
                productstype1.add(productsIterator1.next());
        }

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype1;
            }
        });

        List<products> productstype2 = new ArrayList<products>();
        Iterator<products> productsIterator2 = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator2.hasNext())
                productstype2.add(productsIterator2.next());
        }

        model.addAttribute("productstype2",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype2;
            }
        });

        List<products> productstype3 = new ArrayList<products>();
        Iterator<products> productsIterator3 = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator3.hasNext())
                productstype3.add(productsIterator3.next());
        }
        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype3;
            }
        });

        List<products> productstype4 = new ArrayList<products>();
        Iterator<products> productsIterator4 = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator4.hasNext())
                productstype4.add(productsIterator4.next());
        }
        model.addAttribute("productstype4",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype4;
            }
        });

        List<products> productstype5 = new ArrayList<products>();
        Iterator<products> productsIterator5 = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date")).iterator();
        for(int i=0; i<10; i++){
            if(productsIterator5.hasNext())
                productstype5.add(productsIterator5.next());
        }
        model.addAttribute("productstype5",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productstype5;
            }
        });

        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });

        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return Iterables.concat(odnoraz,jija,cartridge,ispar,pod);
            }
        });


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());



        model.addAttribute("opt",true);
        model.addAttribute("mainpage",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);
        //model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));
        return "productsMain";
    }


    /////////////////////////////////



    @GetMapping(path="/opt/odnoraz")
    public String optodnoraz(Model model) throws ParseException {

//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

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
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("productstype1",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });



        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });

        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());

        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","odnoraz");


        return "productsList";
    }

    @GetMapping(path="/opt/jija")
    public String optjija(Model model) throws ParseException {

//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

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
                return productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
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


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());



        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","jija");

        return "productsList";
    }

    @GetMapping(path="/opt/pod")
    public String optpod(Model model) throws ParseException {


//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

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
                return productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
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


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","pod");

        return "productsList";
    }

    @GetMapping(path="/opt/ispar")
    public String optispar(Model model) throws ParseException {


//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

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
                return productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
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


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","ispar");

        return "productsList";
    }

    @GetMapping(path="/opt/cartridge")
    public String optcartridge(Model model) throws ParseException {


//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);


        model.addAttribute("productstype3",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });


        model.addAttribute("specials",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findSpecials("opt");
            }
        });


        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                        Sort.by(Sort.Direction.DESC, "date"));
            }
        });

        model.addAttribute("opt",true);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("descparam","cartridge");

        return "productsList";
    }


    //////////////////////////////

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

//        if(Objects.equals(oneproducts.get().getOptIliRozn(), "opt"))
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

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



    @GetMapping("/{id}/fastorder")
    public String fastorderget(@PathVariable("id")Long id, products products, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        if((productsrepository.findById(id).get().getQuantity()==0) ||
                Objects.equals(productsrepository.findById(id).get().getOptIliRozn(), "opt")){
            return "redirect:/products/"+id.toString();
        }


        Optional<products> oneproducts = productsrepository.findById(id);
        ArrayList<products> res = new ArrayList<>();
        oneproducts.ifPresent(res::add);
        model.addAttribute("oneprod", res);

        model.addAttribute("fonum","+7(___)___-__-__");

        return "fastorder";
    }


    @PostMapping("/{id}/fastorder")
    public String fastorder(@PathVariable("id") Long id, @Valid orders orders, BindingResult bindingResult, Model model, TimeZone timezone) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if((productsrepository.findById(id).get().getQuantity()==0) ||
                Objects.equals(productsrepository.findById(id).get().getOptIliRozn(), "opt")){
            return "redirect:/products/"+id.toString();
        }

        int postprice=0;
        if (Objects.equals(orders.getSposobpoluch(), "dost")) {
            postprice = 300;
        }

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        orders.setDate(now);

        orders.setStatus("Confirmed");

        orders.setFasttovar(productsrepository.findById(id).get());

        orders.setTotalPrice(productsrepository.findById(id).get().getPrice() * orders.getFastquan());
        orders.setTotalMass(productsrepository.findById(id).get().getMass()  * orders.getFastquan());
        orders.setTotalPostPrice(postprice);

        orderrepository.save(orders);

        return "successpage";

    }

    @GetMapping("/search")
    public String search(products products, @RequestParam String sparam,  Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);



        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","rozn",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","rozn",
                Sort.by(Sort.Direction.DESC, "date"));

        Iterator<products> odniter = odnoraz.iterator();
        while (odniter.hasNext()){
            if (!odniter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                odniter.remove();
            }
        }
        Iterator<products> jijiter = jija.iterator();
        while (jijiter.hasNext()){
            if (!jijiter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                jijiter.remove();
            }
        }

        Iterator<products> poditer = pod.iterator();
        while (poditer.hasNext()){
            if (!poditer.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                poditer.remove();
            }
        }

        Iterator<products> cartridgeiter = cartridge.iterator();
        while (cartridgeiter.hasNext()){
            if (!cartridgeiter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                cartridgeiter.remove();
            }
        }

        Iterator<products> ispariter = ispar.iterator();
        while (ispariter.hasNext()){
            if (!ispariter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                ispariter.remove();
            }
        }

        model.addAttribute("productstype1",odnoraz);
        model.addAttribute("productstype2",jija);
        model.addAttribute("productstype3",cartridge);
        model.addAttribute("productstype4",ispar);
        model.addAttribute("productstype5",pod);


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());


        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return Iterables.concat(odnoraz,jija,cartridge,ispar,pod);
            }
        });

        model.addAttribute("opt",false);


        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("sparamalready",sparam);

        return "productsList";

    }

    @GetMapping("/opt/search")
    public String optsearch(products products, @RequestParam String sparam,  Model model){


//        if(1==1)
//            return "redirect:/products/search?sparam=podonki%20%D0%B0%D0%BD%D0%B0%D1%80%D1%85%D0%B8%D1%8F";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname", currentPrincipalName);

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);




        Iterable<products> odnoraz = productsrepository.findByTypeofproductAndOptIliRozn("odnoraz","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> jija = productsrepository.findByTypeofproductAndOptIliRozn("jija","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> pod = productsrepository.findByTypeofproductAndOptIliRozn("pod","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> cartridge = productsrepository.findByTypeofproductAndOptIliRozn("cartridge","opt",
                Sort.by(Sort.Direction.DESC, "date"));
        Iterable<products> ispar = productsrepository.findByTypeofproductAndOptIliRozn("ispar","opt",
                Sort.by(Sort.Direction.DESC, "date"));

        Iterator<products> odniter = odnoraz.iterator();
        while (odniter.hasNext()){
            if (!odniter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                odniter.remove();
            }
        }
        Iterator<products> jijiter = jija.iterator();
        while (jijiter.hasNext()){
            if (!jijiter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                jijiter.remove();
            }
        }

        Iterator<products> poditer = pod.iterator();
        while (poditer.hasNext()){
            if (!poditer.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                poditer.remove();
            }
        }

        Iterator<products> cartridgeiter = cartridge.iterator();
        while (cartridgeiter.hasNext()){
            if (!cartridgeiter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                cartridgeiter.remove();
            }
        }

        Iterator<products> ispariter = ispar.iterator();
        while (ispariter.hasNext()){
            if (!ispariter.next().getTitle().toLowerCase().contains(sparam.toLowerCase())) {
                ispariter.remove();
            }
        }

        model.addAttribute("productstype1",odnoraz);
        model.addAttribute("productstype2",jija);
        model.addAttribute("productstype3",cartridge);
        model.addAttribute("productstype4",ispar);
        model.addAttribute("productstype5",pod);


        model.addAttribute("productstype1size",odnoraz.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype2size",jija.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype3size",cartridge.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype4size",ispar.spliterator().getExactSizeIfKnown());
        model.addAttribute("productstype5size",pod.spliterator().getExactSizeIfKnown());

        model.addAttribute("products",new LazyContextVariable<Iterable<products>>() {
            @Override
            protected Iterable<products> loadValue() {
                return Iterables.concat(odnoraz,jija,cartridge,ispar,pod);
            }
        });


        model.addAttribute("opt",true);


        model.addAttribute("userrep",userrepository);
        model.addAttribute("productsrep", productsrepository);
        model.addAttribute("commrep",commentrepository);

        model.addAttribute("curusname",currentPrincipalName);

        model.addAttribute("sparamalready",sparam);

        return "productsList";
    }



}
