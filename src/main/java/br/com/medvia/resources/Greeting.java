package br.com.medvia.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Greeting {

    private final long id;
    private final String content;
    private Date date;
    public String text;
    private String field1;
    private String field2;
    
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
        this.date = new Date();
        this.text = "text-" + id;
        this.field1 = "campo n. 1";
        this.field2 = "campo n. 2";
        
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getField2() {
        return field2;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatedDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
    }
    
}
