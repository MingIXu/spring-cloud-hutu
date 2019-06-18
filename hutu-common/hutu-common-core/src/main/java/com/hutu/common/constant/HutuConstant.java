package com.hutu.common.constant;
/**
 * something
 *
 * @author hutu
 * @date 2019/4/12
 */
public class HutuConstant {

    public enum PermissionType {
        /**
         * 权限类别
         */
        CATALOG(1),
        MENU(2),
        BUTTON(3);
        public int code;
        PermissionType(int code){
            this.code = code;
        }
    }
    public enum RoleType {
        /**
         * 角色类型
         */
        SYSTEM(1,"预设角色"),
        CUSTOM(2,"自定义角色");
        public int code;
        public String desc;
        RoleType(int code,String desc){
            this.code = code;
            this.desc = desc;
        }
    }
    public enum Sex {
        /**
         * 性别类型
         */
        MAN(0,"男"),
        WOMAN(1,"女");
        public int code;
        public String desc;
        Sex(int code,String desc){
            this.code = code;
            this.desc = desc;
        }
    }
}
