package com.example.demo.shared.application;

import com.example.demo.shared.domain.Command;

public interface CommandHandler<T extends Command> {

    void handle(T command);

}
