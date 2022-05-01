package ru.ws.marketplace.service.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class ScheduledService {

    private final CRUDUserServiceImpl crudUserService;

    public ScheduledService(CRUDUserServiceImpl crudUserService) {
        this.crudUserService = crudUserService;
    }

    // С помощью zone можно указать:
    // Europe/Moscow	(+03:00) Moscow
    // Asia/Vladivostok 	(+10:00) Vladivostok
    // секунды, минуты, часы, день в месяце, месяц, дней в неделю

    @Scheduled(cron = "0 0 10 1 * ?")
    public void checkerUser() {

        List<TUser> allTUser = crudUserService.getAllByEndDate();

        Calendar calendar = new GregorianCalendar();
        calendar.get(Calendar.DATE);

        List<TUser> userList = allTUser.stream().filter(tUser -> tUser.getEndDate().equals(calendar)).collect(Collectors.toList());

        allTUser.removeAll(userList);
    }
}
