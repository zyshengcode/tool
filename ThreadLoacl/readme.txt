1.ThreadLocal，很多地方叫做线程本地变量，也有些地方叫做线程本地存储。
ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。

2.在进行get之前，必须先set，否则会报空指针异常；
如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue()方法。


应用场景

private static final ThreadLocal threadSession = new ThreadLocal();
 
public static Session getSession() throws InfrastructureException {
    Session s = (Session) threadSession.get();
    try {
        if (s == null) {
            s = getSessionFactory().openSession();
            threadSession.set(s);
        }
    } catch (HibernateException ex) {
        throw new InfrastructureException(ex);
    }
    return s;
}