package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/my")
public class MyController {

    @RequestMapping(value = "/api1",method = RequestMethod.GET)
    public List<Map> getResult(){
//        List<String> stringList=new ArrayList<>();
//        stringList.add("halo");
//        stringList.add("nihao");
        Map<String,String> map=new HashMap<>();
        map.put("a","b");
        map.put("b","c");
        List<Map> mapList=new ArrayList<>();
        mapList.add(map);

        return mapList;
    }

    @RequestMapping(value = "/api2")
    public ModelAndView testView(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("forward:my/api1");
        return modelAndView;
    }
}
