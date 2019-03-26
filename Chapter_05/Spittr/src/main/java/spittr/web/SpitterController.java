package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spittr.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm() {
    return "registerForm";
  }

  /*
  * @Comment : 因为重定向的模型不能传递数据，所以要使用url模版的方式来传递数据
  *            重定向使用string拼接的方式，有安全风险，所以改造成下面占位符的形式,不安全字符会进行转义
  *
  * @Author  : yii.fant@gmail.com
  * @Date    : 2019-03-26
  */
  @RequestMapping(value="/register2", method=POST)
  public String processRegistration2(
      @Valid Spitter spitter,
      Errors errors,Model model) {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    
    spitterRepository.save(spitter);
//    return "redirect:/spitter/" + spitter.getUsername();


    model.addAttribute("username",spitter.getUsername());
    model.addAttribute("password",spitter.getPassword());
    return "redirect:/spitter/{username}";
    //因为模型中的spitterId属性没有匹配重定向URL中的任何占位符，所以它会自
    //动以[查询参数]的形式附加到重定向URL上。如果username属性的值是habuma并且spitterId属性的值是42，
    //那么结果得到的重定向URL路径将会是“/spitter/habuma?spitterId=42”
    //上面password仅做demo演示
  }


  /*
  * @Comment : 这里使用的Spring3.1引入的Model的子接口，Flash attribute特性
  *            这个属性可以将跨重定向存活的数据属性复制在会话中，重定向之后会将其转移出来
  * @Author  : yii.fant@gmail.com
  * @Date    : 2019-03-26
  */
  @RequestMapping(value = "/register",method = POST)
  public String processRegistration(
          @Valid Spitter spitter,
          Errors errors, RedirectAttributes model){
    if (errors.hasErrors()) {
      return "registerForm";
    }
    spitterRepository.save(spitter);
    model.addAttribute("username", spitter.getUsername());
    model.addFlashAttribute("spitter",spitter);
    return "redirect:/spitter/{username}";
  }





  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {
      // 当model中没有spitter对象的时候，才去数据库中获取
    if (!model.containsAttribute("spitter")){
      Spitter spitter = spitterRepository.findByUsername(username);
      model.addAttribute(spitter);
      System.out.println("search in dbbbbbbb");
    }
    return "profile";
  }
  
}
