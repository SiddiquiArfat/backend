package com.revly.Revly.Controller;

import com.revly.Revly.Model.Doubt;
import com.revly.Revly.Model.TutorAvailability;
import com.revly.Revly.Model.Users;
import com.revly.Revly.Repository.Userrepository;
import com.revly.Revly.Service.RevlyService;
import com.revly.Revly.Service.RevlyServiceImpl;
import com.revly.Revly.exception.TutorException;
import com.revly.Revly.exception.UsersException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class RevlyController {
    @Autowired
    private RevlyService rs;
    @Autowired
    private Userrepository ur;
    @Autowired
    private PasswordEncoder pe;

    //    Student Adding API
    @PostMapping("/addStudent")
    public ResponseEntity<Users> addStudent(@RequestBody @Valid Users user){
        user.setPassword(pe.encode(user.getPassword()));
        return new ResponseEntity<>(rs.studentSignup(user), HttpStatus.OK);
    }

    //    Tutor Adding API
    @PostMapping("/addTutor")
    public ResponseEntity<TutorAvailability> addTutor(@RequestBody @Valid TutorAvailability user){
        user.setPassword(pe.encode(user.getPassword()));
        return new ResponseEntity<>(rs.tutorSignup(user), HttpStatus.OK);
    }

    //    Login method
    @PostMapping("/signIn")
    public ResponseEntity<String> getLoggedInCustomerDetailsHandler(Authentication auth) throws UsersException {
        RevlyServiceImpl.username = auth.getName();
         Users customer= ur.findByUsername(auth.getName()).orElseThrow(()-> new UsersException("User Not Found"));
        return new ResponseEntity<>(customer.getUsername()+"Logged In Successfully", HttpStatus.ACCEPTED);
    }

    //    get all Active tutor
    @GetMapping("/getTutor")
    public ResponseEntity<List<TutorAvailability>> listTutor(Principal principal){
        RevlyServiceImpl.username = principal.getName();
        return new ResponseEntity<>(rs.acticeTutor(), HttpStatus.OK);
    }

//    Creating Doubts
    @PostMapping("/addDoubt")
    public ResponseEntity<Doubt> addDoubt(@RequestBody @Valid Doubt doubt, Principal principal) throws UsersException, TutorException {
        RevlyServiceImpl.username = principal.getName();
        return new ResponseEntity<>(rs.createDoudt(principal.getName(), doubt), HttpStatus.OK);
    }

//    getting All doubts
    @GetMapping("/doubts")
    public ResponseEntity<List<Doubt>> getAllDoubts(Principal principal) throws UsersException {
        RevlyServiceImpl.username = principal.getName();
        return new ResponseEntity<>(rs.getAllDoubts(principal.getName()), HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<Users> users(Principal principal) throws UsersException {
        RevlyServiceImpl.username = principal.getName();
        return new ResponseEntity<>(rs.getUserByUsername(principal.getName()), HttpStatus.OK);
    }


}
