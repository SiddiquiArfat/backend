package com.revly.Revly.Service;

import com.revly.Revly.Model.Doubt;
import com.revly.Revly.Model.TutorAvailability;
import com.revly.Revly.Model.Users;
import com.revly.Revly.exception.TutorException;
import com.revly.Revly.exception.UsersException;

import java.security.Principal;
import java.util.List;

public interface RevlyService {

    //Student SignUp Method
    public Users studentSignup(Users user);

    //tutor signup method
    public TutorAvailability tutorSignup(TutorAvailability ta);

    //(POLLING Function)updating ping after each 3 seconds
    public void updateTutorPing () throws TutorException;

    // Getting All Active Tutor
    public List<TutorAvailability> acticeTutor();

    // Creating New Doubts;
    public Doubt createDoudt(String username, Doubt doubt) throws UsersException, TutorException;

    // Getting All Doubts
    public List<Doubt> getAllDoubts(String username) throws UsersException;

    public Users getUserByUsername(String username) throws UsersException;
}
