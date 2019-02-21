package controllers;

import domain.Enrolment;
import domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import services.EnrolmentService;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController{



    public MemberController(){ super(); }



}
