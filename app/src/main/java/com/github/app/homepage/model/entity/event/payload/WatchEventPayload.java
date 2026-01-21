package com.github.app.homepage.model.entity.event.payload;

import com.github.account.model.User;
import com.github.app.homepage.model.entity.event.Payload;
import com.github.app.homepage.model.entity.event.Repository;

/**
 * com.github.app.events.model.payload.WatchEventPayload
 *
 * @author Ivan J. Lee on 2020-01-01 20:08
 * @version v0.1
 * @since v1.0
 **/
public class WatchEventPayload extends Payload {

    private Repository repository;
    private User sender;
}
