package com.example.demo.shared.application;

import com.example.demo.shared.domain.Command;

public interface CommandBus {

    void handle(Command command);

}
