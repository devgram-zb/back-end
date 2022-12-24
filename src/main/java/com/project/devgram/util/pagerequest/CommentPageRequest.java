package com.project.devgram.util.pagerequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class CommentPageRequest implements PageRequest {
    private int page;
    private int size;
    private Sort sort = Sort.by(Direction.ASC, "commentSeq");

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int maxSize = 20;
        this.size = size > maxSize ? maxSize : size;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, sort);
    }
}
