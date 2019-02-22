package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


public class MyControllerTest {

    @Test
    public void shouldReturnMap() throws Exception{
        MyController myController=new MyController();
        MockMvc mockMvc=standaloneSetup(myController)
                .build();

        Map<String,String> map=new HashMap<>();
        map.put("a","b");
        map.put("b","c");
        List<Map> mapList=new ArrayList<>();
        mapList.add(map);

        mockMvc.perform(get("/my/api1"))
                .andExpect(view().name("my/api1"))
                .andExpect(model().attributeExists("mapList"))
                .andExpect(model().attribute("mapList",mapList));


    }

    @Test
    public void viewTest() throws Exception{
        MyController myController=new MyController();
        MockMvc mockMvc=standaloneSetup(myController)
                .build();

        mockMvc.perform(get("/my/api2"))
                .andExpect(view().name("my/api1"));

    }
}
