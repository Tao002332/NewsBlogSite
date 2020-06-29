package entity;

/**
 * *@author 83614
 * *@date 2020/3/11
 **/

public class StatusCode {
    /**
     * 成功
     */
    public  static final  int OK=20000;

    /**
     * 失败
     */
    public  static final  int ERROR=20001;

    /**
     * 登录失败
     */
    public  static final  int LOGGINGERROR=20002;

    /**
     * 权限不足
     */
    public  static final  int ACCESSERR0R=20003;

    /**
     * 远程调用失败
     */
    public  static final  int REMOTEERROR=20004;

    /**
     * 重重操作
     */
    public  static final  int REPERROR=20005;
}
