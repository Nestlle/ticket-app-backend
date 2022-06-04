package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.entity.CategoryEntity;
import com.ticketApp.ticketApp.entity.ReservationEntity;
import com.ticketApp.ticketApp.repository.ReservationRepository;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
public class JobRunrService {

    private final JobRunrDashboardLogger logger = new JobRunrDashboardLogger(LoggerFactory.getLogger(JobRunrService.class));
    private final Integer maxNbOfDays = 1;
    @Autowired
    private ReservationRepository reservationRepository;

    @Recurring(id = "reservation-cleanup-job", cron = "*/1440 * * * *")
    @Job(name = "Reservation Cleanup Job")
    public void executeReservationCleanup() {
        try
        {
            List<ReservationEntity> reservations = reservationRepository.findAll();
            logger.info("Got " + reservations.stream().count() + " from database");
            for (ReservationEntity entity : reservations) {
                logger.info("Entity with ticket id " + entity.getTicket().getTicketID() + " created : " + entity.getCreationDate());
                if(entity.getCreationDate() != null){
                    if(entity.getCreationDate().plusDays(maxNbOfDays).isAfter(LocalDate.now())){
                        logger.info("Entity with ticket id " + entity.getId() + " has been deleted");
                        reservationRepository.delete(entity);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("There was an exception running the job : " + e.getMessage() + ". Stackstrace : " + e.getStackTrace());
        }
    }
}
