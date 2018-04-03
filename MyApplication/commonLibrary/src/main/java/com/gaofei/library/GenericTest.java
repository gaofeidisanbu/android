package com.gaofei.library;

public class GenericTest {
    public static void main(String[] args) {
        try {
            generic();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            System.out.println(e);
        }
    }


    public static void generic() {
        Holder<User> holder = new Holder<>();
        holder.setTarget(new User("gaoFei"));
        User user = holder.getTarget();
        System.out.println(user.getName());
    }


    public static class Holder<T> {
        private T t;

        public void setTarget(T t) {

        }

        public T getTarget() {
            return t;
        }
    }

    public static class User {
        private String mName;

        public User(String name) {
            this.mName = name;
        }

        public String getName() {
            return mName;
        }

    }

}
