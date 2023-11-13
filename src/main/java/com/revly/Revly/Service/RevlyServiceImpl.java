package com.revly.Revly.Service;

import com.revly.Revly.Model.Doubt;
import com.revly.Revly.Model.TutorAvailability;
import com.revly.Revly.Model.Users;
import com.revly.Revly.Repository.DoubtRepository;
import com.revly.Revly.Repository.TutorRepository;
import com.revly.Revly.Repository.Userrepository;
import com.revly.Revly.exception.TutorException;
import com.revly.Revly.exception.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RevlyServiceImpl implements RevlyService{

    @Autowired
    private Userrepository ur;

    @Autowired
    private TutorRepository tr;

    @Autowired
    private DoubtRepository dr;

    public static String username = null;

    @Override
    public Users studentSignup(Users user) {
        return ur.save(user);
    }

    @Override
    public TutorAvailability tutorSignup(TutorAvailability ta) {
        return tr.save(ta);
    }

    @Scheduled(fixedRate = 3000)
    @Override
    public void updateTutorPing() throws TutorException {

        TutorAvailability tutorAvailability = tr.findByUsernameAndUserType(username, "TUTOR").orElseThrow(()-> new TutorException("Tutor Not FOund"));
        tutorAvailability.setPing(LocalDateTime.now());
        tr.save(tutorAvailability);
    }

    @Scheduled(fixedRate = 3000)
    @Override
    public List<TutorAvailability> acticeTutor() {
        return tr.findAllActiveTutors(LocalDateTime.now().minusSeconds(3));
    }

    @Override
    public Doubt createDoudt(String username, Doubt doubt) throws UsersException, TutorException {
        Users user = ur.findByUsername(username).orElseThrow(()-> new UsersException("User Not Found"));
        List<TutorAvailability> tr = acticeTutor();
        TutorAvailability tr1 = tr.stream().filter(h-> h.getGrade().equals(user.getGrade()) && h.getLanguage().equals(user.getLanguage()) && h.getSubjects().contains(doubt.getSubject())).findFirst().orElseThrow(()-> new TutorException("No Tutor Found"));
        List<Doubt> doubts = tr1.getRequest();
        doubts.add(doubt);
        doubt.setCreated(LocalDateTime.now());
        tr1.setRequest(doubts);
        doubt.setTutorAvailability(tr1);

        List<Doubt> d = user.getDoubts();
        d.add(doubt);
        user.setDoubts(d);
        doubt.setUser(user);
        return dr.save(doubt);
    }

    @Override
    public List<Doubt> getAllDoubts(String username) throws UsersException {
        Users user = ur.findByUsername(username).orElseThrow(()-> new UsersException("User Not Found"));
        return user.getDoubts();
    }

    @Override
    public Users getUserByUsername(String username) throws UsersException {
        return ur.findByUsername(username).orElseThrow(()-> new UsersException("User Not Found"));
    }


}
