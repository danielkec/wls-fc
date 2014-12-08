package cz.kec.wls.feedcombiner.beans;

import cz.kec.wls.feedcombiner.utils.MockUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel Kec <daniel at kecovi.cz>
 */
@Startup
@Singleton
public class RefreshFeedsTimerBean {
private final Logger LOG = LoggerFactory.getLogger(RefreshFeedsTimerBean.class);

static{
    //MockUtils.createMockFeeds();
    //MockUtils.mockSync();
    
}

    @PostConstruct
    public void initTimer(){
        LOG.info("Initializing timer {}",RefreshFeedsTimerBean.class.getName());
        this.timerHandle = timerService.createCalendarTimer(
                (new ScheduleExpression()).second(this.timeoutIntervalInSeconds)).getHandle();
        MockUtils.createMockFeeds();
    }

    @Resource
    TimerService timerService;
    private TimerHandle timerHandle;
    private int timeoutIntervalInSeconds = 10;//10 sec is default 


    @Timeout
    public void refreshFeedsTimer() {
        LOG.info("Automatically refreshing feeds.");

        LOG.info("Refreshing ...");
        MockUtils.mockSync();
        LOG.info("Refreshing DONE!");
    }
    

    
    
    
    public int getTimerIntervalInSeconds(){
        return this.timeoutIntervalInSeconds;
    }
    
    public void setTimerIntevalInSeconds(int intervalDuration) {
        LOG.info("Setting a programmatic timeout for " +
                intervalDuration + " seconds from now.");
        this.timeoutIntervalInSeconds = intervalDuration;
        timerHandle.getTimer().getSchedule().second(intervalDuration);       
    }

}
