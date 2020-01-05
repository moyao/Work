package com.guansu.management.config;

/**
 * @date:
 * @author: dongyaoyao
 */
public class Payment {
    /**
     * 支付宝
     */
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016101600703264";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102179897877";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "dsddd";

    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCL7uK7qsXo9DYF+RT1hvtLh9rprGZpJLh3+/WM8apTQom3DprFIA4ODAS1ugB1GFgATn9B7Z9ElZRzZK3Stno5EvRcp5bdfolbjYkdCjunTtQEFouhs0lI54HKYBKrq4/+x4a9m9Fh0Yit3enXSDRRb/398VUqmRBAW6JYzou8B60mk5Vy8Una63I4QkonRnwJ9vmEWHsUQJxaY2p+HdfFZfJ7GiY5LKFDaac1mRmSzYmgcZSfdQQ1qYAG/6mVT/eTZzTSTQ3TBhHI/l4El9L2MgWiuISYdFLz8Zd6WluwWv/iGgG9UeCAFI/aU37WY77Uq08p07Ri1uYk7y2SemUPAgMBAAECggEAOBL0PKvEPwONmnsj0PfUP4IjUTxKlZunaD1JOr+kt1SUhwyNLK0ZtLtQL9pnUIT+zrCmAi9MgVlHca5IkYWaGMDltzNZG00Xkd53sXFmUjcecrzuG1AS7+oFcqlpEe3f+ALQla6ZCw/CK5ofmKtQe8nbCCXrMPTBNGkD935dNG1vTw6v+kWlGEZOG4f4Hd+TliK/5o7AGGc7Adlbp0lfteQCP3bGAIaOQre0I4Iw6AninT7jXKlT5ZWwek5XfN/AjnNDgkkPwu218vp1SJ1Fq8wOBjyfWyyYPa9r/a2EuImw8Z82JLqwRus6Nuk/mu6hsSQ/2z/oURysEjISbR750QKBgQDCtV1Ipl0+nDM/2v0xGSTfGdqM2Vcs0FdbjqRl8M/09W+ajSnU/K7N3/0x5Wd4uIi/+STWIqoVyau1OKaDYslVsK4rHUZWg8B0PgmOMBzZ1EGDYsRoSgvtSa/BFsz5BA5Rz198eEb4uL0CT6vb5AS4rIMnV0tur2Rw8JGHO7MwawKBgQC3+3AOXTPClbJEKmeM66pmtAqB591jVDxZpH9ZZEFmH8LzePQkyuc7bOEm7Zmid/GDuvnXgUod56NZsPDFO49VQ9VYpnUP5h3Q2WJWQ08Q1EBOmjuHIEL4hkpmN/Hh9f1lOU8yvo1XoVR2JS8lqigALIFH5d/fEJuuFcGFXCA27QKBgCYhIi1x9tyl9htRJTgSNnhCNdxTS5EJE7JoUqVr7S2JStu3PTPqb8j9TjYROJwZdx4xChetHiW6IpgQifs8wzS2rsTerMGQd4XMCE8B+QRnXpT+KHxL094aCgMEFgvF0ZAPziEv5KYcd1cWhw8+rfYEQoLXw3vfeQns8nLLLCIVAoGBAILR/rO4Af3yg3eEz2ycheXRT0dfn0PpuZ/kH8loSDhdjTmiX7dpr1HJ8a7Ec+Had5i7cruR6ymoc7Zq4kjcOObf/Ou0MLjFX54JdKrx4V6ODcvWT02d2wDsPCrCzPNm/m7jeTUT2VzeEjiP6B5YB2lYO/Trt2vdW1VFkv1IrH9hAoGBALUGctjigceKYN8eQ9q1rw8hsT4NDP/CFQ8NENTfD/Ts3AKHp14gu+9zq3xyIpWcjEUr29Kh6EgCgxvSPsTuQzwG3eU4/fy/U7QSpNwAZrIWNUvBizQAaHHK1QbH5kYFO/KDPJHwXq35mKxG02UdQjXi0CJvU/TXtbHPVYnm7rKv";
    public static final String RSA_PRIVATE = "";

    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;
    public static final int REQUEST_READ_PHONE_STATE = 0;
}
