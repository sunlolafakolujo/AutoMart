package com.dapafol.userregistration.event;

import com.dapafol.userregistration.appuser.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class RegistrationEvent extends ApplicationEvent {
    private AppUser appUser;
    private String applicationUrl;

    public RegistrationEvent(AppUser appUser, String applicationUrl) {
        super(appUser);
        this.appUser=appUser;
        this.applicationUrl=applicationUrl;
    }
}
