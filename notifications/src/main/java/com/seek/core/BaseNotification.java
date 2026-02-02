package com.seek.core;

public abstract class BaseNotification implements Notification {
    protected final String to;
    protected final String content;

    protected BaseNotification(Builder<?> builder) {
        this.to = builder.to;
        this.content = builder.content;
    }

    @Override
    public String getTo() { return to; }

    @Override
    public String getContent() { return content; }

    
    public abstract static class Builder<T extends Builder<T>> { // T es el tipo concreto del Builder hijo (ej: EmailBuilder)
        private String to;
        private String content;

        // Método abstracto que obliga al hijo a devolver "this" con su tipo correcto
        // Este es un comentario del chat, lo dejo porque me parece importante.
        protected abstract T self();

        public T to(String to) {
            this.to = to;
            return self(); // Retorna T (el hijo), no Builder (el padre)
        }

        public T content(String content) {
            this.content = content;
            return self();
        }

        // Obligamos a definir un build()
        public abstract Notification build();
    }
}