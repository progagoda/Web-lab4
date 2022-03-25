package progagoda.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(
        name = "responses"
)
public class ResponseEntity implements Serializable {
    @Id
    @Column(
            name = "id"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "responses_next_id"
    )
    private Long id;
    @Column(
            name = "user_id"
    )
    private Long userId;
    @Column(
            name = "x"
    )
    private Float x;
    @Column(
            name = "y"
    )
    private Float y;
    @Column(
            name = "r"
    )
    private Float r;
    @Column(
            name = "curtime"
    )
    @CreationTimestamp
    private Timestamp curTime;
    @Column(
            name = "extime"
    )
    private Long exTime;
    @Column(
            name = "res"
    )
    private Boolean res;
    @Column(
            name = "left"
    )
    private Float left;
    @Column(
            name = "top"
    )
    private Float top;

    public ResponseEntity(Long userId, Float x, Float y, Float r, Long exTime, Boolean res, Float left, Float top) {
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.r = r;
        this.exTime = exTime;
        this.res = res;
        this.left = left;
        this.top = top;
    }
    public String toString() {
        ResponseEntity responseEntity = new ResponseEntity(this.userId,this.x,this.y,this.r,this.exTime,this.res,this.left,this.top);
        Gson gson = new Gson();
        return gson.toJson(responseEntity);
    }

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Float getX() {
        return this.x;
    }

    public Float getY() {
        return this.y;
    }

    public Float getR() {
        return this.r;
    }

    public Timestamp getCurTime() {
        return this.curTime;
    }

    public Long getExTime() {
        return this.exTime;
    }

    public Boolean getRes() {
        return this.res;
    }

    public Float getLeft() {
        return this.left;
    }

    public Float getTop() {
        return this.top;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setR(Float r) {
        this.r = r;
    }

    public void setCurTime(Timestamp curTime) {
        this.curTime = curTime;
    }

    public void setExTime(Long exTime) {
        this.exTime = exTime;
    }

    public void setRes(Boolean res) {
        this.res = res;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public void setTop(Float top) {
        this.top = top;
    }

    public ResponseEntity() {
    }
}
