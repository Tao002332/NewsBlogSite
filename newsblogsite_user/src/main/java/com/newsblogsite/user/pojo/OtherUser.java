package com.newsblogsite.user.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user")
public class OtherUser implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 兴趣
     */
    private String interest;
    /**
     * 个性
     */
    private String personality;
    /**
     * 粉丝数
     */
    private Integer fansCount;
    /**
     * 关注数
     */
    private Integer followCount;

    public OtherUser() {
    }

    public OtherUser(String id, String nickname, String sex, String avatar, String interest, String personality, Integer fansCount, Integer followCount) {
        this.id = id;
        this.nickname = nickname;
        this.sex = sex;
        this.avatar = avatar;
        this.interest = interest;
        this.personality = personality;
        this.fansCount = fansCount;
        this.followCount = followCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    @Override
    public String toString() {
        return "OtherUser{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", interest='" + interest + '\'' +
                ", personality='" + personality + '\'' +
                ", fansCount=" + fansCount +
                ", followCount=" + followCount +
                '}';
    }
}
