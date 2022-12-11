package com.example.demo.shared.application;

import com.example.demo.shared.domain.Query;

public interface QueryHandler<R, T extends Query<R>> {

    R handle(T query);
}
