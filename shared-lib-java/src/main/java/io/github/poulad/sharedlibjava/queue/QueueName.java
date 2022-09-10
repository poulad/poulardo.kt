package io.github.poulad.sharedlibjava.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

@Getter
@RequiredArgsConstructor
public enum QueueName {
    MY_QUEUE("myQueue"),
    ;

    @Nonnull
    private final String queueName;

    @Nonnull
    @Override
    public String toString() {
        return queueName;
    }
}
