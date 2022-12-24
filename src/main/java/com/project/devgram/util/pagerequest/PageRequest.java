package com.project.devgram.util.pagerequest;

public interface PageRequest {

    void setPage(int page);
    void setSize(int size);

    org.springframework.data.domain.PageRequest of();
}
